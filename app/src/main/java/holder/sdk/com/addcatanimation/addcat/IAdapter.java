package holder.sdk.com.addcatanimation.addcat;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by yangjian on 2018/12/28.
 */

public interface IAdapter<D> {

    /**
     * 创建移动的View
     * @param context
     * @param data
     * @return
     */
    View onCreateView(Context context , D data,float x,float y);


    /**
     * 移动point
     * @param view
     * @param startF
     * @param endF
     * @param controllF
     */
    void onPointTranslation(View view,PointF startF,PointF endF,PointF controllF);

    /**
     * 下滑移动point
     * @param view
     * @param startF
     * @param endF
     * @param controllF
     */
    void onSumpTranslation(View view,View targetView,PointF startF,PointF endF,PointF controllF);


    /**
     * point动画结束后停留的时间
     * @param view
     * @return
     */
    int onPointPauseTime(View view);
}
