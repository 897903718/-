package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import chanlytech.com.laborsupervision.R;

/**
 * Created by Lyy on 2015/8/31.
 */
public class OcupationAdapter extends BaseAdapter {
    private Context mContext;
    public OcupationAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
       if(convertView==null){
           mViewHolder=new ViewHolder();
           convertView=View.inflate(mContext, R.layout.ocupation_item,null);
           mViewHolder.mImageView= (ImageView) convertView.findViewById(R.id.text_pic);
           mViewHolder.mTextView= (TextView) convertView.findViewById(R.id.tv_name);
           convertView.setTag(mViewHolder);
       }else {
           mViewHolder= (ViewHolder) convertView.getTag();
       }

        return convertView;
    }


    public class ViewHolder{
    public TextView mTextView;
    public ImageView mImageView;
    }
}
