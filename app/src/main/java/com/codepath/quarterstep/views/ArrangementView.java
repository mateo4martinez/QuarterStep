package com.codepath.quarterstep.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class ArrangementView extends GridView {
    public ArrangementView(Context context) {
        super(context);
    }

    public ArrangementView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArrangementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArrangementView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public View getViewByPosition(int position) {
        int firstPosition = this.getFirstVisiblePosition();
        int lastPosition = this.getLastVisiblePosition();

        if ((position < firstPosition) || (position > lastPosition))
            return null;

        return this.getChildAt(position - firstPosition);
    }
}
