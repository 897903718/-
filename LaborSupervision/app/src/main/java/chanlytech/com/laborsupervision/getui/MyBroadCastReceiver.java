package chanlytech.com.laborsupervision.getui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.arialyy.frame.util.show.T;

import chanlytech.com.laborsupervision.activity.CertificationNoticeActivity;
import chanlytech.com.laborsupervision.activity.MainActivity;
import chanlytech.com.laborsupervision.activity.RightsDetailsActivity;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.entiy.PushEntity;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.sqlite.MessageDao;
import chanlytech.com.laborsupervision.web.ServerWebActivity;

/**
 * Created by Lyy on 2015/11/25.
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    private Intent mIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("chanlytech.com.laborsupervision.mybroadcastreceiver")){
         PushEntity pushEntity=intent.getParcelableExtra("pushentity");
         MessageEntity messageEntity=intent.getParcelableExtra("messageEntity");
            MessageDao messageDao=new MessageDao(context);
            messageDao.modifyData(messageEntity);
            ImageView imageView=new ImageView(context);
            MainActivity.showIco(false,imageView);
            PushDemoReceiver.payloadData.delete(0, PushDemoReceiver.payloadData.length());
            if(pushEntity!=null){
                if (pushEntity.getType() == 1||pushEntity.getType() == 10) {//新闻
                    mIntent = new Intent(context, WebActivity.class);
                    mIntent.putExtra("url", pushEntity.getUrl());
                    mIntent.putExtra("title", pushEntity.getAlert());
                    mIntent.putExtra("objid", pushEntity.getObjId());
                    mIntent.putExtra("type", pushEntity.getType());
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent);
                } else if (pushEntity.getType() == 6) {//活动
                } else if (pushEntity.getType() == 7) {//服务
                    WebEntity webEntity = new WebEntity();
                    webEntity.setTitle(pushEntity.getAlert());
                    webEntity.setUrl(pushEntity.getUrl());
                    mIntent = new Intent(context, ServerWebActivity.class);
                    mIntent.putExtra("webEntity", webEntity);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent);
                }else if(pushEntity.getType() == 12){//审核通知
                    mIntent = new Intent(context, CertificationNoticeActivity.class);
                    mIntent.putExtra("objid",pushEntity.getObjId());
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent);
                }else if(pushEntity.getType() == 13){//维权通知
                    mIntent = new Intent(context, RightsDetailsActivity.class);
                    mIntent.putExtra("id",pushEntity.getObjId());
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent);
                }


            }

        }

    }
}
