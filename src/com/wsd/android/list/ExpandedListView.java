package com.wsd.android.list;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExpandedListView extends ListView {
    private int old_count = 0;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getCount() != old_count) {
            old_count = getCount();
            
            ViewGroup.LayoutParams params = getLayoutParams();
            params = getLayoutParams();
            params.height = getCount() * (getChildAt(0).getMeasuredHeight() + getDividerHeight() + 10) - getDividerHeight();
            setLayoutParams(params);
            
//            int totalHeight = 0;
//            
//            for (int i = 0; i < getCount(); i++) {
//                View listItem = getAdapter().getView(i, null, this);
//                listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//                listItem.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                totalHeight += listItem.getMeasuredHeight();
//            }
//
//            ViewGroup.LayoutParams params = getLayoutParams();
//            params.height = totalHeight + (getDividerHeight() * (getCount() - 1));
//            setLayoutParams(params);
//            requestLayout();
        }

        super.onDraw(canvas);
    }
}
