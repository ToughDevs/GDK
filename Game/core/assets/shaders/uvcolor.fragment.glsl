#ifdef GL_ES 
precision mediump float;
#endif
#define MAX_COLOR 1

uniform vec3 u_colorU;
uniform vec3 u_colorV;

varying vec2 v_texCoord0;
 
void main() {
    float a = v_texCoord0.x;
    float b = v_texCoord0.y;
    float sum = 1 + a + b;
    float r = MAX_COLOR/sum;
    float g = MAX_COLOR*a/sum;
    float bl = MAX_COLOR*b/sum;
    gl_FragColor = vec4(
        r, g, bl, 1.0
    );
}