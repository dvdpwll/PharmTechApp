package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.groupproject.android.PharmTech_SA_TN_DP_FY.database.DrugCursorWrapper;
import com.groupproject.android.PharmTech_SA_TN_DP_FY.database.DrugDbHelper;
import com.groupproject.android.PharmTech_SA_TN_DP_FY.database.DrugDbSchema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugLab: to hold one working copy of a database list
 * that can query the database for one, for all and populate a drug object list to use
 */

public class DrugLab {
    private static DrugLab sDrugLab; // Only one instance of itself to be created
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DrugDbHelper mDBHelper;

    // get() simply returns the instance.
    // called by DrugFragment.onCreateView in order to call DrugLab.updateData()
    public static DrugLab get(Context context) {

        if (sDrugLab == null) {
            sDrugLab = new DrugLab(context);
        }
        if(sDrugLab == null){
            Log.d("ERROR - DrugLab", " In get(), if this shows, something is wrong here");
        }
        return sDrugLab;
    }

    //private constructor
    private DrugLab(Context context) {
        mContext = context.getApplicationContext();
        mDBHelper = new DrugDbHelper(mContext);
    }

    // to write to DB if needed in the future
    public void addDrug(Drug d){
        ContentValues values = getContentValues(d);
        mDatabase.insert(DrugDbSchema.DrugTable.NAME, null, values);
    }

    // caller is DrugListFragment().updateSubtitle and DrugListFragment().updateUI()
    // getDrugs() returns entire List of drugs to caller ...
    // by creating ArrayList, creating then opening a database and querying to DbHelper
    // returns a cursor full of information, uses cursor to get getDrug(), then adding to ArrayList
    public List<Drug> getDrugs() {
        List<Drug> drugs = new ArrayList<>();

        try{
            mDBHelper.createDatabase();
        }
        catch(IOException e){
            throw new Error("Error creating db");
        }
        try{
            mDBHelper.openDataBase();
        }
        catch (SQLException sqle){
            throw sqle;
        }

        DrugCursorWrapper cursor = mDBHelper.query(DrugDbSchema.DrugTable.NAME,
                null, null, null, null, null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                drugs.add(cursor.getDrug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return drugs;
    }

    public List<Drug> getDrugs(String col, String[] value) {
        List<Drug> drugs = new ArrayList<>();

        DrugCursorWrapper cursor = queryDrugs(col + " = ?", value);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                drugs.add(cursor.getDrug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return drugs;

    }

    // returns a list of all values, but without any repeats
    public List<Drug> getDistinctList(String distinctCol){
        List<Drug> drugs = new ArrayList<>();
        DrugCursorWrapper cursor = queryDistinctDrugs(distinctCol);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                drugs.add(cursor.getDrug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return drugs;
    }


    public List<Drug> getListOfNotNullFromCol(String where){
        List<Drug> drugs = new ArrayList<>();
        String whereClause = where + " IS NOT NULL AND " + where + " != ?";

        DrugCursorWrapper cursor = queryNotNullDrugs(whereClause);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                drugs.add(cursor.getDrug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return drugs;
    }


    private DrugCursorWrapper queryNotNullDrugs(String whereClause){
        Log.d("DRugLab", " argument length = " + new String[]{""}.length);
        mDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(
                DrugDbSchema.DrugTable.NAME,
                null,
                whereClause,
                new String[]{""},
                null,
                null,
                null
        );
        return new DrugCursorWrapper(cursor);
    }





    // returns a single Drug with the given ID, called within DrugFragments onCreate()
    // builds necessary syntax for a SQLiteDatabase query
    public Drug getDrug(int id) {
        DrugCursorWrapper cursor = queryDrugs(
                DrugDbSchema.DrugTable.Cols.ID + " = ?", new String[] { id + "" }
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDrug();

        } finally {
            cursor.close();
        }
    }


    // called by DrugFragment.onPause() and in DrugFragment.onCreateView()
    // builds necessary SQLiteDatabase syntax to write to DB
    public void updateDrug(Drug drug){
        int idTemp = drug.getId();
        String idString = idTemp + "";

        ContentValues values = getContentValues(drug);

        if (mDatabase != null) {
            mDatabase.update(DrugDbSchema.DrugTable.NAME, values,
                    DrugDbSchema.DrugTable.Cols.ID + " = ?",
                    new String[]{idString});
        }
        else{
        }


    }

    //---------------search by generic-----------------
    // returns the Drug with the given generic name
    // called within DrugListFragments onCreate()
    public Drug getDrug(String generic) {

        DrugCursorWrapper cursor = queryDrugs(
                DrugDbSchema.DrugTable.Cols.GENERIC + " = ?", new String[] { generic + "" }
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDrug();

        } finally {
            cursor.close();
        }
    }



    // called by addDrug() and updateDrug()
    private static ContentValues getContentValues(Drug drug){
        int idTemp = drug.getId();
        String idString = idTemp + "";
        String updatedNotes = drug.getNotes();

        ContentValues values = new ContentValues();
        values.put(DrugDbSchema.DrugTable.Cols.ID, idString);
        values.put(DrugDbSchema.DrugTable.Cols.NOTES, updatedNotes);

        return values;
    }


    // called from getDrugs() and getDrug()
    // accepts query parameters, gets a writable db,
    // query's the db and returns a cursor of information
    private DrugCursorWrapper queryDrugs(String where, String[] whereArgs){

        String whereClause = null;

        if (where == null && whereArgs[0] == null) {
            whereClause = null;
        } else{
            whereClause = where;
        }
        if (where != null ) {
        }

        mDatabase = mDBHelper.getWritableDatabase();
        String orderByGeneric = DrugDbSchema.DrugTable.Cols.GENERIC;
        Cursor cursor = mDatabase.query(
                DrugDbSchema.DrugTable.NAME,
                null,           // null selects all columns
                whereClause,
                whereArgs,
                null,           // groupBy
                null,           // having ie., "generic ASC"
                orderByGeneric  // orderBy
        );

        return new DrugCursorWrapper((cursor));
    }

    // parameter identifies the column to focus on,
    // first param "true" indicates to query it should be distinct
    private DrugCursorWrapper queryDistinctDrugs(String distinctCol){
        mDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(true, DrugDbSchema.DrugTable.NAME,
                null,
                null,
                null,
                distinctCol,
                null,
                null,
                null
        );
        return new DrugCursorWrapper(cursor);
    }

} // end of DrugLab