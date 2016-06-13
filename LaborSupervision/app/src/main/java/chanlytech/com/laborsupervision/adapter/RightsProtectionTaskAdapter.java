package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.RightsDetailsActivity;
import chanlytech.com.laborsupervision.entiy.RightsEntity;

/**
 * Created by Lyy on 2016/1/27.
 */
public class RightsProtectionTaskAdapter extends BaseAdapter {
    private Context mContext;
    private List<RightsEntity>mRightsEntities;
    public RightsProtectionTaskAdapter(Context context,List<RightsEntity>rightsEntities){
        this.mContext=context;
        this.mRightsEntities=rightsEntities;
    }
    @Override
    public int getCount() {
        return mRightsEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mRightsEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        final RightsEntity rightsEntity = mRightsEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.rightsprotectiontask_item,null);
            mViewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            mViewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            mViewHolder.mLinearLayout= (LinearLayout) convertView.findViewById(R.id.ll_all);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_title.setText(rightsEntity.getTitle());
        mViewHolder.tv_time.setText(rightsEntity.getTime());
        mViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, RightsDetailsActivity.class);
                mIntent.putExtra("id",rightsEntity.getId());
                mContext.startActivity(mIntent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView tv_title,tv_time;
        LinearLayout mLinearLayout;
    }
}
