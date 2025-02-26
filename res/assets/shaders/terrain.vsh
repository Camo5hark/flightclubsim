#version 330 core

layout(location = 0) in vec3 vertex;
layout(location = 1) in float color;

out float color_;

uniform mat4 projection;
uniform mat4 sceneView;

void main() {
    gl_Position = projection * sceneView * vec4(vertex, 1.0);
    color_ = color;
}