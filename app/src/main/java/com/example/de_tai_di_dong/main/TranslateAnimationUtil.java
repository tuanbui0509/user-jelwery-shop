package com.example.de_tai_di_dong.main;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.de_tai_di_dong.R;

public class TranslateAnimationUtil implements View.OnTouchListener{
    private GestureDetector gestureDetector;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return false;
    }

    public TranslateAnimationUtil(Context context,View view,View view2) {
        this.gestureDetector = new GestureDetector(context,new SimpleGestureDectector(view,view2));
    }

    public class SimpleGestureDectector extends GestureDetector.SimpleOnGestureListener{
        private View mViewAnimation;
        private View mViewAnimation2;
        private boolean isFinishAnimation = true;

        public SimpleGestureDectector(View mViewAnimation,View mViewAnimation2) {
            this.mViewAnimation = mViewAnimation;
            this.mViewAnimation2 = mViewAnimation2;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(distanceY > 0)
                showView();
            else
                hiddenView();
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void hiddenView() {
            if(mViewAnimation==null||mViewAnimation.getVisibility()==View.GONE){
                return;
            }
            Animation animation = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.buttom_down);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //mViewAnimation.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mViewAnimation.setVisibility(View.GONE);
                    mViewAnimation2.setVisibility(View.GONE);
                    isFinishAnimation = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(isFinishAnimation){
                mViewAnimation.startAnimation(animation);
            }
        }

        private void showView() {
            if(mViewAnimation==null||mViewAnimation.getVisibility()==View.VISIBLE){
                return;
            }
            Animation animation = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.buttom_up);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mViewAnimation.setVisibility(View.VISIBLE);
                    mViewAnimation2.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFinishAnimation = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(isFinishAnimation){
                mViewAnimation.startAnimation(animation);
            }
        }
    }
}
