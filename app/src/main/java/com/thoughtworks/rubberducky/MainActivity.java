package com.thoughtworks.rubberducky;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.widgets.RecordButton;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    private boolean isScanning = false;
    private RecordButton scanButton;
    private View statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

        } else {
            openCamera();
        }

        scanButton = findViewById(R.id.scan_button);
        statusBar = findViewById(R.id.status_bar);


        scanButton.setRecordButtonListener(new RecordButton.RecordButtonListener() {
            @Override
            public void onRecordButtonClicked() {
                isScanning = !isScanning;
                updateView();
            }
        });
    }

    private void updateView() {
        if (isScanning) {
            scanButton.displayVideoRecordStateReady();
            statusBar.setVisibility(View.VISIBLE);
            //NEW STUFF DELETE
            Intent intent = new Intent(this, PossibleMatches.class);
            startActivity(intent);
        } else {
            scanButton.displayPhotoState();
            statusBar.setVisibility(View.GONE);
        }
    }

    private void openCamera() {
        @SuppressLint("MissingPermission") CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_view, cameraFragment, null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // when cam permission either yes or no
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
