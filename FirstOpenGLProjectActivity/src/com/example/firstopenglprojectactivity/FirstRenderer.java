package com.example.firstopenglprojectactivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

public class FirstRenderer implements Renderer{

    private static final int POSITION_COMPONENT_COUNT = 2;
    
    public FirstRenderer () {
        // attribute array
        float[] tableVertices = {
                // triangle1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                
                // triangle2
                0f, 0f,
                9f, 0f,
                9f, 14f,
                
                // line1
                0f, 7f,
                9f, 7f,
                
                // mallets
                4.5f, 2f,
                4.5f, 12f
        };
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

}
