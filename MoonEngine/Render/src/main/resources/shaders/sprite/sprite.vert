#version 330 core

invariant gl_Position;

layout(location = 0) in vec3 vertex_coord;
layout(location = 2) in vec2 tex_coord;

uniform mat4 projectionView;
uniform mat4 model;

out vec2 tex_coord_f;

void main()
{
	tex_coord_f = tex_coord;
    gl_Position = projectionView * model * vec4(vertex_coord, 1.0);
}