package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugActivity
 */

import android.support.v4.app.Fragment;

public class DrugActivity extends SingleFragmentActivity {
    private static final String EXTRA_DRUG_ID = "com.groupproject.android.PharmTech_SA_TN_DP_FY.drug_id";

    @Override
    protected Fragment createFragment() {
        int drugId = (int) getIntent().getIntExtra(EXTRA_DRUG_ID, 10);
        return DrugFragment.newInstance(drugId);
    }
}
