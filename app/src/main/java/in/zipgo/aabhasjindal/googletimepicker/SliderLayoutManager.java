package in.zipgo.aabhasjindal.googletimepicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class SliderLayoutManager extends LinearLayoutManager {

    RecyclerView recyclerView;
    OnItemSelectedListener callback;

    public SliderLayoutManager(Context context) {
        super(context);
    }

    public SliderLayoutManager(Context context, int orientation, boolean reverseLayout, OnItemSelectedListener itemSelectedListener) {
        super(context, orientation, reverseLayout);
        callback = itemSelectedListener;
    }

    public SliderLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SliderLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);

        recyclerView = view;
        new LinearSnapHelper().attachToRecyclerView(view);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
//        scaleUpView();
    }

//    private void scaleUpView() {
//        float mid = getWidth() / 2.0f;
//        for (int i = 0; i < getChildCount(); i++) {
//            // Calculating the distance of the child from the center
//            View child = getChildAt(i);
//            float childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f;
//            if (childMid == mid) {
//            } else {
//                child.setScaleX(1.0f);
//                child.setScaleY(1.0f);
//            }
////            float distanceFromCenter = Math.abs(mid - childMid);
//            // The scaling formula
////            float scale = 1 - (float) Math.sqrt((distanceFromCenter / getWidth())) * 0.66f;
////            float scale = 1 + 0.5*((float) Math.pow(Math.exp(
////                    -(((x - mean) * (x - mean)) / ((2 * variance)))),
////                    1 / (stdDeviation * Math.sqrt(2 * Math.PI))));
//            // Set scale to view
//        }
//    }
//
//    private void scaleSettleViews() {
//        float mid = getWidth() / 2.0f;
//        for (int i = 0; i < getChildCount(); i++) {
//            // Calculating the distance of the child from the center
//            View child = getChildAt(i);
//            child.setScaleX(1.0f);
//            child.setScaleY(1.0f);
//        }
//    }



    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            float recyclerViewCenterX = getRecyclerViewCenterX();
            float minDistance = recyclerView.getWidth();
            int position = -1;
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View child = recyclerView.getChildAt(i);
                int childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2;
                float newDistance = Math.abs(childCenterX - recyclerViewCenterX);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    position = recyclerView.getChildLayoutPosition(child);
                }
            }
//            scaleUpView();
            // Notify on item selection
            if (callback != null) {
                callback.onItemSelected(position);
            }
        }
        // When scroll stops we notify on the selected item
        if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            float recyclerViewCenterX = getRecyclerViewCenterX();
            float minDistance = recyclerView.getWidth();
            int position = -1;
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View child = recyclerView.getChildAt(i);
                int childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2;
                float newDistance = Math.abs(childCenterX - recyclerViewCenterX);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    position = recyclerView.getChildLayoutPosition(child);
                }
            }
//            scaleSettleViews();
            // Notify on item selectio
            if (callback != null) {
                callback.onScrolled();
            }
            // Find the closest child to the recyclerView center --> this is the selected item.
        }
    }



    private float getRecyclerViewCenterX() {
        return (recyclerView.getRight() - recyclerView.getLeft()) / 2 + recyclerView.getLeft();
    }

    interface OnItemSelectedListener {
        void onItemSelected(int layoutPosition);
        void onScrolled();
    }
}
