package com.groupproject.android.PharmTech_SA_TN_DP_FY.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.groupproject.android.PharmTech_SA_TN_DP_FY.Drug;


/**
 * Created by thomasnewell on 11/10/16.
 *
 * used in DrugLab ... public List<Drug> getDrugs()
 *
 * ----------------------------------------------------------------------------------------------
 * -------------creating a drug based on the drug_id that we get it from Database----------------
 * -------------Then setting the whole information that are associated for each drug-------------
 * ----------------------------------------------------------------------------------------------
 */

public class DrugCursorWrapper extends CursorWrapper{

    private static final boolean VERBOSE = false;

    private static final String TAG = "DrugCursorWrapper";

    public DrugCursorWrapper(Cursor cursor){
            super(cursor);
        }

    public Drug getDrug(){

        String id_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.ID));
        String generic_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.GENERIC));
        String brand_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.BRAND));
        String purpose_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.PURPOSE));
        String category_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.CATEGORY));
        String schedule_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.SCHEDULE));
        String special_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.SPECIAL));
        String topic_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.TOPIC));
        String notes_String = getString(getColumnIndex(DrugDbSchema.DrugTable.Cols.NOTES));

        Drug drug = new Drug(Integer.parseInt(id_String));
        drug.setGeneric_Name(generic_String);
        drug.setBrand_Name(brand_String);
        drug.setPurpose(purpose_String);
        drug.setCategory(category_String);
        drug.setDEA_Sch(schedule_String);
        drug.setSpecial(special_String);
        drug.setStudy_Topic(topic_String);
        drug.setNotes(notes_String);

        return drug;
    }
}




