package com.methodsignature.simplehomescreen.view.lists;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by randallmitchell on 7/21/16.
 */
public class EvenPaddingItemDecoration extends RecyclerView.ItemDecoration {

    private final int paddingInDp;

    public EvenPaddingItemDecoration(float paddingInDp) {
        this.paddingInDp = Math.round(paddingInDp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // always pad the bottom. only pad the top on the first item.
        final int top = parent.getChildAdapterPosition(view) == 0 ? paddingInDp : 0;
        outRect.set(paddingInDp, top, paddingInDp, paddingInDp);
    }
}
