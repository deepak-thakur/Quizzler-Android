package com.Deepak_Thakur.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


    // TODO: Declare member variables here:

    Button mtruebutton;
    Button mfalsebutton;
    TextView mquestiontextview;
    int mindex;
    int mquestion;
    int mscore;
    TextView mscoretextview;
    ProgressBar mprogressbar;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };


    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREAMENT = (int) Math.ceil(100.0 / mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mscore=savedInstanceState.getInt("Scorekey");
            mindex = savedInstanceState.getInt("indexkey");
        }else {
            mscore = 0;
            mindex = 0;
        }


        mtruebutton = (Button) findViewById(R.id.true_button);
        mfalsebutton = (Button) findViewById(R.id.false_button);
        mquestiontextview = (TextView) findViewById(R.id.question_text_view);
        mscoretextview = (TextView) findViewById(R.id.score);
        mprogressbar = (ProgressBar) findViewById(R.id.progress_bar);



        // mindex variable is used to count the question no and is assigned to first question variable
        // mquestion variable is used to store the resourse id of the question returned by getquestioid method
        //in the truefalse java class and .getmquestion id is used for shwing them.

        mquestion = mQuestionBank[mindex].getMquestionid();

        //now the question is set to the textview for displaying he question.

        mquestiontextview.setText(mquestion);
        mscoretextview.setText("Score" + mscore + "/" + mQuestionBank.length);

        // this is the onclick listern for truebutton
        View.OnClickListener mylistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkanswe(true);
                updatequestion();


            }
        };
        // this is the true button to which i have provided the parameter mylistner variable.
        mtruebutton.setOnClickListener(mylistner);


        //this is the false button to wichi i have provided the onclick lister dirctly
        // without the use of variable. it has no name. we has declared like this because in nxt program it doesnt have any use
        mfalsebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkanswe(false);
                updatequestion();


            }
        });


    }

    private void updatequestion() {
        // here the mindex variable vvalue contains the number performed by modulus operation.
        // whn the value of mindex is 12 and it gets + 1 the value becomes 13 and then again it adds 1
        // then it again calculate the valur 13+1 =14 but there is no 14th question therefore we get array out of bound error.
        //to ovecome this the modulous operation is performed when the mindex conains 12+1= 13 % numberofquestion it
        //returns 0 therefore it starts again from first question.

        mindex = (mindex + 1) % mQuestionBank.length;
        mquestion = mQuestionBank[mindex].getMquestionid();
        mquestiontextview.setText(mquestion);
        mprogressbar.incrementProgressBy(PROGRESS_BAR_INCREAMENT);
        mscoretextview.setText("Score" + mscore + "/" + mQuestionBank.length);



        //this code generates the alert dialogue box when you complete the questions

        if (mindex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over"); // title of the dialogue box
            alert.setCancelable(false); //it prevents app from being quit by touching anywhere on the screen.
            alert.setMessage("You Scored " + mscore + " Points !"); // this shows the score

            // this is onclick listner on the alert dialogue box button.
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish(); // this closes the application

                }
            });

            alert.show(); // this method shows the alert box.

        }

    }

    private void checkanswe(boolean userselection) {

        boolean correctanswer = mQuestionBank[mindex].isManswer();

        if (userselection == correctanswer) {

            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mscore = mscore + 1;

        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();


        }


    }

        @Override
        public void onSaveInstanceState(Bundle outstate)
        {
            super.onSaveInstanceState(outstate);
            outstate.putInt("Scorekey",mscore);
            outstate.putInt("indexkey",mindex);



        }



}


