package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.QuestionEntity;

/**
 * Created by Lyy on 2016/1/27.
 */
public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<QuestionEntity>mQuestionEntities;
    public QuestionAdapter(Context context,List<QuestionEntity>questionEntities){
        this.mContext=context;
        this.mQuestionEntities=questionEntities;
    }

    @Override
    public int getCount() {
        return mQuestionEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuestionEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        QuestionEntity questionEntity = mQuestionEntities.get(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.question_item,null);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.check= (ImageView) convertView.findViewById(R.id.check);
            viewHolder.line=convertView.findViewById(R.id.line);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(questionEntity.getTitle());
        if(questionEntity.getIsCheck()==0){
            viewHolder.check.setBackgroundResource(R.mipmap.ic_check);
        }else {
            viewHolder.check.setBackgroundResource(R.mipmap.ic_no_check);
        }
        if(position==mQuestionEntities.size()-1){
            viewHolder.line.setVisibility(View.GONE);
        }else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    class ViewHolder{
        TextView tv_name;
        ImageView check;
        View line;
    }
}
