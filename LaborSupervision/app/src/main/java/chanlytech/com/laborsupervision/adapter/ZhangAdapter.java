package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.ChapterEntity;

/**
 * Created by Lyy on 2015/12/2.
 */
public class ZhangAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChapterEntity>mChapterEntities;
    public ZhangAdapter(Context context,List<ChapterEntity>chapterEntities){
        this.mContext=context;
        this.mChapterEntities=chapterEntities;
    }
    @Override
    public int getCount() {
        return mChapterEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mChapterEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        ChapterEntity chapterEntity = mChapterEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.zhang_item,null);
            mViewHolder.mTextView= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTextView.setText(chapterEntity.getName());
        return convertView;
    }


    class ViewHolder{
        TextView mTextView;
    }
}
