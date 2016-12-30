package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * AboutActivity: shows information about the app.
 */
public class AboutActivity extends AppCompatActivity {
    private TextView AboutTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AboutTV = (TextView) findViewById(R.id.about);
        String text = "PharmTech App by:\n" +
                "David Powell\n" +
                "Saadia Achouham\n" +
                "Thomas Newell\n" +
                "Fikir Yilma\n";
        AboutTV.setText(text);
    }
}