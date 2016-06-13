package chanlytech.com.laborsupervision.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.show.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.LoginActivity;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorCode;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.CommentEntity;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.Imageload;

/**
 * Created by Lyy on 2015/9/14.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentEntity> mCommentEntities;
    private boolean state;

    public CommentAdapter(Context context, List<CommentEntity> commentEntities) {
        this.mContext = context;
        this.mCommentEntities = commentEntities;
    }

    @Override
    public int getCount() {
        return mCommentEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final CommentEntity commentEntity = mCommentEntities.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.comment_item, null);
            viewHolder.mImageView_icon = (ImageView) convertView.findViewById(R.id.iv_user);
            viewHolder.mImageView_praise = (ImageView) convertView.findViewById(R.id.iv_praise);//没有选中
            viewHolder.mImageView_praise_s= (ImageView) convertView.findViewById(R.id.iv_praise_s);//选中
            viewHolder.relativeLayout= (RelativeLayout) convertView.findViewById(R.id.rl_praise);
            viewHolder.mTextView_name = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mTextView_number = (TextView) convertView.findViewById(R.id.tv_number);
            viewHolder.mTextView_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.mTextView_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mTextView_name.setText(commentEntity.getUname());
            viewHolder.mTextView_number.setText(commentEntity.getZan() + "");
            viewHolder.mTextView_content.setText(commentEntity.getContent());
            viewHolder.mTextView_time.setText(commentEntity.getTime());
            if (commentEntity.getIszan() == 0) {
                viewHolder.mImageView_praise_s.setVisibility(View.GONE);
                viewHolder.mImageView_praise.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mImageView_praise_s.setVisibility(View.VISIBLE);
                viewHolder.mImageView_praise.setVisibility(View.GONE);
            }
            Imageload.loadImageRound(mContext, 100, commentEntity.getHeadimg(), viewHolder.mImageView_icon);
            final ImageView imageView = viewHolder.mImageView_praise;
            final ImageView imageView_s = viewHolder.mImageView_praise_s;
            final TextView mTextView_number = viewHolder.mTextView_number;
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点赞
                    if(AppManager.isLogin()){
                        Map<String, String> map = new HashMap<>();
                        map.put("id", commentEntity.getId());
                        ServerUtil.commentZan(map, new HttpUtil.Response() {
                            @Override
                            public void onResponse(String data) {
                                super.onResponse(data);
                                BaseEntity baseEntity = JSON.parseObject(data, BaseEntity.class);
                                if (baseEntity.getStatus() == 1) {
                                    BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                                    if (baseDataEntity.getIszan() == 1) {
                                        imageView.setVisibility(View.GONE);
                                        imageView_s.setVisibility(View.VISIBLE);
                                        mTextView_number.setText(baseDataEntity.getZan() + "");
                                    } else {
                                        imageView.setVisibility(View.VISIBLE);
                                        imageView_s.setVisibility(View.GONE);
                                        mTextView_number.setText(baseDataEntity.getZan() + "");

                                    }
                                } else {
                                    ErrorToast.showError(mContext, baseEntity.getErrorCode());
                                }
                            }

                            @Override
                            public void onError(Object error) {
                                super.onError(error);
                                T.showLong(mContext, ResultCode.EORROR_MSG);
                            }
                        });
                    }else {
                        Intent intent=new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            });
            convertView.setTag(viewHolder);
        } else {
            ViewHolder mHolder = (ViewHolder) convertView.getTag();
            mHolder.mTextView_name.setText(commentEntity.getUname());
            mHolder.mTextView_number.setText(commentEntity.getZan() + "");
            mHolder.mTextView_content.setText(commentEntity.getContent());
            mHolder.mTextView_time.setText(commentEntity.getTime());
            if (commentEntity.getIszan() == 0) {
                mHolder.mImageView_praise_s.setVisibility(View.GONE);
                mHolder.mImageView_praise.setVisibility(View.VISIBLE);
            } else {
                mHolder.mImageView_praise_s.setVisibility(View.VISIBLE);
                mHolder.mImageView_praise.setVisibility(View.GONE);
            }
            Imageload.loadImageRound(mContext, 100, commentEntity.getHeadimg(), mHolder.mImageView_icon);
            final ImageView imageView_1 = mHolder.mImageView_praise;
            final ImageView imageView_1S = mHolder.mImageView_praise_s;
            final TextView mTextView_number = mHolder.mTextView_number;
            mHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点赞
                    if(AppManager.isLogin()){
                        Map<String, String> map = new HashMap<>();
                        map.put("id", commentEntity.getId());
                        ServerUtil.commentZan(map, new HttpUtil.Response() {
                            @Override
                            public void onResponse(String data) {
                                super.onResponse(data);
                                BaseEntity baseEntity = JSON.parseObject(data, BaseEntity.class);
                                if (baseEntity.getStatus() == 1) {
                                    BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                                    if (baseDataEntity.getIszan() == 1) {//点过赞
                                        imageView_1.setVisibility(View.GONE);
                                        imageView_1S.setVisibility(View.VISIBLE);

                                        mTextView_number.setText(baseDataEntity.getZan() + "");
                                    } else {//没有点过赞
                                        imageView_1.setVisibility(View.VISIBLE);
                                        imageView_1S.setVisibility(View.GONE);
                                        mTextView_number.setText(baseDataEntity.getZan() + "");
                                    }
                                } else {
                                    ErrorToast.showError(mContext, baseEntity.getErrorCode());
                                }
                            }

                            @Override
                            public void onError(Object error) {
                                super.onError(error);
                                T.showLong(mContext, ResultCode.EORROR_MSG);
                            }
                        });
                    }else {
                        Intent intent=new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            });
        }
//        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.comment_item, null);
//            ImageView mImageView_icon = (ImageView) convertView.findViewById(R.id.iv_user);
//            final ImageView mImageView_praise = (ImageView) convertView.findViewById(R.id.iv_praise);
//            TextView mTextView_name = (TextView) convertView.findViewById(R.id.tv_username);
//            final TextView mTextView_number = (TextView) convertView.findViewById(R.id.tv_number);
//            TextView mTextView_content = (TextView) convertView.findViewById(R.id.tv_content);
//            mTextView_name.setText(commentEntity.getUname());
//            mTextView_number.setText(commentEntity.getZan() + "");
//            mTextView_content.setText(commentEntity.getContent());
//            Imageload.loadImageRound(mContext, 100, commentEntity.getHeadimg(), mImageView_icon);
//            if (commentEntity.getIszan() == 0) {
//                mImageView_praise.setImageResource(R.mipmap.ic_praise);
//            } else {
//                mImageView_praise.setImageResource(R.mipmap.ic_praise_s);
//            }
//            mImageView_praise.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("id", commentEntity.getId());
//                    ServerUtil.commentZan(map, new HttpUtil.Response() {
//                        @Override
//                        public void onResponse(String data) {
//                            super.onResponse(data);
//                            BaseEntity baseEntity = JSON.parseObject(data, BaseEntity.class);
//                            if (baseEntity.getStatus() == 1) {
//                                BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
//                                if (baseDataEntity.getIszan() == 1) {//点过赞
//                                    mImageView_praise.setBackgroundResource(R.mipmap.ic_praise_s);
//                                    mTextView_number.setText(baseDataEntity.getZan() + "");
//                                } else {//没有点过赞
//                                    mImageView_praise.setBackgroundResource(R.mipmap.ic_praise);
//                                    mTextView_number.setText(baseDataEntity.getZan() + "");
////
//                                }
//                            } else {
//                                ErrorToast.showError(mContext, baseEntity.getErrorCode());
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(Object error) {
//                            super.onError(error);
//                            T.showLong(mContext, ResultCode.EORROR_MSG);
//                        }
//                    });
//                }
//            });
//
//        }
        return convertView;
    }


    public class ViewHolder {
        public ImageView mImageView_icon;
        public ImageView mImageView_praise;
        public ImageView mImageView_praise_s;
        public TextView mTextView_name;
        public TextView mTextView_number;
        public TextView mTextView_content;
        public TextView mTextView_time;
        public RelativeLayout relativeLayout;
    }


}
