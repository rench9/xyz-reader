package com.r4hu7.xyzreader.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.r4hu7.xyzreader.R;

public class FeedImageView extends AppCompatImageView {

    private float aspectRatio = 1.7778f;

    public FeedImageView(Context context) {
        this(context, null);
    }

    public FeedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FeedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FeedImageView,
                0, 0);
        try {
            aspectRatio = attributes.getFloat(R.styleable.FeedImageView_aspect_ratio, aspectRatio);
        } finally {
            attributes.recycle();
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) (MeasureSpec.getSize(widthMeasureSpec) / aspectRatio));
    }

}
