package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.BookEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;

/**
 * Created by Lyy on 2015/12/1.
 */
public class CodeAdapter extends BaseAdapter {
    private Context mContext;
    private List<BookEntity>mBookEntities;
    public CodeAdapter(Context context,List<BookEntity>bookEntities){
        this.mContext=context;
        this.mBookEntities=bookEntities;
    }
    @Override
    public int getCount() {
        return mBookEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        BookEntity bookEntity = mBookEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.code_item,null);
            mViewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_name.setText(bookEntity.getName());
        if(bookEntity.isCheck()){
            mViewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.red));
        }else {
            mViewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_name;
    }
}
