package com.example.braintrainerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;

    //Array to hold the numbers
    ArrayList<Integer> answers = new ArrayList<>();

    // Go button disappears once clicked
    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
    }

    public void chooseAnswer(View view){
        Log.i("tag: ",view.getTag().toString()) ;
        // checking to see if the choice is correct or wrong
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            Log.i("Correct!!", "You win");
            resultTextView.setText("Correct! :)");
            score++;
        } else {
            Log.i("Wrong!", "you Lose!");
            resultTextView.setText("Wrong! :(");

        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView sumTextView = findViewById(R.id.sumTextView);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        goButton = findViewById(R.id.goButton);

        Random rand = new Random();
        int a = rand.nextInt(21); // range 0 to 20
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        // set the location of correct answer
        locationOfCorrectAnswer = rand.nextInt(4);

        for (int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == a+b)
                    wrongAnswer = rand.nextInt(41);

                answers.add(wrongAnswer); // adding the wrong answers up to 40
            }

        }
        // update the button values
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));






    }
}
