package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * studyByActivity: user chooses what to study
 */

public class StudyByActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mStudyAllButton;
    private Button mPurposeButton;
    private Button mDeaSchButton;
    private Button mSpecialButton;
    private Button mCategoryButton;
    private Button mStudyTopicButton;
    private Button mStudyByFlashBtn;
    private String where;
    private String option;
    private Spinner mFlashSpinner;
    private Button mSubmitFlashBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_by);

        Intent intent = new Intent(StudyByActivity.this, StudyActivity.class);

        //purpose button
        mStudyAllButton = (Button) findViewById(R.id.study_all_button);
        mStudyAllButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(StudyByActivity.this, DrugListActivity.class);
                String where = null;
                String args = null;
                i.putExtra("where", where);
                i.putExtra("args", args);
                startActivity(i);
            }
        });


        //purpose button
        mPurposeButton = (Button) findViewById(R.id.pupose_button);
        mPurposeButton.setOnClickListener(this);

        //DEA Sch Button
        mDeaSchButton = (Button) findViewById(R.id.DEA_sch_button);
        mDeaSchButton.setOnClickListener(this);

        //special button
        mSpecialButton = (Button) findViewById(R.id.special_button);
        mSpecialButton.setOnClickListener(this);

        //category button
        mCategoryButton = (Button) findViewById(R.id.category_button);
        mCategoryButton.setOnClickListener(this);

        //study topic button
        mStudyTopicButton = (Button) findViewById(R.id.study_topic_button);
        mStudyTopicButton.setOnClickListener(this);

        // study by flash card
        mStudyByFlashBtn = (Button) findViewById(R.id.flash_card_button);
        mStudyByFlashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlashSpinner.setVisibility(View.VISIBLE);
                mSubmitFlashBtn.setVisibility(View.VISIBLE);
            }
        });

        mFlashSpinner = (Spinner)findViewById(R.id.flash_spinner);
        mFlashSpinner.setVisibility(View.INVISIBLE);


        mSubmitFlashBtn = (Button)findViewById(R.id.flash_spinner_button);
        mSubmitFlashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = String.valueOf(mFlashSpinner.getSelectedItem());
                int position = mFlashSpinner.getSelectedItemPosition();
                if (position != 0) {
                    startFlashActivity(option);
                }
            }
        });



    } // end of onCreate

    // handles button clicks on Purpose, DEA_Schedule, Special, Category, Topic
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(StudyByActivity.this, StudyActivity.class);
        switch (v.getId()){
            case R.id.pupose_button:
                where = "purpose";
                break;
            case R.id.DEA_sch_button:
                where = "schedule";
                break;
            case R.id.special_button:
                where = "special";
                break;
            case R.id.category_button:
                where = "category";
                break;
            case R.id.study_topic_button:
                where = "topic";
                break;
        }
        intent.putExtra("where", where);
        startActivity(intent);
    }

    // handles button click on Flash Card
    public void startFlashActivity(String selectedSpinner) {
            Intent intent = new Intent(StudyByActivity.this, FlashActivity.class);
            intent.putExtra("option", selectedSpinner);
            startActivity(intent);
            mFlashSpinner.setVisibility(View.INVISIBLE);
            mSubmitFlashBtn.setVisibility(View.INVISIBLE);
    }
}