package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.CityEntity;

/**
 * Created by Lyy on 2015/7/23.
 */
public class CityAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityEntity> mCityEntities;

    public CityAdapter(Context context, List<CityEntity> cityEntities) {
        this.mContext = context;
        this.mCityEntities = cityEntities;
    }

    @Override
    public int getCount() {
        return mCityEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        final CityEntity cityEntity = mCityEntities.get(position);
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.city_item, null);
            mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTextView.setText(cityEntity.getName());
        return convertView;
    }


    public class ViewHolder {
        public TextView mTextView;
    }
}
