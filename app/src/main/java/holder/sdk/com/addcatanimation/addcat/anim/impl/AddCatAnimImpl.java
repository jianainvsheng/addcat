package holder.sdk.com.addcatanimation.addcat.anim.impl;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

import holder.sdk.com.addcatanimation.addcat.anim.IAddCatAnimation;
import holder.sdk.com.addcatanimation.addcat.anim.evaluator.BezierTypeEvaluator;

/**
 * Created by yangjian on 2018/12/28.
 */

public class AddCatAnimImpl implements IAddCatAnimation {

    private ValueAnimator mPointAnimation;

    private ValueAnimator mSumpAnimation;

    private long mDuration = 300l;

    private long mSumpDuration = 300l;

    private int mPointDuration = 500;

    private AnimationListener mPointListener;

    private AnimationListener mSumpListener;

    public static final int TYPE_POINT = 0;

    public static final int TYPE_SUMP = 1;

    public AddCatAnimImpl setStayDuration(int pointDuration) {

        this.mPointDuration = pointDuration < 0 ? mPointDuration : pointDuration;
        return this;
    }

    public AddCatAnimImpl setPointDuration(long duration) {

        this.mDuration = duration < 0 ? mDuration : duration;
        return this;
    }

    public AddCatAnimImpl setSumpDuration(long duration) {

        this.mSumpDuration = duration < 0 ? mSumpDuration : duration;
        return this;
    }

    public AddCatAnimImpl setSumpListener(AnimationListener l) {

        this.mPointListener = l;
        return this;
    }

    public AddCatAnimImpl setPointListener(AnimationListener l) {

        this.mSumpListener = l;
        return this;
    }

    @Override
    public int onPointAnimation(final View view, final int startX,final int startY ,final int targetX, final int targetY) {

        if (mPointAnimation != null) {
            mPointAnimation.cancel();
            mPointAnimation.removeAllListeners();
            mPointAnimation = null;
        }

        final PointF startF = new PointF();
        final PointF endF = new PointF();
        final PointF controllF = new PointF();

//        int[] startLocation = new int[2];
//        view.getLocationInWindow(startLocation);
//
//        final int startX = startLocation[0];
//        final int startY = startLocation[1];
        startF.x = startX;
        startF.y = startY;
        endF.y = targetY;
        endF.x = targetX;
        controllF.x = endF.x;
        controllF.y = startF.y;

        view.setX(startX);
        view.setY(startY);
        view.setVisibility(View.VISIBLE);
        mPointAnimation = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        mPointAnimation.setDuration(mDuration);
        mPointAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                PointF pointF = (PointF) animation.getAnimatedValue();

//                view.setTranslationX(pointF.x - startF.x);
//                view.setTranslationY(pointF.y - startF.y);
                view.setX(pointF.x);
                view.setY(pointF.y);
                if (pointF.x == startF.x && pointF.y == startF.y) {
                    //开始
                    if (mPointListener != null) {
                        mPointListener.onAnimationStart(animation,TYPE_POINT,startF,endF,pointF);
                    }

                } else if (pointF.x == endF.x && pointF.y == endF.y) {
                    //结束
                    if (mPointListener != null) {
                        mPointListener.onAnimationEnd(animation,TYPE_POINT,startF,endF,pointF);
                    }
                }else{

                    if(mPointListener != null){

                        mPointListener.onAnimationUpdata(animation,TYPE_POINT,startF,endF,pointF);
                    }
                }
            }
        });
        mPointAnimation.start();
        return mPointDuration;
    }

    @Override
    public void onSumpAnimation(final View view, final int startX,final int startY , final int targetX, final int targetY) {

        if (mSumpAnimation != null) {
            mSumpAnimation.cancel();
            mSumpAnimation.removeAllListeners();
            mSumpAnimation = null;
        }

        final PointF startF = new PointF();
        final PointF endF = new PointF();
        final PointF controllF = new PointF();

//        int[] startLocation = new int[2];
//        view.getLocationInWindow(startLocation);
//
//        final int startX = startLocation[0];
//        final int startY = startLocation[1];
        startF.x = startX;
        startF.y = startY;
        endF.y = targetY;
        endF.x = targetX;
//        controllF.x = endF.x;
//        controllF.y = startF.y;
        controllF.x = (endF.x + endF.x)/2;
        controllF.y = (endF.y + startX)/2;
        mSumpAnimation = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        mSumpAnimation.setDuration(mDuration);
        mSumpAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                PointF pointF = (PointF) animation.getAnimatedValue();

//                view.setTranslationX(pointF.x - startF.x);
//                view.setTranslationY(pointF.y - startF.y);
                view.setX(pointF.x);
                view.setY(pointF.y);
                if (pointF.x == startF.x && pointF.y == startF.y) {
                    //开始
                    if (mSumpListener != null) {
                        mSumpListener.onAnimationStart(animation,TYPE_SUMP,startF,endF,pointF);
                    }

                } else if (pointF.x == endF.x && pointF.y == endF.y) {
                    //结束
                    if (mSumpListener != null) {
                        mSumpListener.onAnimationEnd(animation,TYPE_SUMP,startF,endF,pointF);
                    }
                }else{

                    if(mSumpListener != null){
                        mSumpListener.onAnimationUpdata(animation,TYPE_SUMP,startF,endF,pointF);
                    }
                }
            }
        });
        mSumpAnimation.start();
    }

    @Override
    public void onClearAnimations() {

        if (mPointAnimation != null) {

            mPointAnimation.cancel();
            mPointAnimation.removeAllListeners();
            mPointAnimation = null;
        }

        if (mSumpAnimation != null) {

            mSumpAnimation.cancel();
            mSumpAnimation.removeAllListeners();
            mSumpAnimation = null;
        }
    }

    public interface AnimationListener {

        void onAnimationStart(Animator animation,int type,PointF startF, PointF endF, PointF pointF);

        void onAnimationUpdata(Animator animation,int type,PointF startF, PointF endF, PointF pointF);

        void onAnimationEnd(Animator animation,int type,PointF startF, PointF endF, PointF pointF);
    }
}
