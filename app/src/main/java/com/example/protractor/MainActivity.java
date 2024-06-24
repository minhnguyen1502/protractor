package com.example.protractor;// MainActivity.java

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView angleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angleTextView = findViewById(R.id.angleTextView);

//        angleMeasureView.setOnAngleChangeListener(new AngleMeasureView.OnAngleChangeListener() {
//            @Override
//            public void onAngleChange(float angle) {
//                angleTextView.setText(String.format("%.2f°", angle));
//            }
//        });
    }
}
