#version 330 core
layout (triangles) in;
layout (triangle_strip, max_vertices=3) out;

uniform mat4 projectionView;
uniform vec3 lightPos;
uniform float far_plane;
uniform int face;

out float lightDistance; 

void main()
{
	gl_Layer = face; 
	for(int i = 0; i < 3; ++i)
	{
		vec4 FragPos = gl_in[i].gl_Position;
		gl_Position = projectionView * FragPos;
		lightDistance = length(FragPos.xyz - lightPos) / far_plane;
		EmitVertex();
	}    
	EndPrimitive();
} 