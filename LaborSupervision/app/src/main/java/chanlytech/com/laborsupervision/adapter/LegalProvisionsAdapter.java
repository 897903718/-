package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arialyy.frame.util.show.T;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.fragment.LegalProvisionsFragment;

/**
 * Created by Lyy on 2015/12/1.
 */
public class LegalProvisionsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsEntity>mNewsEntities;
    public LegalProvisionsAdapter(Context context,List<NewsEntity>newsEntities){
        this.mContext=context;
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
        ViewHolder mViewHolder=null;
        final NewsEntity newsEntity = mNewsEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.legalprovisions_item,null);
            mViewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            mViewHolder.tv_chuchu= (TextView) convertView.findViewById(R.id.tv_chuchu);
            mViewHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            mViewHolder.tv_keyword= (TextView) convertView.findViewById(R.id.tv_keyword);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_name.setText(newsEntity.getTitle());
        mViewHolder.tv_chuchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showLong(mContext, newsEntity.getSource());
            }
        });
        mViewHolder.tv_content.setText(newsEntity.getDescription());
        mViewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, WebActivity.class);
                mIntent.putExtra("title",newsEntity.getTitle());
                mIntent.putExtra("url",newsEntity.getUrl());
                mIntent.putExtra("objid",newsEntity.getId());
                mIntent.putExtra("type",1);
                LegalProvisionsFragment.isEnter=true;
                mContext.startActivity(mIntent);
            }
        });
        mViewHolder.tv_keyword.setText(newsEntity.getKeywords());
        return convertView;
    }


    class ViewHolder{
        TextView tv_name,tv_chuchu,tv_content,tv_keyword;
    }
}
