#version 330 core

in float color_;

out vec4 FragColor;

void main() {
    FragColor = vec4(color_, color_, color_, 1.0);
}