#version 330 core

uniform sampler2D texture1;

out vec4 color;

in vec2 tex_coord_f;

void main(void) {
	color = texture(texture1, tex_coord_f);
	if(color.a == 0)
		discard;
}
