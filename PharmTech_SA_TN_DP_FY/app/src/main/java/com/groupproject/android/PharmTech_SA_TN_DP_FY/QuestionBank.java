package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.util.Log;
/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 */

public class QuestionBank {

    private String mTextResId;
    private boolean mAnswerTrue;
    private String mCorrectAnswer;

    public QuestionBank(String textResId, boolean answerTrue, String correctAnswer){

        Log.d("QuestionBank", " constructor");

        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCorrectAnswer = correctAnswer;
    }

    public String getTextResId() {
        return mTextResId;
    }
    public void setTextResId(String textResId){
        mTextResId = textResId;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }
    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }
    public void setCorrectAnswer(String correctAnswer){
        mCorrectAnswer = correctAnswer;
    }
}
