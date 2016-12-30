package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugPagerAct: user can swipe through a list of drugs
 */

public class DrugPagerActivity extends AppCompatActivity {

   // private static final String EXTRA_DRUG_LIST = "com.groupproject.android.PharmTech_SA_TN_DP_FY.drugs_list";
    private static final String EXTRA_DRUG_ID = "com.groupproject.android.PharmTech_SA_TN_DP_FY.drug_id";
    private static final String EXTRA_DRUG_WHERE = "where";
    private static final String EXTRA_DRUG_ARGS= "args";
    private ViewPager mViewPager;
    private List<Drug> mDrugs;// = new ArrayList<Drug>();

    // called from DrugListFragment()'s DrugHolder's class in onClick(View view)
    public static Intent newIntent(Context packageContext, int drugId) {
        Intent intent = new Intent(packageContext, DrugPagerActivity.class);
        intent.putExtra(EXTRA_DRUG_ID, drugId);
        //intent.putExtra(EXTRA_DRUG_WHERE, where);
        //intent.putExtra(EXTRA_DRUG_ARGS, arg);

        /*
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DRUG_LIST, (Serializable)drugs);
        intent.putExtras(bundle);
        */

        return intent;
    }

    // called from DrugFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drug_pager);
        int drugId = (int) getIntent().getIntExtra(EXTRA_DRUG_ID, 0);
        //get Intents for query's

        final String where = getIntent().getStringExtra(EXTRA_DRUG_WHERE);
        final String argsTemp = getIntent().getStringExtra(EXTRA_DRUG_ARGS);
        String[] args = {argsTemp};

        //
        if(where != null && args != null) {
            mDrugs = DrugLab.get(this).getDrugs(where, args);
        }
        else {
            mDrugs = DrugLab.get(this).getDrugs();
        }
        /*
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mDrugs = (List<Drug>) bundle.getSerializable(EXTRA_DRUG_LIST);
            }
            mDrugs = DrugLab.get(this).getDrugs();
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }*/

        mViewPager = (ViewPager) findViewById(R.id.activity_drug_pager_view_pager);

        if(mDrugs.isEmpty()){

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override       // fetches Drug instance for given position in dataset
            public Fragment getItem(int position) {
                Drug drug = mDrugs.get(position);
                return DrugFragment.newInstance(drug.getId());
            }


            @Override       // returns number of items in array list.
            public int getCount() {
                return mDrugs.size();
            }
        });

        // shows selected drug by setting ViewPager’s current item to index of selected drug.
        for (int i = 0; i < mDrugs.size(); i++) {
            if (mDrugs.get(i).getId() == drugId) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}