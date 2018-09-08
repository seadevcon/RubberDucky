package com.thoughtworks.rubberducky;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.widgets.RecordButton;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    private AppPhase appPhase = AppPhase.START;
    private RecordButton scanButton;
    private View statusBar;
    private View multipleMatchesScrollBar;
    private ProgressBar scanningProgress;
    private TextView statusBarText;
    private TextView thankYouText;
    private Handler handler = new Handler();



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
        scanningProgress = findViewById(R.id.scanning_progress);
        statusBarText = findViewById(R.id.status_bar_text);
        thankYouText = findViewById(R.id.thank_you_text);
        multipleMatchesScrollBar = findViewById(R.id.multiple_matches_scroll_bar);

        scanButton.setRecordButtonListener(new RecordButton.RecordButtonListener() {
            @Override
            public void onRecordButtonClicked() {
                appPhase = AppPhase.SCANNING;
                updateView();
                handler.postDelayed(new SwitchStateRunnable(), 5000);
            }
        });

        View firstImage = findViewById(R.id.first_image);
        firstImage.setClickable(true);
        firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PossibleMatches.class);
                startActivity(intent);
                appPhase = AppPhase.THANK_YOU;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        switch (appPhase) {
            case START:
                scanButton.displayPhotoState();
                statusBar.setVisibility(View.GONE);
                scanButton.setVisibility(View.VISIBLE);
                multipleMatchesScrollBar.setVisibility(View.GONE);
                thankYouText.setVisibility(View.GONE);
                break;
            case SCANNING:
                scanButton.displayVideoRecordStateReady();
                scanButton.setVisibility(View.VISIBLE);
                statusBar.setVisibility(View.VISIBLE);
                multipleMatchesScrollBar.setVisibility(View.GONE);
                statusBarText.setText("Scanning...");
                thankYouText.setVisibility(View.GONE);
                break;
            case MULTIPLE_MATCHES:
                scanningProgress.setVisibility(View.GONE);
                scanButton.setVisibility(View.GONE);
                multipleMatchesScrollBar.setVisibility(View.VISIBLE);
                statusBarText.setText("Multiple Matches");
                thankYouText.setVisibility(View.GONE);
                break;
            case THANK_YOU:
                scanningProgress.setVisibility(View.GONE);
                scanButton.setVisibility(View.GONE);
                multipleMatchesScrollBar.setVisibility(View.GONE);
                statusBar.setVisibility(View.GONE);
                thankYouText.setVisibility(View.VISIBLE);
                break;
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

    private class SwitchStateRunnable implements Runnable {

        @Override
        public void run() {
            appPhase = AppPhase.MULTIPLE_MATCHES;
            updateView();
        }
    }

}
