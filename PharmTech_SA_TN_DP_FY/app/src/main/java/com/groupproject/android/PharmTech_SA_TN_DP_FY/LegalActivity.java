package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * LegalActivity: shows legal information
 */

public class LegalActivity extends AppCompatActivity {
    private TextView LegalTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        LegalTV = (TextView) findViewById(R.id.legal);
        String text = "Legal Stuff";
        LegalTV.setText(text);
    }
}
