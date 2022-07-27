package com.example.autocallrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    private boolean isRecording = false;
    public static final int RECORD_AUDIO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);

        }

        //references
        button = findViewById(R.id.record_button);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this,RecordingService.class);
            if (!isRecording){
                button.setText("Stop Recording");
                startService(intent);
                isRecording = true;
                Toast.makeText(this, "Recording Started!", Toast.LENGTH_SHORT).show();
            }
            else{
                button.setText("Start Recording");
                stopService(intent);
                isRecording = false;
                Toast.makeText(this, "Recording Stopped!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}