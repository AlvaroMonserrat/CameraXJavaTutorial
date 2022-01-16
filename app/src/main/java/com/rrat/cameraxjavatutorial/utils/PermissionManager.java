package com.rrat.cameraxjavatutorial.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.rrat.cameraxjavatutorial.MainActivity;

public class PermissionManager {

    private final ActivityResultLauncher<String> requestPermissionLauncher;
    private final Context mContext;

    public PermissionManager(Context context, ActivityResultRegistry activityResultRegistry) {

        mContext = context;
        //Interface to get Result of permission request.
        ActivityResultCallback<Boolean> activityResultCallback = isGranted -> {
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                Log.i("PERMISSIONS", "ACEPTADOS");
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                Log.i("PERMISSIONS", "DENEGADOS");
            }
        };

        //Create Activity Result Contracts Request Permission
        ActivityResultContracts.RequestPermission activityContract = new ActivityResultContracts.RequestPermission();


        requestPermissionLauncher = activityResultRegistry.register(
                "Read Camera Permissions",
                activityContract,
                activityResultCallback);

    }

    public boolean allPermissionGranted()
    {
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            //DO SOMETHING
            Log.i("PERMISSIONS", "Permisos OK");
            return true;

        }else{

            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            Log.i("PERMISSIONS", "Solicitud Permisos");
            return false;
        }
    }

}
