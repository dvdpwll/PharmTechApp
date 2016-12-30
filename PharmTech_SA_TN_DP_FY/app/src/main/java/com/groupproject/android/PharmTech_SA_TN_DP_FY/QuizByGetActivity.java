package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * AboutActivity: user chooses whaqt kind of quiz to take
 */

public class QuizByGetActivity extends AppCompatActivity{

    private Spinner mSpinner;
    private Spinner mSpinner2;
    private Button mSubmit;
    //private TextView mQuestionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QuizByGetActivity", " Inside onCreate");
        setContentView(R.layout.activity_quiz_by);



        mSpinner = (Spinner) findViewById(R.id.study_by_spinner);
        mSpinner2 = (Spinner) findViewById(R.id.quiz_by_spinner);
        mSubmit = (Button) findViewById(R.id.study_by_btn);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = String.valueOf(mSpinner.getSelectedItem());
                String type = String.valueOf(mSpinner2.getSelectedItem());
                String origin = "";

                Intent i;
                if ("Multiple Choice".equals(type)){
                    // Multiple Choice Quiz
                    i = new Intent(QuizByGetActivity.this, com.groupproject.android.PharmTech_SA_TN_DP_FY.MocActivity.class);
                }
                else {
                    // True or False Quiz
                    i = new Intent(QuizByGetActivity.this, com.groupproject.android.PharmTech_SA_TN_DP_FY.TofActivity.class);
                }
                /*else {
                    // FlashCard Quiz
                    i = new Intent(QuizByGetActivity.this, com.groupproject.android.PharmTech_SA_TN_DP_FY.FlashActivity.class);
                }*/
                i.putExtra("origin", item);

                startActivity(i);
            }

        });
    }
}