package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.ServersEntity;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.util.PublicTool;

/**
 * Created by Lyy on 2015/8/31.
 * 核心政务适配器
 */
public class GovernmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<ServersEntity> mServersEntities;

    public GovernmentAdapter(Context context, List<ServersEntity> serversEntities) {
        this.mContext = context;
        this.mServersEntities = serversEntities;

    }

    @Override
    public int getCount() {
        return mServersEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mServersEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ServersEntity serversEntity = mServersEntities.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.government_item, null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.img_left);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            int weight = PublicTool.getDeviceWidth();
            LinearLayout.LayoutParams mLayoutParams=new  LinearLayout.LayoutParams(
                    weight/2, weight/4);
            viewHolder.mImageView.setLayoutParams(mLayoutParams);
            Imageload.LoadImag(mContext, serversEntity.getImageUrl(), viewHolder.mImageView);
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView mImageView;
    }
}
