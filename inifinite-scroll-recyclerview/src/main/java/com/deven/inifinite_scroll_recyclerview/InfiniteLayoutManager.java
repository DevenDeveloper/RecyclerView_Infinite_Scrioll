package com.deven.inifinite_scroll_recyclerview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class InfiniteLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "InfiniteLayoutManager";
    private boolean infiniteEnable = true;

    public InfiniteLayoutManager() {}

    public void setInfiniteEnable(boolean infiniteEnable) {
        this.infiniteEnable = infiniteEnable;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        //preLayout
        if (state.isPreLayout()) {
            return;
        }
        //*****scrap*****，*****view*****
        detachAndScrapAttachedViews(recycler);

        int autualWidth = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //*****，*****view*****
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);
            //*****itemView*****
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            //*****itemView*****
            layoutDecorated(itemView, autualWidth, 0, autualWidth + width, height);

            autualWidth += width;
            //*****itemView*****RecyclerView*****
            if (autualWidth > getWidth()) {
                break;
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //1.view
        int travl = fill(dx, recycler, state);
        if (travl == 0) {
            return 0;
        }

        //2.
        offsetChildrenHorizontal(travl * -1);

        //3.
        recyclerHideView(dx, recycler, state);
        return travl;
    }

    /**
     *
     */
    private int fill(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            //*****1.*****
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);
            //*****2.*****个itemView*****
            if (lastView.getRight() < getWidth()) {
                View scrap = null;
                //*****3.*****个itemView*****
                // *****个，*****个itemView*****个
                if (lastPos == getItemCount() - 1) {
                    if (infiniteEnable) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dx;
                }
                //4.*****itemViewadd*****
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap,lastView.getRight(), 0,
                        lastView.getRight() + width, height);
                return dx;
            }
        } else {
            //
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);

            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (infiniteEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap,0,0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }

    /**
     * view
     */
    private void recyclerHideView(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }
            if (dx > 0) {
                //view
                if (view.getRight() < 0) {
                    removeAndRecycleView(view, recycler);
                    Log.d(TAG, "一个view  childCount=" + getChildCount());
                }
            } else {
                //view
                if (view.getLeft() > getWidth()) {
                    removeAndRecycleView(view, recycler);
                    Log.d(TAG, "个view  childCount=" + getChildCount());
                }
            }
        }

    }
}