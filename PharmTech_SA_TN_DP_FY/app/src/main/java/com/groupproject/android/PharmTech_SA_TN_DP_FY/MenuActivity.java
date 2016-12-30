package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * Main Menu, has buttons for quiz, study, about, and legal
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MenuActivity extends AppCompatActivity {
    private Button mStudyButton;
    private Button mQuizButton;
    private Button mAboutButton;
    private Button mLegalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //study button
        mStudyButton = (Button) findViewById(R.id.study_button);
        mStudyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //opens studybeactivity
                Intent i = new Intent(MenuActivity.this, StudyByActivity.class);
                startActivity(i);
            }
        });

        mQuizButton = (Button) findViewById(R.id.quiz_button);
        mQuizButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("MenuActivity", " quiz button cliced and should call QuizByGetAcatityv.class");
                // this used to call "ChoiceActivity.class"
                Intent i = new Intent(MenuActivity.this, QuizByGetActivity.class);
                startActivity(i);

            }

       });

        mLegalButton = (Button) findViewById(R.id.legal_button);
        mLegalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("MenuActivity", " quiz button cliced and should call QuizByGetAcatityv.class");
                // this used to call "ChoiceActivity.class"
                Intent i = new Intent(MenuActivity.this, LegalActivity.class);
                startActivity(i);

            }

        });

        mAboutButton = (Button) findViewById(R.id.about_button);
        mAboutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("MenuActivity", " quiz button cliced and should call QuizByGetAcatityv.class");
                // this used to call "ChoiceActivity.class"
                Intent i = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(i);

            }

        });
    }
}
