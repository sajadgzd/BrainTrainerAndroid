package sajad.braintrainerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import sajad.braintrainerandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("highestScore");

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
    TextView highestScoreTextView;

    int unicodeWin = 0x1F44F;
    int unicodeLose = 0x1F44E;
//    int unicodeAgain = 	0x1F64C;


    // ArrayList to hold the Integers, one of which is correct answer to the question,
    // other 3 are randomly generated wrong Integers
    ArrayList<Integer> answers = new ArrayList<>();

    // Array to store scores
    ArrayList<Integer> scoresLst = new ArrayList<>();

    // ArrayList to hold random welcome messages
    ArrayList<String> welcomeMsgs = new ArrayList<String>(){
        {
            add("Wanna Train Your Brain?");
            add("Let's play!");
            add("Ready for challenge?");
            add("Got 30 seconds to play?!");
        }
    };


    // play again is clicked or game starts
    public void playAgain(View view){
        // reset the game
        tableLayout.setVisibility(View.VISIBLE);

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText(" s");
        scoreTextView.setText((score) + "/" + (numberOfQuestions));

        // update highest score from database, add the highest to scoresLst
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Log.i("READ VALUE: ", "Value is: " + value);

                // if database exists update, else zero
                if(value == null){
                    scoresLst.add(score);
                    highestScoreTextView.setText("Highest Score " + score);
                } else {
                    scoresLst.add(Integer.parseInt(value));
                    highestScoreTextView.setText("Highest Score " + value);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("ERROR:: ", "Failed to read value.", error.toException());
            }
        });


        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        // Timer starts meanwhile user is playing until time's up & the tableLayout become invisible
        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                // get the highest score and show to the user
                resultTextView.setText("Time's up!");

                // update highest score from database when finish
                readFromDataBase();

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
            resultTextView.setText("Correct! " +  new String(Character.toChars(unicodeWin)));
            score++;
        } else {
            Log.i("Wrong!", "you Lose!");
            resultTextView.setText("Wrong! " + new String(Character.toChars(unicodeLose)));

        }
        //increment the total num of questions
        numberOfQuestions++;
        scoreTextView.setText((score) + "/" + (numberOfQuestions));
        scoresLst.add(score);

//
        // write to DB
        myRef.setValue(Integer.toString(Collections.max(scoresLst)));

        // update highest score from database to display
        readFromDataBase();

        newQuestion();
    }

    // creating new questions with random ints
    public void newQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21); // range 0 to 20
        int b = rand.nextInt(21);

        // updating the question text
        sumTextView.setText((a) + " + " + (b));

        // set the location of correct answer - 0 to 3 locations
        locationOfCorrectAnswer = rand.nextInt(4);

        //clear out the array of answers before the next round of question
        answers.clear();

        // add one correct answer and three wrong answers to answers array
        for (int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);

                // wrongAnswer cannot be a+b or repetitive
                while(wrongAnswer == a+b || answers.contains(wrongAnswer))
                    wrongAnswer = rand.nextInt(41);

                answers.add(wrongAnswer); // adding the wrong answer to answers list to show up
            }

        }
        // update the button texts
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void readFromDataBase(){
        // update highest score from database
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i("READ VALUE: ", "Value is: " + value);
                highestScoreTextView.setText("Highest Score " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("ERROR:: ", "Failed to read value.", error.toException());
            }
        });
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
        playAgainButton.setText("Play Again! "
//                + new String(Character.toChars(unicodeAgain))
        );
        gameLayout = findViewById(R.id.gameLayout);
        tableLayout = findViewById(R.id.tableLayout);
        welcomeTextView = findViewById(R.id.welcomeTextView);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);

        // Go button and Welcome message show up at landing page
        goButton.setVisibility(View.VISIBLE);
        Random rand = new Random();
        welcomeTextView.setText(welcomeMsgs.get(rand.nextInt(welcomeMsgs.size())));
        welcomeTextView.setVisibility(View.VISIBLE);
        // game layout is invisible at the landing page
        gameLayout.setVisibility(View.INVISIBLE);



    }
}
