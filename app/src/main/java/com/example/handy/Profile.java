package com.example.handy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    Button btnTaskViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnTaskViewer = findViewById(R.id.btnTaskviewer);
        btnTaskViewer.setOnClickListener(v -> {
            Intent i = new Intent(Profile.this, TaskHome.class);
            startActivity(i);
        });
    }
}
