package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.RightsEntity;
import chanlytech.com.laborsupervision.util.Spannable;

/**
 * Created by Lyy on 2016/1/27.
 */
public class ProgressAdapter extends BaseAdapter {
    private Context mContext;
    private List<RightsEntity>mRightsEntities;
    public ProgressAdapter(Context context,List<RightsEntity>rightsEntities){
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
        RightsEntity rightsEntity = mRightsEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.progress_item,null);
            mViewHolder.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            mViewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            mViewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_company_name);
            mViewHolder.tv_address= (TextView) convertView.findViewById(R.id.tv_comment_address);
            mViewHolder.tv_phone= (TextView) convertView.findViewById(R.id.tv_comment_phone);
            mViewHolder.tv_rights_phone= (TextView) convertView.findViewById(R.id.tv_rights_phone);
            mViewHolder.tv_quest= (TextView) convertView.findViewById(R.id.tv_question);
            mViewHolder.tv_state= (TextView) convertView.findViewById(R.id.tv_state);
            mViewHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(mViewHolder);
        }else {
           mViewHolder= (ViewHolder) convertView.getTag();
        }
            mViewHolder.tv_number.setText(Spannable.matcherSearchTitle("维权编号："+rightsEntity.getCode(),rightsEntity.getCode(),0xFF333333));
            mViewHolder.tv_time.setText(Spannable.matcherSearchTitle("提交时间："+rightsEntity.getAddtime(),rightsEntity.getAddtime(),0xFF333333));
            mViewHolder.tv_name.setText(Spannable.matcherSearchTitle("单位名称："+rightsEntity.getEnterpriseName(),rightsEntity.getEnterpriseName(),0xFF333333));
            mViewHolder.tv_address.setText(Spannable.matcherSearchTitle("单位地址："+rightsEntity.getAddress(),rightsEntity.getAddress(),0xFF333333));
            mViewHolder.tv_phone.setText(Spannable.matcherSearchTitle("单位电话："+rightsEntity.getPhone(),rightsEntity.getPhone(),0xFF333333));
//            mViewHolder.tv_rights_phone.setText(Spannable.matcherSearchTitle("维权人电话："+rightsEntity.getUserphone(),rightsEntity.getUserphone(),0xFF333333));
            mViewHolder.tv_rights_phone.setText(rightsEntity.getUserphone());
            mViewHolder.tv_quest.setText(Spannable.matcherSearchTitle("反映问题："+rightsEntity.getProblemstr(),rightsEntity.getProblemstr(),0xFF333333));
           if (rightsEntity.getFlag()==1){
               //待处理
               mViewHolder.tv_state.setText(Spannable.matcherSearchTitle("进度状态：" + rightsEntity.getFlagname(), rightsEntity.getFlagname(), 0xFF5FC747));
               mViewHolder.tv_content.setText(rightsEntity.getRemark());
               mViewHolder.tv_content.setTextColor(0xFF5FC747);

           }else if (rightsEntity.getFlag()==2){
               //已指派
               mViewHolder.tv_state.setText(Spannable.matcherSearchTitle("进度状态："+rightsEntity.getFlagname(),rightsEntity.getFlagname(),0xFF81C3F3));
               mViewHolder.tv_content.setText(rightsEntity.getRemark());
               mViewHolder.tv_content.setTextColor(0xFF81C3F3);
           }else if(rightsEntity.getFlag()==3){
               //已处理
               mViewHolder.tv_state.setText(Spannable.matcherSearchTitle("进度状态："+rightsEntity.getFlagname(),rightsEntity.getFlagname(),0xFFEB908E));
               mViewHolder.tv_content.setText(rightsEntity.getRemark());
               mViewHolder.tv_content.setTextColor(0xFFEB908E);

           }else if(rightsEntity.getFlag()==4){
               //4已联系
               mViewHolder.tv_state.setText(Spannable.matcherSearchTitle("进度状态："+rightsEntity.getFlagname(),rightsEntity.getFlagname(),0xFF333333));
               mViewHolder.tv_content.setText(rightsEntity.getRemark());
               mViewHolder.tv_content.setTextColor(0xFF333333);
           }else if(rightsEntity.getFlag()==5){
               // 5已处理
               mViewHolder.tv_state.setText(Spannable.matcherSearchTitle("进度状态："+rightsEntity.getFlagname(),rightsEntity.getFlagname(),0xFF333333));
               mViewHolder.tv_content.setText(rightsEntity.getRemark());
               mViewHolder.tv_content.setTextColor(0xFF333333);
           }
        return convertView;
    }

    class ViewHolder{
        TextView tv_number,tv_time,tv_name,tv_address,tv_phone,tv_rights_phone,tv_quest,tv_state,tv_content;
    }
}
