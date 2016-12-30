package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by saadiaachouham on 12/13/16.
 *
 * RecyclerView decorator class will use this drawable file: bottom_border.xml and line_divider.xml
 * to draw a line demarcation between items in the RecyclerView components.
 */

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    public SimpleDividerItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
    }
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}