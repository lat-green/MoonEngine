#version 330 core

uniform sampler2D sprite;

out vec4 color;

in vec2 tex_coord_f;

void main(void) {
	color = texture(sprite, tex_coord_f);
	if(color.a == 0)
		discard;
}
