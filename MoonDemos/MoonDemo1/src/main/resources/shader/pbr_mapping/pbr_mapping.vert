#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in vec3 aTangent;

#define MAX_LIGHT 3

out VS_OUT {
    vec3 FragPos;
    vec2 TexCoords;
	mat3 TBN;
    vec3[MAX_LIGHT] FragPosLightSpace;
    vec3 TangentViewPos;
    vec3 TangentFragPos;
} vs_out;

uniform mat4 projectionView;
uniform mat4 model;

uniform int count_dir_light;
uniform mat4[MAX_LIGHT] lightSpaceMatrix;

uniform vec3 viewPos;

void main()
{
    mat3 tm = mat3(model);
    
    vec3 T = normalize(tm * aTangent);
    vec3 N = normalize(tm * aNormal);
    vec3 B = cross(N, T);

    vs_out.TBN = mat3(T, B, N);

    vs_out.FragPos = vec3(model * vec4(aPos, 1.0));
	
    vs_out.TexCoords = aTexCoords;
   
	for(int i = 0; i < count_dir_light; i++){
		vec4 vec = lightSpaceMatrix[i] * vec4(vs_out.FragPos, 1.0);
		vs_out.FragPosLightSpace[i] = (vec.xyz / vec.w)  * 0.5 + 0.5;
	}
   
    gl_Position = projectionView * model * vec4(aPos, 1.0);

    vs_out.TangentViewPos  = vs_out.TBN * viewPos;
    vs_out.TangentFragPos  = vs_out.TBN * vs_out.FragPos;
}