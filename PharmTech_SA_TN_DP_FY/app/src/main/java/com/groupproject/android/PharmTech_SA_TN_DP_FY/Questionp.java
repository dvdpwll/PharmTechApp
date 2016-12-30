package com.groupproject.android.PharmTech_SA_TN_DP_FY;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 */

public class Questionp {

    private String mTextResId;
    private boolean mAnswerTrue;
    private String mCorrectAnswer;

    public Questionp(String textResId, boolean answerTrue, String correctAnswer){

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
