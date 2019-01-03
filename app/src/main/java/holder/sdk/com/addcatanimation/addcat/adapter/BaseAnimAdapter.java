package holder.sdk.com.addcatanimation.addcat.adapter;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import holder.sdk.com.addcatanimation.addcat.IAdapter;
import holder.sdk.com.addcatanimation.addcat.helper.AnimationHelper;

/**
 * Created by yangjian on 2018/12/28.
 */

public abstract class BaseAnimAdapter<D> implements IAdapter<D>{

    private Context mContext;

    private AnimationHelper mLayout;

    public BaseAnimAdapter(Context context, AnimationHelper layout){

        this.mContext = context;
        this.mLayout = layout;
    }

    @Override
    public void onPointTranslation(View view, PointF startF, PointF endF, PointF controllF) {

    }

    @Override
    public void onSumpTranslation(View view, View targetView,PointF startF, PointF endF, PointF controllF) {

    }

    public Context getContext() {
        return mContext;
    }

    public AnimationHelper getContentLayout(){

        return mLayout;
    }
}
