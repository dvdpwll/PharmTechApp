package com.groupproject.android.PharmTech_SA_TN_DP_FY.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**  * Created by thomasnewell on 11/1/16. 
 *
 * used to assist in opening a database
 * mDatabase uses SQLiteDatabase methods to open, query and close the projects database
 *
 *
 * */

public class DrugDbHelper extends SQLiteOpenHelper {

    private static final boolean VERBOSE = false;
    private static final String TAG = "DrugDbHelper";

    private static final int VERSION = 3;
    private static String DATABASE_NAME = "PharmTech.db";
    private String DB_Path= null;
    private final Context myContext;
    private SQLiteDatabase mDataBase;


    // develops path to search for an existing database
    public DrugDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.myContext = context;

        this.DB_Path = "/data/data/" + myContext.getPackageName() + "/" + "databases/";    // check
    }


    // called in DrugLab getDrugs()
    public void createDatabase()throws IOException{
        boolean dbExist = checkDatabase();
        if(dbExist){
            // then don't go get another
        }
        else{
            this.getReadableDatabase();
            try{
                copyDatabase();
            }
            catch (IOException e){
                throw new Error("Error Copying Db");
            }
            finally {
                this.close();
            }
        }
    }


    // checks to see if emulator path has a database
    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = this.DB_Path + this.DATABASE_NAME;
                Log.d("DrugDBHelper ", "Path is: " + myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLException e){
        }
        if(checkDB != null) {
                checkDB.close();
        }
        return checkDB != null ? true: false;
    }


    // if no database was found, then open and go get one in the assets folder, write it to the emulator path
    // used in createDatabase() and upGrade(...)
    private void copyDatabase () throws IOException {
        InputStream mInput = myContext.getAssets().open(DATABASE_NAME);

        String outFileName = DB_Path + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[10];
        int mLength;

        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    //Open database, so we can use mDataBase variable to query SQLiteDatabase methods
    // called from DrugLab.getDrugs()
    public void openDataBase() throws SQLException {
        String mPath = DB_Path + DATABASE_NAME;
        this.mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            try{
                copyDatabase();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    //
    public DrugCursorWrapper query(String tableName,
                        String[] col,
                        String selection,
                        String[] selectionArgs,
                        String groupBy, String having, String orderBy)
    {
        String orderByGeneric = DrugDbSchema.DrugTable.Cols.GENERIC;
        Cursor cursor = mDataBase.query(tableName, col, selection, selectionArgs, groupBy, having, orderByGeneric);
        //Cursor cursor = mDataBase.query(tableName, null, null, null, null, null, orderByGeneric);
        return new DrugCursorWrapper(cursor);
    }
}
