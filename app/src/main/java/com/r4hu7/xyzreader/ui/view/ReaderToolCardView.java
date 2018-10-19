package com.r4hu7.xyzreader.ui.view;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.r4hu7.xyzreader.R;

import java.lang.ref.WeakReference;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderToolCardView extends MaterialCardView implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public interface ReaderToolChangeListner {
        void OnFontSizeChange(float sizeInPixel);

        void OnNightModeChange(boolean isEnabled);

        void OnSystemPermissionRequired();
    }

    private AnimationSet animationSet;
    private AnimationSet animationSet2;

    private WeakReference<ReaderToolChangeListner> readerToolChangeListner;

    private int selectedFontSize;
    private float fonts[];
    private boolean nightMode = false;

    @BindView(R.id.btnSmallChar)
    ImageButton btnSmallChar;
    @BindView(R.id.btnBigChar)
    ImageButton btnBigChar;
    @BindView(R.id.btnNightMode)
    ImageButton btnNightMode;
    @BindView(R.id.btnBrightness)
    ImageButton btnBrightness;
    @BindView(R.id.btnDecBrightness)
    ImageButton btnDecBrightness;
    @BindView(R.id.btnIncBrightness)
    ImageButton btnIncBrightness;
    @BindView(R.id.sbBrightness)
    AppCompatSeekBar sbBrightness;
    @BindView(R.id.llPrimaryTool)
    LinearLayout llPrimaryTool;
    @BindView(R.id.llSecondaryTool)
    LinearLayout llSecondaryTool;

    @BindDimen(R.dimen.typography_caption)
    float typography_caption;
    @BindDimen(R.dimen.typography_body1)
    float typography_body;
    @BindDimen(R.dimen.typography_subheading)
    float typography_subheading;
    @BindDimen(R.dimen.typography_title)
    float typography_title;
    @BindDimen(R.dimen.typography_headline)
    float typography_headline;
    @BindDimen(R.dimen.typography_headline2)
    float typography_headline2;
    @BindDimen(R.dimen.typography_display1)
    float typography_display1;

    public ReaderToolCardView(Context context) {
        this(context, null);
    }

    public ReaderToolCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReaderToolCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initDefaults();
    }

    private AnimationSet getAnimationSet() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setDuration(260);
        return animationSet;
    }

    private AnimationSet showBar() {
        if (animationSet == null) {
            animationSet = getAnimationSet();

            AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
            animationSet.addAnimation(alphaAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 150, Animation.ABSOLUTE, 0);
            translateAnimation.setFillAfter(true);

            animationSet.addAnimation(translateAnimation);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    llPrimaryTool.setVisibility(VISIBLE);
                    llSecondaryTool.setVisibility(GONE);
                    ReaderToolCardView.super.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        }
        clearAnimation();
        return animationSet;
    }

    private AnimationSet hideBar() {
        if (animationSet2 == null) {
            animationSet2 = getAnimationSet();

            AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
            animationSet2.addAnimation(alphaAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 150);
            translateAnimation.setFillAfter(true);

            animationSet2.addAnimation(translateAnimation);

            animationSet2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ReaderToolCardView.super.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        clearAnimation();
        return animationSet2;
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE)
            startAnimation(showBar());
        else if (visibility == GONE)
            startAnimation(hideBar());
    }

    private void initDefaults() {
        selectedFontSize = 1;
        fonts = new float[]{
                typography_caption,
                typography_body,
                typography_subheading,
                typography_title,
                typography_headline,
                typography_headline2,
                typography_display1};
    }

    private void initView() {
        inflate(getContext(), R.layout.adapter_reader_tools, this);
        ButterKnife.bind(this);
        btnSmallChar.setOnClickListener(this);
        btnBigChar.setOnClickListener(this);
        btnNightMode.setOnClickListener(this);
        btnBrightness.setOnClickListener(this);
        btnIncBrightness.setOnClickListener(this);
        btnDecBrightness.setOnClickListener(this);
        sbBrightness.setOnSeekBarChangeListener(this);
    }


    public void setFontSize(int defaultFontIndex, float[] fontSizes) {
        fonts = fontSizes;
        this.selectedFontSize = defaultFontIndex;
    }

    public void addReaderToolChangeListener(@NonNull ReaderToolChangeListner readerToolChangeListner) {
        this.readerToolChangeListner = new WeakReference<>(readerToolChangeListner);
    }

    @Override
    public void onClick(View view) {
        if (readerToolChangeListner == null || readerToolChangeListner.get() == null)
            return;
        switch (view.getId()) {
            case R.id.btnSmallChar:
                if (selectedFontSize > 0) {
                    readerToolChangeListner.get().OnFontSizeChange(fonts[selectedFontSize -= 1]);
                    if (!btnBigChar.isEnabled()) {
                        btnBigChar.isEnabled();
                    }
                }
                break;
            case R.id.btnBigChar:
                if (selectedFontSize < fonts.length - 1) {
                    readerToolChangeListner.get().OnFontSizeChange(fonts[selectedFontSize += 1]);
                    if (!btnSmallChar.isEnabled()) {
                        btnSmallChar.isEnabled();
                    }
                }
                break;
            case R.id.btnNightMode:
                readerToolChangeListner.get().OnNightModeChange(nightMode = !nightMode);
                break;
            case R.id.btnBrightness:
                readerToolChangeListner.get().OnSystemPermissionRequired();
                break;
            case R.id.btnIncBrightness:
                break;
            case R.id.btnDecBrightness:
                break;
        }
    }

    public void switchToBrightnessTool() {
        llSecondaryTool.setVisibility(VISIBLE);
        llPrimaryTool.setVisibility(GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, i);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
