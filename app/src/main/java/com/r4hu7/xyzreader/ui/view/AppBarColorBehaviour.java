package com.r4hu7.xyzreader.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.r4hu7.xyzreader.R;

public class AppBarColorBehaviour extends CoordinatorLayout.Behavior {
    private float bottom = -1;

    public AppBarColorBehaviour() {
    }

    public AppBarColorBehaviour(Context context, AttributeSet attrs) {
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

        ImageView imageView = dependency.findViewById(R.id.AppBarColorBehaviour_ivBg);
        imageView.setImageAlpha(255 - alpha);
        imageView.setScaleY(1 + (float) r / 100);
        imageView.setScaleX(1 + (float) r / 100);

        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(ContextCompat.getColor(dependency.getContext(), R.color.dark_shade_2));
        colorDrawable.setAlpha(alpha);

        dependency.setBackground(colorDrawable);
        return true;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

}
