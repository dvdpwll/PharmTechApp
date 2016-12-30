package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * StudyActivity: user chooses what drugs to view
 */

public class StudyActivity extends AppCompatActivity {
    Spinner mSpinner;
    //ArrayAdapter<CharSequence> mAdapter;
    private final static String EXTRA_STRING_SELECTION = "where";
    List<Drug> mDrugList = new ArrayList<>();
    List<String> mSpinnerItemList = new ArrayList<>();
    //String mItem;
    DrugLab mDrugLab;
    String thing;
    Button mStudyActBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Log.d("StudyActivity", " in onCreate");

        final String mItem = this.getIntent().getStringExtra(EXTRA_STRING_SELECTION);

        mDrugLab = DrugLab.get(getBaseContext());
        mDrugList = mDrugLab.getDistinctList(mItem);


        for(Drug d:mDrugList){
            switch (mItem){
                case "purpose": thing = d.getPurpose();
                    Log.d("StudyActivity", mItem + " " + thing);
                    break;
                case "category": thing = d.getCategory();
                    Log.d("StudyActivity", mItem + " " + thing);
                    break;
                case "special": thing = d.getSpecial();
                    Log.d("StudyActivity", mItem + " " + thing);
                    break;
                case "schedule": thing = d.getDEA_Sch();
                    Log.d("StudyActivity", mItem + " " + thing);
                    break;
                case "topic": thing = d.getStudy_Topic();
                    Log.d("StudyActivity", mItem + " " + thing);
                    break;
            }
            mSpinnerItemList.add(thing);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, mSpinnerItemList);

        mSpinner = (Spinner)findViewById(R.id.study_spinner);
        mStudyActBtn = (Button)findViewById(R.id.study_activity_btn);

        mSpinner.setAdapter(adapter);
        mStudyActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = String.valueOf(mSpinner.getSelectedItem());
                Intent i = new Intent(StudyActivity.this, com.groupproject.android.PharmTech_SA_TN_DP_FY.DrugListActivity.class);
                i.putExtra("where", mItem);
                i.putExtra("args", selected);
                startActivity(i);
            }
        });



    }
}
