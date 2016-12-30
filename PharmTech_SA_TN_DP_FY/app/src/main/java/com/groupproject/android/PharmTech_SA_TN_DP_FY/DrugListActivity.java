package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.support.v4.app.Fragment;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugListActivity: start a druglistfragment
 */

public class DrugListActivity extends SingleFragmentActivity {

   @Override
    protected Fragment createFragment() {
       return new DrugListFragment();
    }

}
