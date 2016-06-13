package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.CityEntity;

/**
 * Created by Lyy on 2015/11/10.
 */
public class AddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityEntity> mCityEntities;
    public AddressAdapter(Context context,List<CityEntity>cityEntities){
        this.mContext=context;
        this.mCityEntities=cityEntities;
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
        CityEntity cityEntity = mCityEntities.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.address_view,null);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.iv_check= (ImageView) convertView.findViewById(R.id.iv_check);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(cityEntity.getName());
        if(cityEntity.isCheck()==0){
            viewHolder.iv_check.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_check.setVisibility(View.GONE);
        }
        return convertView;
    }


    class  ViewHolder{
        public TextView tv_name;
        public ImageView iv_check;
    }
}
