package com.mph.xaccapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

public class MPHSwitch extends Switch {

    public MPHSwitch(Context context) {
        super(context);
    }

    public MPHSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MPHSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCheckedSilently(boolean checked, final OnCheckedChangeListener listener) {
        setOnCheckedChangeListener(null);
        setChecked(checked);
        setOnCheckedChangeListener(listener);
    }
}
