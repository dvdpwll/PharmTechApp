package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;


/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * TofResults: shows resutls of tf quiz
 */

public class ToFResults extends AppCompatActivity {
    private int MaxQuestions = 10;
    private TextView mQuestion0;

    boolean[] mUserAnswered = new boolean[MaxQuestions];
    String[] mQuestions = new String[MaxQuestions];
    boolean[] mActualAnswer = new boolean[MaxQuestions];
    String[] mTextAnswer = new String[MaxQuestions];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tof_results);

        //Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        mUserAnswered = b.getBooleanArray("userAnswered");
        mQuestions = b.getStringArray("questions");
        mActualAnswer = b.getBooleanArray("actualAnswers");
        mTextAnswer = b.getStringArray("textAnswer");

        //make string to display all questions
        String display = "";
        for (int i = 0; i < MaxQuestions; i++){
            if (mUserAnswered[i] == mActualAnswer[i]){
                display = display +
                        "Question " + (i + 1) + ": " + mQuestions[i] +
                        "<br>User Answer: " + String.valueOf(mUserAnswered[i]) +
                        "<br>Actual Answer: " + String.valueOf(mActualAnswer[i]) + ", " + mTextAnswer[i] +
                        "<br><br>";
            }
            else {
                display = display +
                        "<font color='#EE0000'>Question " + (i + 1) + ": " + mQuestions[i] +
                        "<br>User Answer: " + String.valueOf(mUserAnswered[i]) +
                        "<br>Actual Answer: " + String.valueOf(mActualAnswer[i]) + ", " + mTextAnswer[i] +
                        "<br><br></font>";
            }
        }

        //display results for each question
        mQuestion0 = (TextView) findViewById(R.id.results_q0);
        mQuestion0.setText(Html.fromHtml(display));
    }
}
