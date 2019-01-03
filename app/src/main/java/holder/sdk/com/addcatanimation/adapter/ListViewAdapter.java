package holder.sdk.com.addcatanimation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import holder.sdk.com.addcatanimation.R;
import holder.sdk.com.addcatanimation.addcat.helper.AnimationHelper;
import holder.sdk.com.addcatanimation.test.TestAdapter;
import holder.sdk.com.addcatanimation.test.data.TestData;

/**
 * Created by yangjian on 2019/1/3.
 */

public class ListViewAdapter extends BaseAdapter {

    private List<TestData> mDatas;

    private TestAdapter adapter;

    private AnimationHelper<TestData> mAnimLayout;

    private View mTargetView;

    public ListViewAdapter(Context context,View targetView) {

        mAnimLayout = new AnimationHelper<TestData>(context)
                .setAdapter(TestAdapter.class);
        adapter = new TestAdapter(context, mAnimLayout);
        mTargetView = targetView;
    }

    public void setData(List<TestData> datas) {

        if (mDatas == null) {

            mDatas = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_test_text, null);

            holder = new Holder();
            holder.mImageView = convertView.findViewById(R.id.image);
            holder.mTextView = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }
        holder.mTextView.setText(mDatas.get(position).name);
        holder.mImageView.setImageResource(mDatas.get(position).resId);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] startLocation = new int[2];
                v.getLocationOnScreen (startLocation);
                //  mImpl.onPointAnimation(v,startLocation[0],startLocation[1]);

                int viewWidth = v.getMeasuredWidth();
//                int viewHeight = mTargetView.getMeasuredHeight();
                TestData data = mDatas.get(position);
                mAnimLayout.startAnimation(v, data, startLocation[0] + viewWidth - 100, startLocation[1] - 130, mTargetView);
            }
        });
        return convertView;
    }

    public class Holder {

        private TextView mTextView;

        private ImageView mImageView;
    }
}
