package holder.sdk.com.addcatanimation.addcat.helper;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Constructor;

import holder.sdk.com.addcatanimation.addcat.adapter.BaseAnimAdapter;
import holder.sdk.com.addcatanimation.addcat.anim.impl.AddCatAnimImpl;

/**
 * Created by yangjian on 2018/12/28.
 */

public final class AnimationHelper<D> implements AddCatAnimImpl.AnimationListener{

    private BaseAnimAdapter<D> mAdapter;

    private ViewGroup mContentView;

    private AddCatAnimImpl mAddCatImpl;

    private int[] mPosition = new int[2];

    private FrameLayout mAnimLayout;

    private boolean isFinish = true;

    private Context mContext;

    private Handler mHandler = new Handler();

    private View mTargetView;

    public AnimationHelper(@NonNull Context context) {
        this.mContext = context;
        initContent(context);
    }

    private void initContent(Context context){

        mContentView = ((Activity)context).findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mAnimLayout = new FrameLayout(context);
        mAnimLayout.setLayoutParams(params);
        mContentView.addView(mAnimLayout);
        mAddCatImpl = new AddCatAnimImpl()
        .setSumpListener(this)
        .setPointListener(this);
    }

    public AnimationHelper<D> setAdapter(Class< ? extends BaseAnimAdapter<D>> cls){

        if(cls == null){
            throw new NullPointerException("cls is null");
        }
        this.mAdapter = onCreateAdapter(cls);
        if(mAdapter == null){
            throw new NullPointerException("adapter is null");
        }
        return this;
    }

    private BaseAnimAdapter<D> onCreateAdapter(Class< ? extends BaseAnimAdapter<D>> cls){

        try {
            Constructor constructor = cls.getConstructor(Context.class,AnimationHelper.class);
            return (BaseAnimAdapter<D>) constructor.newInstance(mContext,this);
        } catch (Exception e) {
            throw new NullPointerException(cls.getCanonicalName() + " is null");
        }
    }

    public void startAnimation(final View view,D data,final int pointX,final int pointY,View targetView){

        if(view == null || view.getParent() == null || !isFinish ||
                mAdapter == null){
            return;
        }
        if(mAddCatImpl != null){

            mAddCatImpl.onClearAnimations();
        }
        mAnimLayout.removeAllViews();
        final int[] startLocation = new int[2];
        view.getLocationOnScreen(startLocation);
        final int[] targetLocation = new int[2];
        targetView.getLocationOnScreen(targetLocation);
        mContentView.getLocationOnScreen(mPosition);
        if(mAdapter != null){
            final View adapterView = mAdapter.onCreateView(view.getContext(),data,startLocation[0],startLocation[1]);
            adapterView.setVisibility(View.INVISIBLE);
            mAnimLayout.addView(adapterView);
            mTargetView = targetView;
            adapterView.post(new Runnable() {
                @Override
                public void run() {
                    startLocation[0] = startLocation[0] + (view.getMeasuredWidth() - adapterView.getMeasuredWidth()) /2;
                    startLocation[1] = startLocation[1] + (view.getMeasuredHeight() - adapterView.getMeasuredHeight()) /2;
                    mAddCatImpl.onPointAnimation(adapterView,
                            startLocation[0] < 0 ? 0 : startLocation[0],
                            startLocation[1] - mPosition[1] < 0 ? 0 : startLocation[1] - mPosition[1],
                            pointX < 0 ? 0 : pointX,
                            pointY - mPosition[1] < 0 ? 0 : pointY - mPosition[1]);
                }
            });

        }
    }

    private void startTargetAnimation(){

        if(mAnimLayout.getChildCount() <= 0 || mTargetView == null){
            isFinish = true;
            return;
        }
        View view = mAnimLayout.getChildAt(0);
        if(view == null || view.getParent() == null ||
                mAdapter == null){
            isFinish = true;
            return;
        }
        if(mAddCatImpl != null){

            mAddCatImpl.onClearAnimations();
        }

        final int[] startLocation = new int[2];
        view.getLocationOnScreen(startLocation);
        final int[] targetLocation = new int[2];
        mTargetView.getLocationOnScreen(targetLocation);
        mContentView.getLocationOnScreen(mPosition);
        if(mAdapter != null){

            mAddCatImpl.onSumpAnimation(view,
                    startLocation[0],
                    startLocation[1] - mPosition[1],
                    targetLocation[0],
                    targetLocation[1] - mPosition[1]);
        }
    }

    @Override
    public void onAnimationStart(Animator animation, int type,PointF startF, PointF endF, PointF pointF) {
        if(mAnimLayout.getChildAt(0) == null){
            isFinish = true;
            return;
        }
        isFinish = false;
        if(type == AddCatAnimImpl.TYPE_POINT){

            if(mAdapter != null){

                mAdapter.onPointTranslation(mAnimLayout.getChildAt(0),startF,endF,pointF);
            }
        }else if(type == AddCatAnimImpl.TYPE_SUMP){
            if(mAdapter != null){

                mAdapter.onSumpTranslation(mAnimLayout.getChildAt(0),mTargetView,startF,endF,pointF);
            }
        }
    }

    @Override
    public void onAnimationUpdata(Animator animation, int type,PointF startF, PointF endF, PointF pointF) {

        if(mAnimLayout.getChildAt(0) == null){
            return;
        }
        if(type == AddCatAnimImpl.TYPE_POINT){

            if(mAdapter != null){

                mAdapter.onPointTranslation(mAnimLayout.getChildAt(0),startF,endF,pointF);
            }
        }else if(type == AddCatAnimImpl.TYPE_SUMP){


            if(mAdapter != null){

                mAdapter.onSumpTranslation(mAnimLayout.getChildAt(0),mTargetView,startF,endF,pointF);
            }
        }
    }

    @Override
    public void onAnimationEnd(Animator animation, int type, PointF startF, PointF endF, PointF pointF) {

        if(mAnimLayout.getChildAt(0) == null){
            isFinish =true;
            return;
        }
        if(type == AddCatAnimImpl.TYPE_POINT){
            if(mAdapter != null){

                mAdapter.onPointTranslation(mAnimLayout.getChildAt(0),startF,endF,pointF);
                int duration = mAdapter.onPointPauseTime(mAnimLayout.getChildAt(0));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startTargetAnimation();
                    }
                }, duration < 0 ? 0 : duration);
            }else{
                isFinish =true;
            }

        }else if(type == AddCatAnimImpl.TYPE_SUMP){

            isFinish =true;
            if(mAdapter != null){

                mAdapter.onSumpTranslation(mAnimLayout.getChildAt(0),mTargetView,startF,endF,pointF);
            }
            if(mAddCatImpl != null){

                mAddCatImpl.onClearAnimations();
            }
        }
    }
}
