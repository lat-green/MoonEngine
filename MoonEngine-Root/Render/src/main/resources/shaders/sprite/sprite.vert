#version 330 core

invariant gl_Position;

uniform mat4 projectionView;
uniform mat4 model;

layout(location = 0) in vec2 vertex_coord;
layout(location = 1) in vec2 tex_coord;

out vec2 tex_coord_f;

void main()
{
	tex_coord_f = tex_coord;
	
	vec4 vcoord = vec4( vertex_coord, 0, 1.0 );
	gl_Position = projectionView * model * vcoord;
}