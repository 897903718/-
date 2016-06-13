package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.entiy.ServersEntity;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.util.PublicTool;

/**
 * Created by Lyy on 2015/7/8.
 * 首页服务Adapter
 */
public class HomeServerAdapter extends OrdinaryAdapter<ServersEntity, HomeServerAdapter.ServerHolder> {
    private Context mContext;
    private int index;
    private List<ServersEntity> mServersEntities;

    public HomeServerAdapter(Context context, List<ServersEntity> data, int index) {
        super(context, data);
        this.mContext = context;
        this.index = index;
        this.mServersEntities=data;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_home_list;
    }

    @Override
    public void bindData(int position, ServerHolder helper, ServersEntity item) {
//        helper.leftImg.setImageUrl(item.getUrl(), new ImageLoader(Volley.newRequestQueue(getContext()),new BitmapCache(new CacheUtil(getContext(), true))));
//        ImageUtil.loaderImage(item.getImageUrl(), helper.leftImg);
        int weight = PublicTool.getDeviceWidth();
//        if (index == 1) {
//            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
//                    weight / 2, weight / 6);
//            helper.leftImg.setLayoutParams(mLayoutParams);
//        } else {
//
//        }
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                weight/2 , weight /6);
        mLayoutParams.setMargins(0,10,0,10);
        helper.leftImg.setLayoutParams(mLayoutParams);
        Imageload.LoadImag(mContext, item.getImageUrl(), helper.leftImg);
        helper.rightLine.setVisibility(position % 2 == 1 ? View.VISIBLE : View.GONE);

    }

    @Override
    public ServerHolder getViewHolder(View convertView) {
        return new ServerHolder(convertView);
    }

    class ServerHolder extends OrdinaryViewHolder {
        @InjectView(R.id.img_left)
        ImageView leftImg;
        @InjectView(R.id.right_line)
        View rightLine;

        public ServerHolder(View view) {
            super(view);
        }
    }
}
