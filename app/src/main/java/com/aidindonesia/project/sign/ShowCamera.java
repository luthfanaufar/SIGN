package com.aidindonesia.project.sign;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Aufar on 10/01/2015.
 */
public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holdMe;
    private Camera theCamera;

    public ShowCamera(Context context,Camera camera) {
        super(context);
        theCamera = camera;
        holdMe = getHolder();
        holdMe.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try   {
            Camera.Parameters parameters = theCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            parameters.setPictureSize(1280,720);
           // parameters.setPictureSize(sizes.get(0).width, sizes.get(0).height); // mac dinh solution 0
            parameters.set("orientation","landscape");
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            List<Camera.Size> size = parameters.getSupportedPreviewSizes();
            //parameters.setPreviewSize(size.get(0).width, size.get(0).height);
//            parameters.setPreviewSize(640,480);
            theCamera.setParameters(parameters);
            theCamera.setPreviewDisplay(holder);
            theCamera.startPreview();
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

}