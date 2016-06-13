package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.CaseAnalysisActivity;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.Imageload;

/**
 * Created by Lyy on 2015/7/22.
 */
public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsEntity> mNewsEntities;

    public NewsAdapter(Context context, List<NewsEntity> newsEntities) {
        this.mContext = context;
        this.mNewsEntities = newsEntities;
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
        ViewHolder mViewHolder = null;
        final NewsEntity newsEntity = mNewsEntities.get(position);
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.news_item, null);
            mViewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.tv_name);
            mViewHolder.mTextView_number = (TextView) convertView.findViewById(R.id.tv_num);
            mViewHolder.mLayout= (LinearLayout) convertView.findViewById(R.id.ll_all);
            mViewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTextView_name.setText(newsEntity.getTitle());
        mViewHolder.mTextView_number.setText(newsEntity.getCreatTime());
        Imageload.LoadImag(mContext,newsEntity.getImageUrl(),mViewHolder.imageView);
        mViewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Constants.isFastClick()){
                        if(newsEntity.getCatid().equals("2")){//案例分析
                            Intent intent=new Intent(mContext, CaseAnalysisActivity.class);
                            intent.putExtra("title",newsEntity.getTitle());
                            intent.putExtra("url",newsEntity.getUrl());
                            intent.putExtra("id",newsEntity.getId());
                            mContext.startActivity(intent);
                        }else {
                            Intent mIntent=new Intent(mContext, WebActivity.class);
                            mIntent.putExtra("title",newsEntity.getTitle());
                            mIntent.putExtra("url",newsEntity.getUrl());
                            mIntent.putExtra("objid",newsEntity.getId());
                            mIntent.putExtra("type",1);
                            mContext.startActivity(mIntent);
                        }
                    }


                }
            });
        return convertView;
    }


    public class ViewHolder {
        public TextView mTextView_name;
        public TextView mTextView_number;
        public LinearLayout mLayout;
        public ImageView imageView;
    }
}
