package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * QuizActivity: choose mc or tf
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity {
    private Button mTofButton;
    private Button mMocButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTofButton = (Button) findViewById(R.id.tof_button);
        mTofButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent i = new Intent(QuizActivity.this,TofActivity.class);
                startActivity(i);

            }
        });

        mMocButton = (Button)findViewById(R.id.moc_button);
        mMocButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


            }
        });

    }
}
