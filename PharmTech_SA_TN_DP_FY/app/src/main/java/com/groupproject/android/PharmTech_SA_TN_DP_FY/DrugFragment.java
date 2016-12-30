package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugFragment: show a single drug from drug list fragment
 */

public class DrugFragment extends Fragment {
    private static final String ARG_DRUG_ID = "drug_id"; // added from p 198
    private Drug mDrug;
    private TextView mGeneric_tv;
    private TextView mBrand_tv;
    private TextView mPurpose_tv;
    private TextView mSchedule_tv;
    private TextView mSpecial_tv;
    private TextView mCategory_tv;
    private TextView mTopic_tv;
    private EditText mET_Notes;
    private ImageButton play_sound;
    private TextView textSound;
    private MediaPlayer mPlayer;
    private int resId = 0;


    // to attach args to a bundle after fragment is created but before adding it to an activity
    // called/used by DrugActivity.createFragment()
    public static DrugFragment newInstance(int drugID){
        Bundle args = new Bundle();
        args.putInt(ARG_DRUG_ID, drugID);
        DrugFragment fragment = new DrugFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int drugId = (int) getArguments().getInt(ARG_DRUG_ID);
        mDrug = DrugLab.get(getActivity()).getDrug(drugId);
    }

    // needed to account for drug instances being changed p 265
    @Override
    public void onPause() {
        super.onPause();
        DrugLab.get(getActivity()).updateDrug(mDrug);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drug, container, false);

        mGeneric_tv = (TextView)v.findViewById(R.id.generic_tv_id);
        mGeneric_tv.setText("Generic Name: " + mDrug.getGeneric_Name());

        mBrand_tv = (TextView)v.findViewById(R.id.brand_tv_id);
        mBrand_tv.setText("Brand Name: " + mDrug.getBrand_Name());

        mPurpose_tv = (TextView)v.findViewById(R.id.purpose_tv_id);
        mPurpose_tv.setText("Purpose: " + mDrug.getPurpose());

        mSchedule_tv = (TextView)v.findViewById(R.id.schedule_tv_id);
        mSchedule_tv.setText("DEA Schedule: " + mDrug.getDEA_Sch());

        mSpecial_tv = (TextView)v.findViewById(R.id.special_tv_id);
        mSpecial_tv.setText("Special: " + mDrug.getSpecial());

        mCategory_tv = (TextView)v.findViewById(R.id.category_tv_id);
        mCategory_tv.setText("Category: " + mDrug.getCategory());

        mTopic_tv = (TextView)v.findViewById(R.id.study_topic_tv_id);
        mTopic_tv.setText("Study Topic: " + mDrug.getStudy_Topic());

        mET_Notes = (EditText)v.findViewById(R.id.et_notes);
        mET_Notes.setText(mDrug.getNotes() + "", TextView.BufferType.EDITABLE);

        //text to tell the user click to hear the vocabulary
        textSound = (TextView)v.findViewById(R.id.text_sound);

        mET_Notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDrug.setNotes(s.toString());

                // trying to use updateDrug
                DrugLab.get(getActivity()).updateDrug(mDrug);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is intentionally left blank too
            }
        });


        //-------------------play the sound of this drug--------------------------------------
        String fname=mDrug.getGeneric_Name().toLowerCase();
        resId = getResources().getIdentifier(fname, "raw", getContext().getPackageName());

        System.out.println("\t : "+resId);
        // Release any resources from previous MediaPlayer
        if (mPlayer != null) {
            //mp.release();
        }

        // Create a new MediaPlayer to play this sound
        // mPlayer = MediaPlayer.create(getActivity(), resID);

        play_sound = (ImageButton)v.findViewById(R.id.imageView_sound);

        //if we don't have the sound, don't display the image
        if(resId==0){
            play_sound.setVisibility(View.INVISIBLE);
            textSound.setVisibility(View.INVISIBLE);
        }


        play_sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getActivity(), resID, Toast.LENGTH_SHORT).show();
                if (resId != 0) {
                    try {
                        mPlayer = MediaPlayer.create(getActivity(), resId);
                    } catch (IllegalArgumentException e) {
                    } catch (SecurityException e) {
                    } catch (IllegalStateException e) {
                    }
                    try {
                        mPlayer.prepare();
                    } catch (IllegalStateException e) {
                    } catch (IOException e) {
                    }
                    mPlayer.start();
                }
            }
        });
        return v;
    }




    public void returnResult() {
        getActivity().setResult(Activity.RESULT_OK, null);
    }
}
