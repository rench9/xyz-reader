package com.r4hu7.xyzreader.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.ui.activity.ReaderActivity;

public class ReaderToolBar extends Toolbar {
    private boolean isNightMode;
    private boolean lightStatusBar = true;

    public ReaderToolBar(Context context) {
        super(context);
    }

    public ReaderToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReaderToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }

    public void setDarkTheme() {
        try {
            int size = getMenu().size();
            int color = getContext().getResources().getColor(R.color.shade_0);
            for (int i = 0; i < size; i++) {
                DrawableCompat.setTint(getMenu().getItem(i).getIcon(), color);
            }
            if (getNavigationIcon() != null)
                DrawableCompat.setTint(getNavigationIcon(), color);
            clearLightStatusBar(((ReaderActivity) getContext()));
        } catch (Exception e) {
            Log.e("ReaderToolBar", e.getMessage());
        }
    }

    public void setLightTheme() {
        if (isNightMode)
            return;
        try {
            int size = getMenu().size();
            int color = getContext().getResources().getColor(R.color.dark_shade_2);
            for (int i = 0; i < size; i++) {
                DrawableCompat.setTint(getMenu().getItem(i).getIcon(), color);
            }
            if (getNavigationIcon() != null)
                DrawableCompat.setTint(getNavigationIcon(), color);
            setLightStatusBar(((ReaderActivity) getContext()));
        } catch (Exception e) {
            Log.e("ReaderToolBar", e.getMessage());
        }
    }

    private void setLightStatusBar(Activity activity) {
        if (lightStatusBar && !isNightMode)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            lightStatusBar = true;
        }
    }

    private void clearLightStatusBar(Activity activity) {
        if (!lightStatusBar && isNightMode)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            lightStatusBar = false;
        }
    }


}
