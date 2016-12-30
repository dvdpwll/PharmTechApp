package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * MocActivity: code for multiple choice quiz
 */

public class MocActivity extends AppCompatActivity {
    private int MaxQuestions = 10;
    private TextView mQuestionTextView;
    private RadioGroup mRadios;
    private RadioButton mRadioA;
    private RadioButton mRadioB;
    private RadioButton mRadioC;
    private RadioButton mRadioD;
    private Button mSubmit;
    private Button mNext;

    private List<Drug> mDrugList;
    private List<String> mPurposeList;
    private List<String> mStudyTopicList;
    private List<String> mCategoryList;

    private int mCurrentIndex = 0;
    String[] mUserAnswered = new String[MaxQuestions];
    String[] mQuestions = new String[MaxQuestions];
    String[] mActualAnswer = new String[MaxQuestions];
    private String question;
    private int abcdChoice;
    private int randDrugNum;
    private String origin = "";
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";

    //generate a new question and answer
    private void newQuestion(){
        //decide if answer will be true or false
        abcdChoice = randABCD();
        randDrugNum = getRandDrug();

        //create the question
        question = makeNewQuestion(abcdChoice, origin, randDrugNum);

        //show question
        mQuestionTextView.setText(question);

        //set correct answer to the appropriate radio
        if (abcdChoice == 0){
            a = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 1){
            b = getCorrectAnswer(randDrugNum, origin);
            a = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 2){
            c = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            a = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 3){
            d = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            a = getIncorrectAnswer(origin);
        }
        else {
            Log.d("ERROR", "Error with abcdChoice");
        }

        mRadioA.setText(a);
        mRadioB.setText(b);
        mRadioC.setText(c);
        mRadioD.setText(d);


    }

    //get a 0,1,2,3
    private int randABCD(){
        int rand = (int)( Math.random() * 4 );
        return rand;
    }

    //get 0-199
    private int getRandDrug(){
        int rand = (int)( Math.random() * 200 );
        return rand;
    }

    //get 0-79
    private int getRandPurpose(){
        int rand = (int)( Math.random() * 80 );
        return rand;
    }
    //get 0-12
    private int getRandStudyTopic(){
        int rand = (int)( Math.random() * 13 );
        return rand;
    }
    //get 0-51
    private int getRandCategory(){
        int rand = (int)( Math.random() * 52 );
        return rand;
    }

    //fill list with all drugs
    private void getDrugList(){
        mDrugList = new ArrayList<>();
        DrugLab drugLab = DrugLab.get(this);
        mDrugList = drugLab.getDrugs();
    }

    //fill list with all purposes
    private void getPurposeList(){
        mPurposeList = new ArrayList<>();
        mPurposeList = Arrays.asList(getResources().getStringArray(R.array.purpose_list));
    }
    //fill list with all study topic
    private void getStudyTopicList(){
        mStudyTopicList = new ArrayList<>();
        mStudyTopicList = Arrays.asList(getResources().getStringArray(R.array.study_topic_list));
    }
    //fill list with all category
    private void getCategoryList(){
        mCategoryList = new ArrayList<>();
        mCategoryList = Arrays.asList(getResources().getStringArray(R.array.category_list));
    }

    //pick a random purpose from list
    private String getFalsePurpose(){
        String fp = "";

        int randPurposeNum = getRandPurpose();
        fp = mPurposeList.get(randPurposeNum);
        return fp;
    }
    //pick a random Category from list
    private String getFalseCategory(){
        String fc = "";

        int randCategoryNum = getRandCategory();
        fc = mCategoryList.get(randCategoryNum);
        return fc;
    }
    //pick a random study topic from list
    private String getFalseStudyTopic(){
        String fs = "";

        int randStudyTopicNum = getRandStudyTopic();
        fs = mStudyTopicList.get(randStudyTopicNum);
        return fs;
    }

    private String getIncorrectAnswer(String o){
        String s = "-";

        while (s.equals("-")){
            if (o.equals("purpose")){
                s = getFalsePurpose();
                if (s.equals(a) || s.equals(b) || s.equals(c) || s.equals(d)){
                    s = "-";
                }
            }
            else if (o.equals("study topic")){
                s = getFalseStudyTopic();
                if (s.equals(a) || s.equals(b) || s.equals(c) || s.equals(d)){
                    s = "-";
                }
            }
            else if (o.equals("category")){
                s = getFalseCategory();
                if (s.equals(a) || s.equals(b) || s.equals(c) || s.equals(d)){
                    s = "-";
                }
            }
        }

        return s;
    }

    private String getCorrectAnswer(int rand, String o){
        String str = "";
        if (o.equals("purpose")){
            Drug drug = mDrugList.get(rand);
            str = drug.getPurpose();
        }
        else if (o.equals("study topic")){
            Drug drug = mDrugList.get(rand);
            str = drug.getStudy_Topic();
        }
        else if (o.equals("category")){
            Drug drug = mDrugList.get(rand);
            str = drug.getCategory();
        }
        return str;
    }

    //generate a question
    private String makeNewQuestion(int abcd, String o, int drugNum){
        //make variables
        String genericName;
        String question = "";

        //pick a random drug
        Drug d = mDrugList.get(drugNum);

        //get string info
        genericName = d.getGeneric_Name();

        question = "The " + o + " of " + genericName + " is: ";

        return question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moc);

        //get purpose, category, or study topic
        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");

        //make drug list
        getDrugList();

        //make lists
        getPurposeList();
        getStudyTopicList();
        getCategoryList();

        //decide if answer will be abcd
        abcdChoice = randABCD();
        randDrugNum = getRandDrug();


        //create the question
        question = makeNewQuestion(abcdChoice, origin, randDrugNum);

        //set correct answer to the appropriate radio
        if (abcdChoice == 0){
            a = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 1){
            b = getCorrectAnswer(randDrugNum, origin);
            a = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 2){
            c = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            a = getIncorrectAnswer(origin);
            d = getIncorrectAnswer(origin);
        }
        else if (abcdChoice == 3){
            d = getCorrectAnswer(randDrugNum, origin);
            b = getIncorrectAnswer(origin);
            c = getIncorrectAnswer(origin);
            a = getIncorrectAnswer(origin);
        }
        else {
            Log.d("ERROR", "Error with abcdChoice");
        }

        mQuestionTextView = (TextView) findViewById(R.id.tvQuestion);
        mQuestionTextView.setText(question);

        mRadios = (RadioGroup) findViewById(R.id.radioGroup1);
        mRadioA = (RadioButton) findViewById(R.id.radio0);
        mRadioB = (RadioButton) findViewById(R.id.radio1);
        mRadioC = (RadioButton) findViewById(R.id.radio2);
        mRadioD = (RadioButton) findViewById(R.id.radio3);
        mRadioA.setText(a);
        mRadioB.setText(b);
        mRadioC.setText(c);
        mRadioD.setText(d);

        mSubmit = (Button) findViewById(R.id.btnSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int choice = mRadios.getCheckedRadioButtonId();

                switch(choice){
                    case R.id.radio0:
                        mUserAnswered[mCurrentIndex] = mRadioA.getText().toString();
                        break;
                    case R.id.radio1:
                        mUserAnswered[mCurrentIndex] = mRadioB.getText().toString();
                        break;
                    case R.id.radio2:
                        mUserAnswered[mCurrentIndex] = mRadioC.getText().toString();
                        break;
                    case R.id.radio3:
                        mUserAnswered[mCurrentIndex] = mRadioD.getText().toString();
                        break;
                    default:
                        Log.d("ERROR", "Switch error");
                        break;
                }

                //save what the user answered for current question
                mQuestions[mCurrentIndex] = question;
                mActualAnswer[mCurrentIndex] = getCorrectAnswer(randDrugNum, origin);
                mCurrentIndex++;

                //disable buttons until next is pressed
                mSubmit.setEnabled(false);
                mNext.setEnabled(true);
            }
        });
        mNext = (Button) findViewById(R.id.btnNext);
        mNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //after an amount of quetions stop quiz
                if (mCurrentIndex == MaxQuestions ) {
                    mNext.setEnabled(false);

                    //Intent intent = new Intent(TofpActivity.this, ToFResults.class);
                    Intent intent = new Intent(MocActivity.this, MocResults.class);
                    Bundle b = new Bundle();
                    b.putStringArray("userAnswered", mUserAnswered);
                    b.putStringArray("questions", mQuestions);
                    b.putStringArray("actualAnswers", mActualAnswer);
                    intent.putExtras(b);
                    startActivity(intent);

//                    for (int i = 0; i < mCurrentIndex; i ++) {
//                        Log.d("ENDOFTEST", mQuestions[i] + ", " + mUserAnswered[i] + ", " + mActualAnswer[i]);
//                    }
                }
                else {
                    //make new question and answer
                    newQuestion();

                    //disable next until user answers
                    mSubmit.setEnabled(true);
                    mNext.setEnabled(false);
                }
            }
        });

        mNext.setEnabled(false);
    }
}
