package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import chanlytech.com.laborsupervision.R;

/**
 * Created by Lyy on 2015/9/10.
 */
public class MyTestAdapter extends BaseAdapter {
    private Context mContext;

    private MyTestAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.mytest_item,null);
            viewHolder.mTextView_titime= (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mTextView_title= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    public class ViewHolder{
        public TextView mTextView_title;
        public TextView mTextView_titime;
    }
}
