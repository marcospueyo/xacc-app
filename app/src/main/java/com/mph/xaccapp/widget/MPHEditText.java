package com.mph.xaccapp.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;


public class MPHEditText extends AppCompatEditText {

    public MPHEditText(Context context) {
        super(context);
    }

    public MPHEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MPHEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getTrimmedText() {
        return getText().toString().trim();
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getTrimmedText());
    }
}
