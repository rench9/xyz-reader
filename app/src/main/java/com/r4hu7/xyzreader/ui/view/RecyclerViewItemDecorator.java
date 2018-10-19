package com.r4hu7.xyzreader.ui.view;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemDecorator extends RecyclerView.ItemDecoration {
    private final boolean isGrid;
    private final boolean outerSpacing;
    private final int spanCount;
    private final int space;
    private final int orientation;

    public RecyclerViewItemDecorator(boolean isGrid, int orientation, boolean outerSpacing, int spanCount, int space) {
        this.isGrid = isGrid;
        this.outerSpacing = outerSpacing;
        this.orientation = orientation;
        this.spanCount = spanCount;
        this.space = space;
    }

    public RecyclerViewItemDecorator(boolean isGrid, int orientation, boolean outerSpacing, int space) {
        this(isGrid, orientation, outerSpacing, 1, space);
        if (isGrid)
            throw new IllegalArgumentException("Span count can't be empty with GridLayoutManager");
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (isGrid) {
            if (orientation == OrientationHelper.HORIZONTAL) {
                if (outerSpacing) {
                    outRect.top = space - column * space / spanCount;
                    outRect.bottom = (column + 1) * space / spanCount;

                    if (position < spanCount) {
                        outRect.left = space;
                    }
                    outRect.right = space;
                } else {
                    outRect.top = column * space / spanCount;
                    outRect.bottom = space - (column + 1) * space / spanCount;
                    if (position >= spanCount) {
                        outRect.left = space;
                    }
                }
            } else {
                if (outerSpacing) {
                    outRect.left = space - column * space / spanCount;
                    outRect.right = (column + 1) * space / spanCount;

                    if (position < spanCount) {
                        outRect.top = space;
                    }
                    outRect.bottom = space;
                } else {
                    outRect.left = column * space / spanCount;
                    outRect.right = space - (column + 1) * space / spanCount;
                    if (position >= spanCount) {
                        outRect.top = space;
                    }
                }
            }
        } else {
            if (orientation == OrientationHelper.HORIZONTAL) {
                if (outerSpacing) {
                    outRect.left = position == 0 ? space : 0;
                    outRect.right = space;
                } else outRect.left = position > 0 ? space : 0;

            } else {
                if (outerSpacing) {
                    outRect.top = position == 0 ? space : 0;
                    outRect.bottom = space;
                } else outRect.top = position > 0 ? space : 0;

            }
        }

    }
}
