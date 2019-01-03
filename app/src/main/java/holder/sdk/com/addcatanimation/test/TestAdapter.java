package holder.sdk.com.addcatanimation.test;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import holder.sdk.com.addcatanimation.R;
import holder.sdk.com.addcatanimation.addcat.adapter.BaseAnimAdapter;
import holder.sdk.com.addcatanimation.addcat.helper.AnimationHelper;
import holder.sdk.com.addcatanimation.test.data.TestData;

/**
 * Created by yangjian on 2018/12/29.
 */

public class TestAdapter extends BaseAnimAdapter<TestData>{

    private int count = 0;

    public TestAdapter(Context context, AnimationHelper layout) {
        super(context, layout);
    }

    @Override
    public View onCreateView(Context context, TestData data, float x, float y) {

        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                220,
                220
        );
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(data.resId);
        return imageView;
    }

    @Override
    public void onPointTranslation(View view, PointF startF, PointF endF, PointF controllF) {
        super.onPointTranslation(view, startF, endF, controllF);
        float startY = startF.y;
        float endY = endF.y;
        float curentY = controllF.y;

        float scale = 0.2f + ((float)(startY - curentY)/(startY - endY)) * 0.8f;
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    @Override
    public int onPointPauseTime(final View view) {

        ValueAnimator animator = ValueAnimator.ofInt(100);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int curent = (int) animation.getAnimatedValue();
                if(curent < 30){

                    float scale = (float) (1f - ((float)curent / 30) * 0.5);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }else if(curent < 60){
                    float scale = (float) (0.5 + ((float)(curent - 30) / 30) * 0.9);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }else{
                    float scale = (float) (1.4f - ((float)(curent - 60) / 40) * 0.4);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            }
        });
        animator.start();
        return 508;
    }

    @Override
    public void onSumpTranslation(View view, final View target,PointF startF, PointF endF, PointF controllF) {
        super.onSumpTranslation(view,target,startF, endF, controllF);
        float endY = endF.y;
        float curentY = controllF.y;

        float startX = startF.x;
        float endX = endF.x;
        float curentX = controllF.x;

        float scale = 0.2f + ((float)(endX - curentX)/(endX - startX)) * 0.8f;
        view.setScaleX(scale);
        view.setScaleY(scale);

        if(controllF.x == endF.x && curentY == endY){
            //结束了
            view.setVisibility(View.GONE);
            count++;
            if(count > 0){

                TextView textView = target.findViewById(R.id.target_view_textview);
                textView.setVisibility(View.VISIBLE);
                textView.setText(count + "");
            }
            ValueAnimator animator = ValueAnimator.ofInt(100);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int curent = (int) animation.getAnimatedValue();
                    if(curent < 30){

                        float scale = (float) (1f - ((float)curent / 30) * 0.6);
                        target.setScaleX(scale);
                        target.setScaleY(scale);
                    }else if(curent < 60){
                        float scale = (float) (0.4 + ((float)(curent - 30) / 30) * 1.1);
                        target.setScaleX(scale);
                        target.setScaleY(scale);
                    }else{
                        float scale = (float) (1.5f - ((float)(curent - 60) / 40) * 0.5);
                        target.setScaleX(scale);
                        target.setScaleY(scale);
                    }
                }
            });
            animator.start();
        }
    }
}
