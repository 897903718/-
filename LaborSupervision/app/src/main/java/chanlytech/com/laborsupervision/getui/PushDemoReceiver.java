package chanlytech.com.laborsupervision.getui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

import java.text.SimpleDateFormat;
import java.util.Date;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.MainActivity;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.config.Constance;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.entiy.PushEntity;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.sqlite.MessageDao;
import chanlytech.com.laborsupervision.util.SharedPreferData;
import chanlytech.com.laborsupervision.web.ServerWebActivity;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    private Intent mIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
                if (payload != null) {
                    String data = new String(payload);
                    Log.d("GetuiSdkDemo", "receiver payload : " + data);

                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
//                    T.showLong(context, "透传消息" + data);
                    //接收透传消息
                    PushEntity pushEntity = JSON.parseObject(data, PushEntity.class);
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                    Date data1=new Date(System.currentTimeMillis());
                    String time = dateFormat.format(data1);
                    MessageDao messageDao = new MessageDao(context);
                    MessageEntity messageEntity = new MessageEntity();
                    messageEntity.setObjId(pushEntity.getObjId());
                    messageEntity.setTitle(pushEntity.getAlert());
                    messageEntity.setType(pushEntity.getType());
                    messageEntity.setDescription(pushEntity.getDescription());
                    messageEntity.setTime(time);
                    messageEntity.setImageUrl("0");
                    messageEntity.setUrl(pushEntity.getUrl());
                    messageDao.insertData1(messageEntity);//插入数据库
                    ImageView imageView=new ImageView(context);
                    MainActivity.showIco(true,imageView);
                    createNotifi(context,pushEntity,messageEntity);
                    payloadData.append(data);
                    payloadData.append("\n");


//                    if (GetuiSdkDemoActivity.tLogView != null) {
//                        GetuiSdkDemoActivity.tLogView.append(data + "\n");
//                    }
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                SharedPreferData.writeStringdata(context, "CID", cid);
//                Tag tag=new Tag();
//                tag.setName("jsdfjf");
//                Tag [] tags=new Tag[]{};
//                tags[0]=tag;
//                PushManager.getInstance().setTag(context,tags);
//                if (GetuiSdkDemoActivity.tView != null) {
//                    GetuiSdkDemoActivity.tView.setText(cid);
//                }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }


    // 发送通知
    @SuppressWarnings("deprecation")
    private void send(Context context, PushEntity pushEntity) {
        // 1 得到通知管理器
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 2构建通知
        Notification notification = new Notification(R.mipmap.logo_72,
                pushEntity.getTitle(),
                System.currentTimeMillis());
        // 3设置通知的点击事件
        if (pushEntity.getType() == 1||pushEntity.getType() == 10) {//新闻
            mIntent = new Intent(context, WebActivity.class);
            mIntent.putExtra("url", pushEntity.getUrl());
            mIntent.putExtra("title", pushEntity.getAlert());
            mIntent.putExtra("objid", pushEntity.getObjId());
            mIntent.putExtra("type", pushEntity.getType());
        } else if (pushEntity.getType() == 6) {//活动
        } else if (pushEntity.getType() == 7) {//服务
            WebEntity webEntity = new WebEntity();
            webEntity.setTitle(pushEntity.getAlert());
            webEntity.setUrl(pushEntity.getUrl());
            mIntent = new Intent(context, ServerWebActivity.class);
            mIntent.putExtra("webEntity", webEntity);
        }

        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 100,
                mIntent, 0);
        notification.setLatestEventInfo(context, pushEntity.getTitle(), pushEntity.getAlert(), contentIntent);
        notification.defaults = Notification.DEFAULT_SOUND;//声音默认
        notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
        // 4发送通知
        nm.notify(100, notification);
    }

    @SuppressWarnings("deprecation")
    private void Notifi(Context context, PushEntity pushEntity) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 2构建通知
        Notification notification = new Notification(R.mipmap.logo_72,
                pushEntity.getTitle(),
                System.currentTimeMillis());
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context, 100,
                intent, 0);
        notification.setLatestEventInfo(context, pushEntity.getTitle(), pushEntity.getAlert(), contentIntent);
        notification.defaults = Notification.DEFAULT_SOUND;//功能：向通知添加声音、闪灯和振动效果的最简单、使用默认（defaults）属性，可以组合多个属性（和方法1中提示效果一样的） 声音默认
        notification.flags = Notification.FLAG_AUTO_CANCEL;// 提醒标志 点击通知之后自动消失
        notification.iconLevel = Notification.PRIORITY_HIGH;
        // 4发送通知
        nm.notify(100, notification);
    }

    //创建自定义通知栏
    private void createNotifi(Context context,PushEntity pushEntity,MessageEntity messageEntity) {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        Date data1=new Date(System.currentTimeMillis());
        String time = dateFormat.format(data1);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.logo_72, pushEntity.getDescription(), timeMillis);
        notification.defaults = Notification.DEFAULT_SOUND;//声音默认
        notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        contentView.setTextViewText(R.id.tv_title,pushEntity.getTitle());
        contentView.setTextViewText(R.id.tv_des, pushEntity.getDescription());
        contentView.setTextViewText(R.id.tv_time,time);
//        contentView.setTextColor(R.id.tv_title,0xA5A5A5);
//        contentView.setTextColor(R.id.tv_des,0xA5A5A5);
//        contentView.setTextColor(R.id.tv_time,0xA5A5A5);
        notification.contentView = contentView;
//        Intent notificationIntent = new Intent(getActivity(), IdentityAuthenActivity.class);
//        notificationIntent.setAction("chanlytech.com.laborsupervision.mybroadcastreceiver");
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent contentItent = PendingIntent.getActivity(getActivity(), 0,
//        notificationIntent, 0);
        Intent notificationIntent=new Intent();
        notificationIntent.setAction("chanlytech.com.laborsupervision.mybroadcastreceiver");
        notificationIntent.putExtra("pushentity", pushEntity);
        notificationIntent.putExtra("messageEntity",messageEntity);
        PendingIntent contentItent=PendingIntent.getBroadcast(context,100,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        contentView.setOnClickPendingIntent(R.id.notifi_all,contentItent);
        notification.contentIntent = contentItent;
        notificationManager.notify(100, notification);

    }

}
