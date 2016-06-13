package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Lyy on 2015/3/12.
 * 可扩展的适配器
 */
public abstract class OrdinaryAdapter<T, ViewHolder extends OrdinaryViewHolder> extends BaseAdapter {

    private LayoutInflater mInflater;
    private int mLayoutId;
    private List<T> mData;
    private Context mContext;

    public OrdinaryAdapter(Context context, List<T> data) {
        mInflater = LayoutInflater.from(context);
        mLayoutId = setLayoutId();
        mData = data;
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    protected abstract int setLayoutId();

    @Override
    public int getCount() {
        //2015-10-10改
        if(mData==null){
            return 0;
        }else {
            return mData.size();
        }

    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void bindData(int position, ViewHolder helper, T item);

    public abstract ViewHolder getViewHolder(View convertView);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, null);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindData(position, viewHolder, mData.get(position));
        return convertView;
    }

}