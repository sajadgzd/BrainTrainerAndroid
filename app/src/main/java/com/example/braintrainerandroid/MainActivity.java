package com.example.braintrainerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
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
    TextView timerTextView;
    TextView sumTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    TextView welcomeTextView;
    TableLayout tableLayout;

    //Array to hold the numbers
    ArrayList<Integer> answers = new ArrayList<>();

    // play again is clicked or game starts
    public void playAgain(View view){
        // reset the game
        tableLayout.setVisibility(View.VISIBLE);
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText(" s");
        scoreTextView.setText((score) + "/" + (numberOfQuestions));

        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        new CountDownTimer(4100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Time's up!");
                timerTextView.setText("0s");
                playAgainButton.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.INVISIBLE);

            }
        }.start();

    }

    // Go button disappears once clicked
    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        welcomeTextView.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.timerTextView));
        gameLayout.setVisibility(View.VISIBLE);
    }

    // user chooses the answer
    public void chooseAnswer(View view){
        // Log.i("tag: ",view.getTag().toString()) ;

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
        scoreTextView.setText((score) + "/" + (numberOfQuestions));
        newQuestion();
    }

    // creating new questions with random ints
    public void newQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21); // range 0 to 20
        int b = rand.nextInt(21);

        sumTextView.setText((a) + " + " + (b));

        // set the location of correct answer
        locationOfCorrectAnswer = rand.nextInt(4);

        //clear out the array of answers before the next round of questions
        answers.clear();

        // add one correct answer and three wrong answers to answers array
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
        // update the button texts
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        tableLayout = findViewById(R.id.tableLayout);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        // Go button and Welcome message show up at landing page
        goButton.setVisibility(View.VISIBLE);
        welcomeTextView.setVisibility(View.VISIBLE);
        // game layout is invisible at the landing page
        gameLayout.setVisibility(View.INVISIBLE);



    }
}
