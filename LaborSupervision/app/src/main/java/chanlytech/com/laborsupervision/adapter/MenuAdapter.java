package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.MenueEntity;
import chanlytech.com.laborsupervision.util.Imageload;

/**
 * Created by Lyy on 2015/11/2.
 */
public class MenuAdapter extends BaseAdapter {
    private List<MenueEntity>mMenueEntities;
    private Context mContext;
    public MenuAdapter(Context context,List<MenueEntity>menueEntities){
        this.mContext=context;
        this.mMenueEntities=menueEntities;
    }

    @Override
    public int getCount() {
        return mMenueEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenueEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        MenueEntity menueEntity = mMenueEntities.get(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.user_menue_item,null);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.tv_myorder);
            viewHolder.imageView_icon= (ImageView) convertView.findViewById(R.id.iv_order);
            viewHolder.imageView_show= (ImageView) convertView.findViewById(R.id.iv_myorder_meg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(menueEntity.getName());
        Imageload.LoadImag(mContext,menueEntity.getImg(),viewHolder.imageView_icon);
        if(menueEntity.getIsUpdate()==0){
            viewHolder.imageView_show.setVisibility(View.GONE);
        }else {
            viewHolder.imageView_show.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    public class  ViewHolder{
        TextView textView;
        ImageView imageView_icon,imageView_show;
    }
}
