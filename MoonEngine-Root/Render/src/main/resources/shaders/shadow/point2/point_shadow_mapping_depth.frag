#version 330 core

in float lightDistance;

out vec4 FragColor;

void main()
{
    gl_FragDepth = lightDistance;
}  