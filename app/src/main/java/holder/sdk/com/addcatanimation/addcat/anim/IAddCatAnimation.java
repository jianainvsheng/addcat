package holder.sdk.com.addcatanimation.addcat.anim;

import android.view.View;

/**
 * Created by yangjian on 2018/12/28.
 */

public interface IAddCatAnimation {

    /**
     * 滑动到指定的point
     * @param view
     * @param startX
     * @param startY
     * @param targetX
     * @param targetY
     * @return
     */
    int onPointAnimation(View view, final int startX,final int startY , final int targetX, final int targetY);

    /**
     * 下落动画
     * @param view
     * @param startX
     * @param startY
     * @param targetX
     * @param targetY
     */
    void onSumpAnimation(View view , final int startX,final int startY , final int targetX, final int targetY);

    /**
     * 释放anim
     */
    void onClearAnimations();
}
