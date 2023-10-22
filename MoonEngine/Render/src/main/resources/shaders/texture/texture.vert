#version 330 core

invariant gl_Position;

layout(location = 0) in vec3 vertex_coord;

out vec2 tex_coord_f;

void main()
{
	tex_coord_f = (vertex_coord.xy + 1) / 2;
	
	vec4 vcoord = vec4( vertex_coord, 1.0 );
	gl_Position = vcoord;
}