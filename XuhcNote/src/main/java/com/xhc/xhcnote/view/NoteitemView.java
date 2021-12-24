package com.xhc.xhcnote.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.xhc.xhcnote.R;

public class NoteitemView extends RelativeLayout {

    private GradientDrawable backDrawable;

    public NoteitemView(Context context) {
        super(context);
        init(context);
    }

    public NoteitemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoteitemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        backDrawable=new GradientDrawable();
        int colorDrawable=context.getColor(R.color.item_bg);
        backDrawable.setColor(colorDrawable);
        backDrawable.setCornerRadius(50);
        setBackground(backDrawable);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
    }


}
