package holder.sdk.com.addcatanimation;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import holder.sdk.com.addcatanimation.adapter.ListViewAdapter;
import holder.sdk.com.addcatanimation.test.data.TestData;

public class MainActivity extends FragmentActivity{

    private ListView mListView;

    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = this.findViewById(R.id.listview);
        mAdapter = new ListViewAdapter(this,findViewById(R.id.target_view));
        mListView.setAdapter(mAdapter);
        int[] resIds = new int[3];
        resIds[0] = R.drawable.image_1;
        resIds[1] = R.drawable.image_2;
        resIds[2] = R.drawable.image_3;
        List<TestData> datalist = new ArrayList<>();
        for (int i = 0 ; i < 30 ; i ++){

            TestData data = new TestData();
            data.name = "大家好，才是真的好" + i;
            data.resId = resIds[i % 3];
            datalist.add(data);
        }
        mAdapter.setData(datalist);
    }
}
