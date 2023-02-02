#version 330 core

uniform sampler2D render_texture;

out vec4 color;

in vec2 tex_coord_f;

void main(void) {
	color = texture(render_texture, tex_coord_f);
	if(color.a == 0)
		discard;
}
