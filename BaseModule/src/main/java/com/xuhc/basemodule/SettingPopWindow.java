package com.xuhc.basemodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import androidx.core.content.ContextCompat;

public class SettingPopWindow {
    private PopupWindow popupWindow;
    private View mConvertView;

    private SeekBar seekBar1;
    private SeekBar seekBar2;

    private SeekBarListener seekBarListener;

    public void setSeekBarListener(SeekBarListener seekBarListener) {
        this.seekBarListener = seekBarListener;
    }

    public interface SeekBarListener {
        void textSize(int progress);

        void playSpeed(int progress);
    }


    public SettingPopWindow(Context mContext) {
        mConvertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_setting, null);
        popupWindow = new BasePopupWindow(mConvertView
                , (int) mContext.getResources().getDimension(R.dimen.pop_window_width)
                , (int) mContext.getResources().getDimension(R.dimen.pop_window_height));
        seekBar1 = mConvertView.findViewById(R.id.seekbar_textsize);
        seekBar2 = mConvertView.findViewById(R.id.seekbar_playspeed);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.setting_pop_bg));
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        addListener();
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        popupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    private void addListener() {
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBarListener != null) {
                    seekBarListener.textSize(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBarListener != null) {
                    seekBarListener.playSpeed(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setSeekBar1Progress(int progress) {
        if (seekBar1 != null) {
            seekBar1.setProgress(progress);
        }

    }

    public void setSeekBar2Progress(int progress) {
        if (seekBar2 != null) {
            seekBar2.setProgress(progress);
        }
    }
}
