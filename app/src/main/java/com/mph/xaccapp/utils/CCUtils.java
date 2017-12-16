package com.mph.xaccapp.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mph.xaccapp.R;

import java.util.Date;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;
import static com.google.common.base.Preconditions.checkNotNull;


public class CCUtils {

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        fragmentManager.beginTransaction()
                .replace(frameId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static String getFormattedCheckout(final Context context, String date) {
        return context.getString(R.string.checkout) + " " + date;
    }

    public static String getFormattedLastActivity(Date date) {
        return DateUtils.dateToString(date,
                DateUtils.isToday(date) ? DateUtils.SHORT_TIME_FORMAT : DateUtils.DATE_FORMAT);
    }

    public static String getFormattedMessageDate(Date date) {
        return DateUtils.dateToString(date,
                DateUtils.isToday(date) ? DateUtils.SHORT_TIME_FORMAT : DateUtils.FULL_DATE_FORMAT);
    }

    public static boolean isAPI_L_OrAbove() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static RecyclerView.AdapterDataObserver getScrolldownObserver(
            final RecyclerView.Adapter adapter,
            final LinearLayoutManager layoutManager,
            final RecyclerView recyclerView) {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1  || (positionStart >= (messageCount - 1)
                        && lastVisiblePosition == (positionStart -1))) {
                    recyclerView.smoothScrollToPosition(positionStart);
                }
            }

            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }

    public static View.OnLayoutChangeListener getScrolldownLayoutObserver(
            final RecyclerView.Adapter adapter,
            final RecyclerView recyclerView) {
        return new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom)
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }


    // TODO: 26/07/2017 Extend TextView with this method
    public static void renderHighlighting(boolean isHighlighted, TextView textView) {
        textView.setTypeface(null, isHighlighted ? BOLD : NORMAL);
    }

    public static void makeCall(final Activity activity, String phoneNumber) {
        // TODO: 27/09/2017 Handle permission request response
        int checkPermission = ContextCompat.checkSelfPermission(activity, "android.permission.CALL_PHONE");
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{"android.permission.CALL_PHONE"},
                    /*REQUEST_CALL_PHONE)*/ 0);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        }
    }
}
