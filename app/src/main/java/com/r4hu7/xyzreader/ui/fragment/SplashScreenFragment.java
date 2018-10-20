package com.r4hu7.xyzreader.ui.fragment;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.di.module.GlideApp;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenFragment extends DialogFragment implements ValueAnimator.AnimatorUpdateListener {
    @BindView(R.id.ivLoadingLogo)
    ImageView ivLoadingLogo;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;

    private boolean isDismissed = false;

    private ValueAnimator animator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_splashscreen, container, false);
        ButterKnife.bind(this, v);
        setAnimation();
        setImages();
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog().getWindow()).getAttributes().windowAnimations = R.style.AppTheme_Dialog;
    }

    private void setImages() {
        GlideApp.with(getContext())
                .asBitmap()
                .fitCenter()
                .load(R.drawable.banner_logo)
                .into(ivLoadingLogo);
        GlideApp.with(getContext())
                .asGif()
                .fitCenter()
                .load(R.drawable.loading_book)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (animator != null)
                            animator.start();
                        return false;
                    }
                })
                .into(ivLoading);
    }

    private void setAnimation() {
        PropertyValuesHolder alphaPropertyValuesHolder = PropertyValuesHolder.ofInt("ALPHA", 0, 255);
        animator = new ValueAnimator();
        animator.setValues(
                alphaPropertyValuesHolder);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(1500);
        animator.addUpdateListener(this::onAnimationUpdate);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isDismissed)
            this.dismiss();
    }

    @Override
    public void dismiss() {
        isDismissed = true;
    }


    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int alpha = (int) valueAnimator.getAnimatedValue("ALPHA");
        ivLoading.setImageAlpha(alpha);
    }

    @Override
    public void dismissAllowingStateLoss() {
        isDismissed = true;
        super.dismissAllowingStateLoss();
    }

    public void animateLogo(float x, float y, float size) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(
                PropertyValuesHolder.ofFloat("X", 0,
                        x - ivLoadingLogo.getX()
                ),
                PropertyValuesHolder.ofFloat("Y", 0,
                        y - ivLoadingLogo.getY()
                ),
                PropertyValuesHolder.ofFloat("S", 1, size / ivLoadingLogo.getMeasuredHeight())
        );
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float _x = (float) valueAnimator1.getAnimatedValue("X");
            float _y = (float) valueAnimator1.getAnimatedValue("Y");
            float _s = (float) valueAnimator1.getAnimatedValue("S");

            ivLoadingLogo.setTranslationY(_y);
            ivLoadingLogo.setScaleX(_s);
            ivLoadingLogo.setScaleY(_s);
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                SplashScreenFragment.super.dismiss();
                SplashScreenFragment.super.dismissAllowingStateLoss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        try {
            valueAnimator.start();
        } catch (Exception e) {
            SplashScreenFragment.super.dismiss();
            SplashScreenFragment.super.dismissAllowingStateLoss();
        }
    }

    public boolean isDismissed() {
        return isDismissed;
    }
}