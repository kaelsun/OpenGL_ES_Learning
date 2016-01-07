package com.example.firstopenglprojectactivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        //setContentView(R.layout.activity_main);
        
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo config = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = config.reqGlEsVersion >= 0x20000;
        
        if(supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new FirstRenderer());
            rendererSet = true;
        } else {
            Toast.makeText(this, "This device do not support OpenGL ES 2.0", Toast.LENGTH_SHORT);
            return;
        }
        setContentView(glSurfaceView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if(rendererSet)
            glSurfaceView.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if(rendererSet)
            glSurfaceView.onResume();
    }

}
