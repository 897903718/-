package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import chanlytech.com.laborsupervision.R;

/**
 * Created by Lyy on 2015/9/22.
 */
public class ReservationAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    // 一级标签上的状态图片数据源
    int[] group_state_array = new int[] { R.mipmap.group_down,
            R.mipmap.group_up };
    // 这个数组是用来存储一级item的点击次数的，根据点击次数设置一级标签的选中、为选中状态
    private int[] group_checked = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public ReservationAdapter(Context context ){
        this.mContext=context;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.group,null);
            // 新建一个TextView对象，用来显示一级标签上的标题信息
            TextView group_title = (TextView) convertView
                    .findViewById(R.id.group_title);
            // 新建一个TextView对象，用来显示一级标签上的大体描述的信息
            TextView group_text = (TextView) convertView
                    .findViewById(R.id.tv_number);
            // 新建一个ImageView对象，根据用户点击来标识一级标签的选中状态
            ImageView group_state = (ImageView) convertView
                    .findViewById(R.id.group_state);
            if(isExpanded){
                group_state.setBackgroundResource(group_state_array[1]);
            }else {
                group_state.setBackgroundResource(group_state_array[0]);
            }

//        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(mContext,R.layout.child,null);
            TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            TextView tv_state= (TextView) convertView.findViewById(R.id.tv_state);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
