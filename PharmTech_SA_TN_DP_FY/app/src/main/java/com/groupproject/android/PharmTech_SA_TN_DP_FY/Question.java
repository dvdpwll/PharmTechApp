package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){

        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }
    public void setTextResId(int textResId){
        mTextResId = textResId;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }
}