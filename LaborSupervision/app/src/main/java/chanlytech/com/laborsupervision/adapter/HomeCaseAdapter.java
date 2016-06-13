package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.NewsEntity;

/**
 * Created by Lyy on 2015/9/1.
 */
public class HomeCaseAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsEntity>mNewsEntities;
    public HomeCaseAdapter(Context context,List<NewsEntity>newsEntities) {
        this.mContext = context;
        this.mNewsEntities=newsEntities;
    }

    @Override
    public int getCount() {
        return mNewsEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        NewsEntity newsEntity = mNewsEntities.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.homecase_item, null);
            viewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.mTextView_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView_name.setText(newsEntity.getTitle());
        viewHolder.mTextView_number.setText("评论("+newsEntity.getComm_count()+")");
        return convertView;
    }


    public class ViewHolder {
        public TextView mTextView_name;
        public TextView mTextView_number;
    }
}
