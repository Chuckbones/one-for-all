package com.example.oneforall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.*;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String url="192.168.1.4:5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = findViewById(R.id.textView);
        String tContents = "";

        try {
            InputStream stream = getAssets().open("test3.txt");

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        textView.setText(tContents);

    }
}