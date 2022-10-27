package com.xuhc.floatingwindow;

import android.content.Context;
import android.widget.RelativeLayout;

public class ControlSmallBar extends BaseControlBar {

    private RelativeLayout arrowBtn;
    private boolean isCanMove = true;

    public ControlSmallBar(Context context) {
        super(context);
    }

    public ControlSmallBar(Context context, boolean isCanMove) {
        super(context);
        this.isCanMove = isCanMove;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_control_small_bar;
    }

    @Override
    public void init() {
        getWindowLayoutParams().width = (int) getResources().getDimension(R.dimen.control_bar_item_width);
        getWindowLayoutParams().height = (int) getResources().getDimension(R.dimen.control_bar_item_height);
        addListener();
    }

    private void addListener() {
        arrowBtn = findViewById(R.id.small_control_bar_arrow);
        if (isCanMove) {
            setOnTouchListener(new FloatingOnTouchListener(this));
        }
    }

    @Override
    public void changeEdge(int direction) {
        if (direction == ORIENTATION_LEFT){
            arrowBtn.setBackgroundResource(R.drawable.control_bar_right_arrow_bg);
        } else {
            arrowBtn.setBackgroundResource(R.drawable.control_bar_left_arrow_bg);
        }
    }

}
