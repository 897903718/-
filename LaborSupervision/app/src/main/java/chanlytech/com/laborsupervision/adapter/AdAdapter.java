package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arialyy.frame.util.show.L;


import java.util.List;

import chanlytech.com.laborsupervision.activity.CaseAnalysisActivity;
import chanlytech.com.laborsupervision.activity.MapActivity;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.entiy.AdsEntity;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.web.ServerWebActivity;
import chanlytech.com.laborsupervision.web.TestActivity;

/**
 * Created by Lyy on 2015/7/22.
 */
public class AdAdapter extends PagerAdapter {
    //法律咨询轮播适配器
    private Context mContext;
    private Intent mIntent;
    private List<AdsEntity> list;
    private List<View>views;
    public AdAdapter(Context context, List<AdsEntity> list, List<View> views){
        this.mContext=context;
        this.list=list;
        this.views=views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ((ViewPager) container).addView(views.get(position));
        if (views.get(position) != null&&list.size()>0) {
            // views.size=5 view(c' a b c a') list.size=3 list(a b c)
            if (position == 0) {// c'>c
                Imageload.LoadImag(mContext, list.get(list.size() - 1).getImageUrl(), (ImageView) views.get(position));
                views.get(position).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        topage(list.get(list.size()-1));
//                        mIntent=new Intent(mContext, MapActivity.class);
//                        mIntent.putExtra("url", list.get(list.size()-1).getUrl());
//                        mIntent.putExtra("title",list.get(list.size()-1).getTitle());
//                        mIntent.putExtra("objid",list.get(list.size()-1).getObjId());
//                        mIntent.putExtra("type",list.get(list.size()-1).getType());
//                        mContext.startActivity(mIntent);

                    }
                });
            } else if (position == (views.size() - 1)) {// a'>a
                Imageload.LoadImag(mContext,list.get(0).getImageUrl(),(ImageView) views.get(position));
                views.get(position).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        topage(list.get(0));
//                        mIntent=new Intent(mContext, MapActivity.class);
//                        mIntent.putExtra("url", list.get(0).getUrl());
//                        mIntent.putExtra("title",list.get(0).getTitle());
//                        mIntent.putExtra("objid",list.get(0).getObjId());
//                        mIntent.putExtra("type",list.get(0).getType());
//                        mContext.startActivity(mIntent);

                    }
                });
            } else {
                Imageload.LoadImag(mContext,list.get(position-1).getImageUrl(),(ImageView) views.get(position));

                views.get(position).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        topage(list.get(position-1));
//                        mIntent=new Intent(mContext, MapActivity.class);
//                        mIntent.putExtra("url", list.get(position-1).getUrl());
//                        mIntent.putExtra("title",list.get(position-1).getTitle());
//                        mIntent.putExtra("objid",list.get(position-1).getObjId());
//                        mIntent.putExtra("type",list.get(position-1).getType());
//                        mContext.startActivity(mIntent);
                    }
                });
            }

        }
        return views.get(position);
    }


    private void topage(AdsEntity adsEntity){
        if(!Constants.isFastClick()){
            if(adsEntity.getType()!=null){
                if(adsEntity.getType().equals("1")){//新闻
                    Intent mIntent=new Intent(mContext, WebActivity.class);
                    mIntent.putExtra("title",adsEntity.getTitle());
                    mIntent.putExtra("url",adsEntity.getUrl());
                    mIntent.putExtra("objid",adsEntity.getObjId());
                    mIntent.putExtra("type",1);
                    mContext.startActivity(mIntent);
                }else if(adsEntity.getType().equals("7")){//服务
                    WebEntity webEntity = new WebEntity();
                    webEntity.setTitle(adsEntity.getTitle());
                    webEntity.setUrl(adsEntity.getUrl());
                    mIntent = new Intent(mContext, ServerWebActivity.class);
                    mIntent.putExtra("webEntity", webEntity);
//                    mIntent.putExtra("url",adsEntity.getUrl());
                    mContext.startActivity(mIntent);
                }else if(adsEntity.getType().equals("10")){//外部链接
                    Intent mIntent=new Intent(mContext, WebActivity.class);
                    mIntent.putExtra("title",adsEntity.getTitle());
                    mIntent.putExtra("url",adsEntity.getUrl());
                    mIntent.putExtra("objid",adsEntity.getObjId());
                    mIntent.putExtra("type",10);
                    mContext.startActivity(mIntent);
                }else if(adsEntity.getType().equals("11")){//案列
                    Intent intent=new Intent(mContext, CaseAnalysisActivity.class);
                    intent.putExtra("title",adsEntity.getTitle());
                    intent.putExtra("url",adsEntity.getUrl());
                    intent.putExtra("id",adsEntity.getObjId());
                    mContext.startActivity(intent);
                }
            }
        }

    }
}
