#version 330 core

out vec4 FragColor;

#define PI 3.14159265359

#define MAX_LIGHT 3

in VS_OUT {
    vec3 FragPos;
    vec2 TexCoords;
	mat3 TBN;
    vec3[MAX_LIGHT] FragPosLightSpace;
    vec3 TangentViewPos;
    vec3 TangentFragPos;
} fs_in;


struct PointLight {
	vec3 position;
    vec3 color;
	float intensity;

	samplerCube depth;
};

struct DirLight {
    vec3 direction;
    vec3 color;
	float intensity;

	sampler2D depth;
};

struct Material {
    sampler2D  albedo;
    sampler2D  metallic;
    sampler2D  normal;
    sampler2D  roughness;
    sampler2D  displacement;
    sampler2D  ao;
};
struct Color {
    vec3   albedo;
    float  metallic;
    vec3   normal;
    float  roughness;
    float  displacement;
};

uniform vec3 viewPos;
uniform float far_plane;

uniform PointLight point_light[MAX_LIGHT];
uniform int count_point_light;

uniform DirLight dir_light[MAX_LIGHT];
uniform int count_dir_light;

uniform Material material;

uniform float displacement_scale, ao_scale;

vec2 ParallaxMapping(vec2 texCoords, vec3 viewDir);

float DistributionGGX(vec3 normal, vec3 H, float roughness);
float GeometrySchlickGGX(float NdotV, float roughness);
float GeometrySmith(vec3 normal, vec3 V, vec3 L, float roughness);
vec3 fresnelSchlickRoughness(float cosTheta, vec3 F0, float roughness);
vec3 fresnelSchlick(float cosTheta, vec3 F0);

float ShadowCalculationDirection(int i, float bias, vec3 side_normal, vec3 normal);
float ShadowCalculationPoint(int i, float bias, vec3 side_normal, vec3 normal);

vec3 CalcLight(Color color, vec3 V, vec3 F0, vec3 L, vec3 light_diffuse);

void main()
{
    // vec3 viewDir = normalize(fs_in.TangentViewPos - fs_in.TangentFragPos);
	// vec2 TexCoords = ParallaxMapping(fs_in.TexCoords, viewDir);

	vec2 TexCoords = fs_in.TexCoords;

	// FragColor = vec4(TexCoords, 0, 1);
	// return;

	Color col;
	
	col.normal = texture(material.normal, TexCoords).rgb;
	col.normal = normalize(col.normal * 2.0 - 1.0);
	col.normal = normalize(fs_in.TBN * col.normal);

	col.albedo = texture(material.albedo, TexCoords).rgb;
    col.roughness = texture(material.roughness, TexCoords).r;
    col.metallic = texture(material.metallic, TexCoords).r;
	
	
	vec3 V = normalize(viewPos - fs_in.FragPos);
	vec3 F0 = vec3(0.04); 
	F0 = mix(F0, col.albedo, col.metallic);
	
	vec3 Lo = vec3(0.0);

	for(int i = 0; i < count_point_light; ++i)
	{
		vec3 D = (point_light[i].position - fs_in.FragPos);
		vec3 L = normalize(D);
		float bias = max(0.05 * (1.0 - dot(fs_in.TBN[2], L)), 0.005);

		Lo += 
		(1 - ShadowCalculationPoint(i, bias, fs_in.TBN[2], col.normal))
		* CalcLight(col, V, F0, L, point_light[i].color)
		* point_light[i].intensity;
	}

	for(int i = 0; i < count_dir_light; ++i) 
	{
		vec3 L = -dir_light[i].direction;
		float bias = max(0.05 * (1.0 - dot(fs_in.TBN[2], L)), 0.005);
		
		Lo += 
		(1 - ShadowCalculationDirection(i, bias, fs_in.TBN[2], col.normal))
		* CalcLight(col, V, F0, L, dir_light[i].color)
		* point_light[i].intensity;
	}

    float ao = texture(material.ao, TexCoords).r;
	vec3 ambient = vec3(ao_scale) * col.albedo * ao;
	
    FragColor = vec4(ambient + Lo, 1);
}

//----------------------------------------------------------------------------------------------------------------------------------------
vec2 ParallaxMapping(vec2 texCoords, vec3 viewDir)
{
    float height = texture(material.displacement, texCoords).r * displacement_scale;
    vec2 p = viewDir.xy / viewDir.z * height;
    return texCoords - p;
}
//----------------------------------------------------------------------------------------------------------------------------------------

float DistributionGGX(vec3 normal, vec3 H, float roughness)
{
    float a      = roughness*roughness;
    float a2     = a * a;
    float NdotH  = max(dot(normal, H), 0.0);
    float NdotH2 = NdotH*NdotH;
	
    float num   = a2;
    float denom = (NdotH2 * (a2 - 1.0) + 1.0);
    denom = PI * denom * denom;
	
    return num / denom;
}

float GeometrySchlickGGX(float NdotV, float roughness)
{
    float r = (roughness + 1.0);
    float k = (r*r) / 8.0;

    float num   = NdotV;
    float denom = NdotV * (1.0 - k) + k;
	
    return num / denom;
}

float GeometrySmith(vec3 normal, vec3 V, vec3 L, float roughness)
{
    float NdotV = max(dot(normal, V), 0.0);
    float NdotL = max(dot(normal, L), 0.0);
    float ggx2  = GeometrySchlickGGX(NdotV, roughness);
    float ggx1  = GeometrySchlickGGX(NdotL, roughness);
	
    return ggx1 * ggx2;
}

float pow_int(float x, int n) {
	float result = 1;
	for(int i = 0; i < n; i++)
		result *= x;
	return result;
}

vec3 fresnelSchlick(float cosTheta, vec3 F0)
{
	return F0 + (1.0 - F0) * pow_int(1.0 - cosTheta, 5);
}
//----------------------------------------------------------------------------------------------------------------------------------------
vec3 CalcLight(Color color, vec3 V, vec3 F0, vec3 L, vec3 light_diffuse)
{
	vec3 normal = color.normal;
	vec3 albedo = color.albedo;
	float roughness = color.roughness;
	float metallic = color.metallic;

	vec3 H = normalize(V + L);
	
	vec3 radiance     = light_diffuse;        

	// Cook-Torrance BRDF
	float NDF = DistributionGGX(normal, H, roughness);        
	float G   = GeometrySmith(normal, V, L, roughness);      
	vec3 F    = fresnelSchlick(max(dot(H, V), 0.0), F0);

	vec3 kD = (1 - F) * (1 - metallic);
	
	vec3 numerator    = NDF * G * F;
	float denominator = 4.0 * max(dot(normal, V), 0.0) * max(dot(normal, L), 0.0);
	vec3 specular     = numerator / max(denominator, 0.001);
	
	// ���������� ��������� � ��������� �������������� ������� Lo
	return (kD * albedo / PI + specular) * radiance;
}
//----------------------------------------------------------------------------------------------------------------------------------------

float ShadowCalculationDirection(int i, float bias, vec3 side_normal, vec3 normal)
{
	#define FRESH 0
	
	if(dot(side_normal, dir_light[i].direction) > 0) return 1;
	if(dot(normal, dir_light[i].direction) > 0) return 1;

    vec3 projCoords = fs_in.FragPosLightSpace[i];
	float currentDepth = projCoords.z - bias;
	if(currentDepth > 1.0) return 0;
	if(currentDepth < 0.0) return 0;

	vec2 texelSize = 1.0 / textureSize(dir_light[i].depth, 0);
	float shadow = 0;
	for(int x = -FRESH; x <= FRESH; ++x)
		for(int y = -FRESH; y <= FRESH; ++y)
		{
			float closestDepth = texture(dir_light[i].depth, projCoords.xy + vec2(x, y) * texelSize).r;
			shadow += currentDepth > closestDepth ? 1.0 : 0.0;			
		}
	return shadow / ((2*FRESH+1)*(2*FRESH+1));

	#undef FRESH
}


float ShadowCalculationPoint(int i, float bias, vec3 side_normal, vec3 normal)
{	
	//#define FRESH

    vec3 fragToLight = fs_in.FragPos - point_light[i].position;
	
	if(dot(side_normal, fragToLight) > 0) return 1;
	if(dot(normal, fragToLight) > 0) return 1;

    float currentDepth = length(fragToLight) / far_plane - bias;

	if(currentDepth > 1) return 1;
	
	#ifndef FRESH
		float closestDepth = texture(point_light[i].depth, fragToLight).r;
		
		return currentDepth > closestDepth ? 1.0 : 0.0;
	#else
		float shadow = 0.0;
		#define samples 20
		
		const vec3 sampleOffsetDirections[samples] = vec3[]
		(
		   vec3( 1,  1,  1), vec3( 1, -1,  1), vec3(-1, -1,  1), vec3(-1,  1,  1), 
		   vec3( 1,  1, -1), vec3( 1, -1, -1), vec3(-1, -1, -1), vec3(-1,  1, -1),
		   vec3( 1,  1,  0), vec3( 1, -1,  0), vec3(-1, -1,  0), vec3(-1,  1,  0),
		   vec3( 1,  0,  1), vec3(-1,  0,  1), vec3( 1,  0, -1), vec3(-1,  0, -1),
		   vec3( 0,  1,  1), vec3( 0, -1,  1), vec3( 0, -1, -1), vec3( 0,  1, -1)
		);  
	
		float viewDistance = length(viewPos - fs_in.FragPos);
		float diskRadius = (1.0 + (viewDistance / far_plane)) / 25.0;  
		for(int i = 0; i < samples; ++i)
		{
			float closestDepth = texture(point_light[i].depth, fragToLight + sampleOffsetDirections[i] * diskRadius).r;
			if(currentDepth > closestDepth)
				shadow += 1.0;
		}
		return shadow / (samples);;
	#endif
	
	#undef FRESH
	
}  