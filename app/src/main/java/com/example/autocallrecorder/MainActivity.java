package com.example.autocallrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references
        button = findViewById(R.id.record_button);

        button.setOnClickListener(view -> {
            if (!isRecording){
                isRecording = true;
                Toast.makeText(this, "Recording Started!", Toast.LENGTH_SHORT).show();
            }
            else{
                isRecording = false;
                Toast.makeText(this, "Recording Stopped!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}