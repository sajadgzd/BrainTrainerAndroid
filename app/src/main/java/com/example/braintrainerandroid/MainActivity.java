package com.example.braintrainerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;

    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView sumTextView = findViewById(R.id.sumTextView);
        goButton = findViewById(R.id.goButton);

        Random rand = new Random();
        int a = rand.nextInt(21); // range 0 to 20
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));



    }
}