#version 330 core

layout (location = 0) in vec3 aPos;

uniform mat4 projectionView;
uniform mat4 model;

void main()
{
    gl_Position = projectionView * model * vec4(aPos, 1.0);
}