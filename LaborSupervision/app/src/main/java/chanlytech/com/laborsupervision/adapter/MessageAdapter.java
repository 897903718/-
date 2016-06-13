package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.util.Imageload;

/**
 * Created by Lyy on 2015/9/10.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageEntity> mMessageEntities;

    public MessageAdapter(Context context, List<MessageEntity> messageEntities) {
        this.mContext = context;
        this.mMessageEntities = messageEntities;
    }

    @Override
    public int getCount() {
        return mMessageEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        MessageEntity messageEntity = mMessageEntities.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.message_item, null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.iv_msg);
            viewHolder.mTextView_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTextView_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.mTextView_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView_time.setText(messageEntity.getTime() + "");
        viewHolder.mTextView_title.setText(messageEntity.getTitle());
        viewHolder.mTextView_content.setText(messageEntity.getDescription());
        if(messageEntity.getImageUrl().equals("0")){
            viewHolder.mImageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mImageView.setVisibility(View.GONE);
        }
//        Imageload.LoadImag(mContext, messageEntity.getImageUrl(), viewHolder.mImageView);
        return convertView;
    }


    public class ViewHolder {
        public ImageView mImageView;
        public TextView mTextView_title;
        public TextView mTextView_time;
        public TextView mTextView_content;
    }
}
