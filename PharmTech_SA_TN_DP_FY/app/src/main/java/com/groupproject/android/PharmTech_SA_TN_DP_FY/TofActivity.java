package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * TofActivity: User takes a True/False quiz
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toast;

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TofActivity extends AppCompatActivity {
    private static final String TAG = "TOFACTIVITY";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TextView mResultTV;

    private String origin = "";
    private int MaxQuestions = 10;
    private int mCurrentIndex = 0;
    private int tOrF;

    boolean[] mUserAnswered = new boolean[MaxQuestions];
    boolean[] mActualAnswer = new boolean[MaxQuestions];
    String[] mQuestions = new String[MaxQuestions];
    String[] mTextAnswer = new String[MaxQuestions];

    // these list variables will query the database for their lists
    private List<Drug> mDrugList;
    private List<Drug> mDistinctList;

    private void updateQuestion(){
        tOrF = randTorF();
        String subjectQues = "";
        int i = getRandomIndex();
        if (tOrF == 1){
            if (origin.equals("category")){
                subjectQues = mDrugList.get(i).getCategory();
            }
            else if (origin.equals("purpose")){
                subjectQues = mDrugList.get(i).getPurpose();
            }
            else if (origin.equals("topic")){
                subjectQues = mDrugList.get(i).getStudy_Topic();
            }
            else {
                subjectQues = "ERROR";
            }
            mActualAnswer[mCurrentIndex] = true;
        }
        else {
            subjectQues = getFalseSubjectQues();
            mActualAnswer[mCurrentIndex] = false;
        }
        String questionText = "The " + origin + " of " + mDrugList.get(i).getGeneric_Name() + " is " + subjectQues + ".";
        mQuestionTextView.setText(questionText);

        mQuestions[mCurrentIndex] = questionText;

        if (origin.equals("category")){
            mTextAnswer[mCurrentIndex] = mDrugList.get(i).getCategory();
        }
        else if (origin.equals("purpose")){
            mTextAnswer[mCurrentIndex] = mDrugList.get(i).getPurpose();
        }
        else if (origin.equals("topic")){
            mTextAnswer[mCurrentIndex] = mDrugList.get(i).getStudy_Topic();
        }
        else {
            subjectQues = "ERROR!!";
        }
    }

    //get a 1 or 0
    private int randTorF(){
        int rand = (int)( Math.random() * 2 );
        return rand;
    }

    // happens every time a new question occurs
    private int getRandomIndex (){
        int index = (int)(Math.random() * 200);
        return index;
    }

    // receives size so random method knows limit, passes new random index back, happens every question
    private int getDistinctRandomIndex(int size){
        int index = (int)(Math.random() * size);

        return index;
    }

    //pick a random subject question from list
    private String getFalseSubjectQues(){
        String sub = "";

        int randNum = getDistinctRandomIndex(mDistinctList.size());

        if (origin.equals("category")){
            sub = mDistinctList.get(randNum).getCategory();
        }
        else if (origin.equals("purpose")){
            sub = mDistinctList.get(randNum).getPurpose();
        }
        else if (origin.equals("topic")){
            sub = mDistinctList.get(randNum).getStudy_Topic();
        }
        else {
            sub = "ERROR!";
        }

        return sub;
    }

    // queries database for a list, just happens once
    private void getArrayList(){

        mDrugList = new ArrayList<>();
        DrugLab drugLab = DrugLab.get(this);
        mDrugList = drugLab.getDrugs();
    }

    // queries database for a distinct list of values, just happens once
    private void queryForDistinctList(){

        mDistinctList = new ArrayList<>();
        DrugLab drugLab = DrugLab.get(this);
        mDistinctList = drugLab.getDistinctList(origin);
    }


    private void showAnswer(String result){
        if (result == "correct"){
            mResultTV.setText("Correct");
        }
        else{
            mResultTV.setText("Incorrect");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tof);

        //get purpose, category, or study topic
        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");//category, purpose, study topic

        getArrayList();
        queryForDistinctList();

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mResultTV = (TextView) findViewById(R.id.result_text_view);
        mNextButton = (Button) findViewById(R.id.next_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);

        mNextButton.setEnabled(false);

        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (tOrF == 1){
                    showAnswer("correct");
                }
                else {
                    showAnswer("incorrect");
                }
                mUserAnswered[mCurrentIndex] = true;
                //disable buttons until next is pressed
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                mNextButton.setEnabled(true);
                mCurrentIndex++;
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (tOrF == 1){
                    showAnswer("incorrect");
                }
                else {
                    showAnswer("correct");
                }
                mUserAnswered[mCurrentIndex] = false;
                //disable buttons until next is pressed
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                mNextButton.setEnabled(true);
                mCurrentIndex++;
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mCurrentIndex == (MaxQuestions)){
                    mNextButton.setEnabled(false);
//                    for (int i = 0; i < MaxQuestions; i++){
//                        Log.d(TAG, "Question" + i + ": " + mQuestions[i]);
//                        Log.d(TAG, "User Answered" + i + ": " + mUserAnswered[i]);
//                        Log.d(TAG, "Actual Answer" + i + ": " + mActualAnswer[i]);
//                        Log.d(TAG, "Text Answer" + i + ": " + mTextAnswer[i]);
//                    }
                    //                    Log.d(TAG, "All Done!");

                    //Intent intent = new Intent(TofpActivity.this, ToFResults.class);
                    Intent intent = new Intent(TofActivity.this, ToFResults.class);
                    Bundle b = new Bundle();
                    b.putBooleanArray("userAnswered", mUserAnswered);
                    b.putStringArray("questions", mQuestions);
                    b.putBooleanArray("actualAnswers", mActualAnswer);
                    b.putStringArray("textAnswer", mTextAnswer);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                else{
                    updateQuestion();
                    mResultTV.setText("---");
                    //disable buttons until next is pressed
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mNextButton.setEnabled(false);
                }
            }
        });
    }
}
