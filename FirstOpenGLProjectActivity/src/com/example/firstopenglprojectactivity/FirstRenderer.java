package com.example.firstopenglprojectactivity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class FirstRenderer implements Renderer{

    private static final int POSITION_COMPONENT_COUNT = 2;
    
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    
    private Context context;
    
    private int program;
    
    private static final String A_POSITION = "a_Position";
    private int aPositonLocation;
    
    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    
    private int aColorLocation;
    
    private static final String U_MATRIX = "u_Matrix";
    private final float[] projectionMatrix = new float[16];
    private int uMatrixLocation;
    
    public FirstRenderer (Context context) {
        // attribute array
        float[] tableVertices = {
                /*// triangle1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                
                // triangle2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,*/
                
                // triangle fans
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.8f,  0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                
                // line1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,
                
                // mallets
                0f, -0.4f, 0f, 0f, 1f,
                0f, 0.4f, 1f, 0f, 0f
        };
        
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        
        vertexData.put(tableVertices);
        
        this.context = context;
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        
        // triangles
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
        
        // lines
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
        
        // points
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        
        final float aspectRatio = width > height ? (float) width / (float) height : (float) height/ (float) width;
        if(width > height)
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        else
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        
        ShaderHelper.validateProgram(program);
        
        GLES20.glUseProgram(program);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.cimpileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        aPositonLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositonLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositonLocation);
        
        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        
        GLES20.glEnableVertexAttribArray(aColorLocation);
        
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
    }

}
