package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * FlashActivity: flash cards for user
 */

public class FlashActivity extends AppCompatActivity {
    private static final String EXTRA_OPTION= "option";
    TextView mTextView;
    Button mButton_Reveal;
    Button mButton_Next;
    List<Drug> mDrugList;
    DrugLab mDrugLab;
    int mListSize;
    int randIndex = 0;
    String questionOption = "";
    String origin;
    String mQuestion = "";
    int count = 0;

    // create the view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        origin = this.getIntent().getStringExtra(EXTRA_OPTION);

        //create interface
        mTextView = (TextView)findViewById(R.id.textView);
        mButton_Reveal = (Button) findViewById(R.id.button_reveal);
        mButton_Next = (Button) findViewById(R.id.button_next);

        mButton_Reveal.setEnabled(true);
        mButton_Next.setEnabled(false);

        // gets a drugList to use and gets the size
        getAllDrugsWithOrigin(origin);

        // initialize a boolean that will help keep track of what drugs i've already asked questions about
        final boolean mBoolArray[] = new boolean[mListSize];
        for(int i = 0 ; i < mListSize ; i++){
            mBoolArray[i] = false;
        }

        //choose a random drug
        do {
            randIndex = getRandomIndex();
        }while (mBoolArray[randIndex] == true || count == mListSize);

        //generate question and mark that I've asked them about the current drug
        createQuestion(randIndex);
        mBoolArray[randIndex] = true;


// reveal the answer "flash"
        mButton_Reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealAnswer(randIndex);
                mButton_Reveal.setEnabled(false);
                mButton_Next.setEnabled(true);

            }
        });

        mButton_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after an amount of quetions stop quiz
                if (count == mListSize ) {
                    mButton_Next.setEnabled(false);
                    mTextView.setText("All Done!");
                }
                else {
                    //make new question and answer
                    //getQuestionChoice();

                    do {
                        randIndex = getRandomIndex();
                    }while (mBoolArray[randIndex] == true || count == mListSize);

                    createQuestion(randIndex);
                    mBoolArray[randIndex] = true;

                    //disable next until user answers
                    mButton_Reveal.setEnabled(true);
                    mButton_Next.setEnabled(false);
                }
            }
        });
    }

    // query the database for the "type" selection (category, purpose, topic)
    //search database and returns list of rows of NOT NULL
    // save size of list in order to develop parallel list that holds boolean ...
    // used to determine if a drug has been flashed or not
    private void getAllDrugsWithOrigin(String origin){
        mDrugLab = DrugLab.get(getBaseContext());
        mDrugList = mDrugLab.getListOfNotNullFromCol(origin);
        mListSize = mDrugList.size();
    }

    private int getRandomIndex(){
        int rand = (int)( Math.random() * mListSize );
        return rand;
    }

    private void createQuestion(int rand){
        mQuestion = "What is the " + origin + " of " + mDrugList.get(rand).getGeneric_Name() + ".";
        mTextView.setText(mQuestion);
        count++;
    }

    private void revealAnswer(int rand){
        mQuestion = "The " + origin + " of " + mDrugList.get(rand).getGeneric_Name() + " is: ";
        switch (origin){
            case "Purpose":
                mQuestion = mQuestion + mDrugList.get(rand).getPurpose() + ".";
                break;
            case "Schedule":
                mQuestion = mQuestion + mDrugList.get(rand).getDEA_Sch() + ".";
                break;
            case "Special":
                mQuestion = mQuestion + mDrugList.get(rand).getSpecial() + ".";
                break;
            case "Category":
                mQuestion = mQuestion + mDrugList.get(rand).getCategory() + ".";
                break;
            case "Topic":
                mQuestion = mQuestion + mDrugList.get(rand).getStudy_Topic() + ".";
                break;
            default:
                mQuestion = "";
                break;
        }
        mTextView.setText(mQuestion);
    }
} // of FlashActivity
