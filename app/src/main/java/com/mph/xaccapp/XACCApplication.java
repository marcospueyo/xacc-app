package com.mph.xaccapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

public class XACCApplication extends MultiDexApplication {

    private Context mContext;

    public Context getContext() {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        return mContext;
    }
}
