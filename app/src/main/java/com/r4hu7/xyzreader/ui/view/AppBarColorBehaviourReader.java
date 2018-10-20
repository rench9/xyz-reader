package com.r4hu7.xyzreader.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.ui.activity.ReaderActivity;

public class AppBarColorBehaviourReader extends CoordinatorLayout.Behavior {
    private float bottom = -1;
    private boolean isLightStatusBar = true;

    public AppBarColorBehaviourReader() {
    }

    public AppBarColorBehaviourReader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        if (bottom < 0)
            bottom = dependency.getBottom();
        int r = (100 - (int) (dependency.getBottom() / bottom * 100)) * 2;
        if (r > 100)
            r = 100;

        int alpha = (int) (((double) r / 100) * 255);

        ReaderToolBar readerToolBar = dependency.findViewById(R.id.tbPrimary);
        CardView cardView = dependency.findViewById(R.id.AppBarColorBehaviour_cvBg);
        cardView.setAlpha(255 - alpha);
        cardView.setScaleY(1 + (float) r / 100);
        cardView.setScaleX(1 + (float) r / 100);

        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(ContextCompat.getColor(dependency.getContext(), R.color.dark_shade_2));
        colorDrawable.setAlpha(alpha);

        dependency.setBackground(colorDrawable);

        if (r > 90 && isLightStatusBar) {
            readerToolBar.setDarkTheme();
            isLightStatusBar = false;
        } else if (r < 10 && !isLightStatusBar) {
            readerToolBar.setLightTheme();
            isLightStatusBar = true;
        }

        return true;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }
}
