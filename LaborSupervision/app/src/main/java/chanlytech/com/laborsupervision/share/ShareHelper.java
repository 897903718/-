package chanlytech.com.laborsupervision.share;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.util.AspLog;
import chanlytech.com.laborsupervision.util.MyLog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Lyy on 2015/7/29.
 * 分享工具类
 */
public class ShareHelper implements Handler.Callback {
    private Context mContext;
    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    private PopupWindow mPopupWindow;
    private static final String share_image_url = "http://e.hiphotos.baidu.com/image/w%3D310/sign=8daa74acd743ad4ba62e40c1b2035a89/8601a18b87d6277fcab6bb312e381f30e924fc17.jpg?qq-pf-to=pcqq.c2c";
    private boolean state;

    public ShareHelper(Context context) {
        this.mContext = context;
        ShareSDK.initSDK(context);//初始化sharesdk

    }

    //QQ空间
    public void ShareQzone(String title, String url, String content, String imgurl) {
        QZone.ShareParams params = new QZone.ShareParams();
        Platform Qzone = ShareSDK.getPlatform(mContext, QZone.NAME);
        params.setTitle(title);
        params.setText(content);
        params.setTitleUrl(url);
        params.setImageUrl(imgurl);//"http://pt.pngmb.com/logo.png"
        Qzone.setPlatformActionListener(platformActionListener);
        Qzone.share(params);
    }

    //微信好友
    public void ShareWechat(String url, String title, String content, String imgurl) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("标题:\n");
//        sb.append(title);
//        sb.append("分享链接:\n");
//        sb.append(url);
//        sb.append("描述:\n");
//        sb.append(content);
//        sb.append("图片路径:\n");
//        sb.append(imgurl);
//        MyLog.i("微信好友分享的参数", sb.toString());

        Wechat.ShareParams wechat = new Wechat.ShareParams();
        wechat.setTitle(title);
        if (content != null) {
            wechat.setText(content);
        } else {
            wechat.setText(title);
        }
        if (imgurl != null) {
            wechat.setImageUrl(imgurl);
        } else {
            wechat.setImageUrl(share_image_url);
        }
        wechat.setUrl(url);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(mContext, Wechat.NAME);
        weixin.setPlatformActionListener(platformActionListener);
        weixin.share(wechat);
    }

    //微信朋友圈
    public void ShareWeFrend(String url, String title, String content, String imgurl) {
        state = true;
//        StringBuffer sb = new StringBuffer();
//        sb.append("标题:\n");
//        sb.append(title);
//        sb.append("分享链接:\n");
//        sb.append(url);
//        sb.append("描述:\n");
//        sb.append(content);
//        sb.append("图片路径:\n");
//        sb.append(imgurl);
//        MyLog.i("微信朋友圈分享的参数", sb.toString());
        WechatMoments.ShareParams wechatMoments = new WechatMoments.ShareParams();
        wechatMoments.setTitle(title);

        if (content != null) {
            wechatMoments.setText(content);
        } else {
            wechatMoments.setText(title);
        }
        wechatMoments.setUrl(url);
        if (imgurl != null) {
            wechatMoments.setImageUrl(imgurl);
        } else {
            wechatMoments.setImageUrl(share_image_url);
        }
        wechatMoments.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
        weixin.setPlatformActionListener(platformActionListener);
        weixin.share(wechatMoments);
    }

    //QQ好友
    public void ShareQQ(String title, String url, String content, String imgurl) {
        QZone.ShareParams params = new QZone.ShareParams();
        Platform qq = ShareSDK.getPlatform(mContext, QQ.NAME);
        params.setTitle(title);
        if (content != null) {
            params.setText(content);
        } else {
            params.setText(title);
        }
        params.setTitleUrl(url);
        if (imgurl != null) {
            params.setImageUrl(imgurl);
        } else {
            params.setImageUrl(share_image_url);
        }
        qq.setPlatformActionListener(platformActionListener);
        qq.share(params);
    }

    PlatformActionListener platformActionListener = new PlatformActionListener() {

        @Override
        public void onError(Platform arg0, int arg1, Throwable arg2) {
            //错误监听,handle the error msg
            Message msg = new Message();
            msg.what = MSG_ACTION_CCALLBACK;
            msg.arg1 = 2;
            msg.arg2 = arg1;
            msg.obj = arg2;
//            MyLog.e("分享错误信息", arg2 + "");
            UIHandler.sendMessage(msg, ShareHelper.this);
        }

        @Override
        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
            Message msg = new Message();
            msg.what = MSG_ACTION_CCALLBACK;
            msg.arg1 = 1;
            msg.arg2 = arg1;
            msg.obj = arg0;
            UIHandler.sendMessage(msg, ShareHelper.this);
        }

        @Override
        public void onCancel(Platform arg0, int arg1) {
            Message msg = new Message();
            msg.what = MSG_ACTION_CCALLBACK;
            msg.arg1 = 3;
            msg.arg2 = arg1;
            msg.obj = arg0;
            UIHandler.sendMessage(msg, ShareHelper.this);
        }
    };

    @Override
    public boolean handleMessage(Message arg0) {
        switch (arg0.what) {
            case MSG_TOAST: {
                String text = String.valueOf(arg0.obj);
                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_ACTION_CCALLBACK: {
                switch (arg0.arg1) {
                    case 1: { // 成功提示, successful notification
                        if (state) {
                            Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                            state = false;
                        }

                    }
                    break;
                    case 2: { // 失败提示, fail notification
                        String expName = arg0.obj.getClass().getSimpleName();
                        L.i("分享失败", expName);
//                        MyLog.i("分享失败", expName);
                        T.showLong(mContext, "分享失败" + expName);
                        if ("WechatClientNotExistException".equals(expName)
                                || "WechatTimelineNotSupportedException".equals(expName)) {
                        } else if ("GooglePlusClientNotExistException".equals(expName)) {
                        } else if ("QQClientNotExistException".equals(expName)) {
                        } else {
                        }
                    }
                    break;
                    case 3: { // 取消提示, cancel notification
                        T.showLong(mContext, "取消分享");
                    }
                    break;
                }
            }
            break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) arg0.obj;
                if (nm != null) {
                    nm.cancel(arg0.arg1);
                }
            }
            break;
        }
        return false;
    }


    public void showSharePop(final String title, final String url, final String content, final String imgurl) {
        View view = View.inflate(mContext, R.layout.share_item, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        FrameLayout mFrameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
        TextView mTextView_weixin = (TextView) view.findViewById(R.id.tv_weixin);
        TextView mTextView_qq = (TextView) view.findViewById(R.id.tv_qq);
        TextView mTextView_qzone = (TextView) view.findViewById(R.id.tv_qzone);
        TextView mTextView_wchat = (TextView) view.findViewById(R.id.tv_wchat);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mTextView_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareWechat(url, title, content, imgurl);
                mPopupWindow.dismiss();
            }
        });
        mTextView_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareQQ(title, url, content, imgurl);
                mPopupWindow.dismiss();
            }
        });
        mTextView_qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareQzone(title, url, content, imgurl);
                mPopupWindow.dismiss();
            }
        });
        mTextView_wchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareWeFrend(url, title, content, imgurl);
                mPopupWindow.dismiss();
            }
        });
    }
}
