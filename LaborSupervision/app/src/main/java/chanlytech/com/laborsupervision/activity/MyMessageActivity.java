package chanlytech.com.laborsupervision.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arialyy.frame.util.show.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.MessageAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.sqlite.MessageDao;
import chanlytech.com.laborsupervision.web.ServerWebActivity;

/**
 * 我的消息 2015-9-10
 */
public class MyMessageActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.message_list)
    ListView mListView;
    private MessageAdapter mMessageAdapter;
    private MessageEntity messageEntity;
    private MessageDao messageDao;
    private List<MessageEntity> messageEntities = new ArrayList<>();
    private Intent mIntent;

    @Override
    public int setContentView() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    private void initView() {
        tv_title.setText("我的消息");
        messageEntity = new MessageEntity();
        messageDao = new MessageDao(this);
        messageEntities = messageDao.selectData1(messageEntity);
        Collections.reverse(messageEntities);
        if (messageEntities.size() > 0) {
            mMessageAdapter = new MessageAdapter(this, messageEntities);
            mListView.setAdapter(mMessageAdapter);
        } else {
            T.showLong(this, "抱歉！您还没有消息");
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                messageDao.modifyData(messageEntities.get(position));//修改状态
                MessageEntity messageEntity = messageEntities.get(position);
                messageEntities.get(position).setImageUrl("1");
                mMessageAdapter.notifyDataSetChanged();
//               new  AlertDialog.Builder(MyMessageActivity.this).setMessage("是否删除这条消息").setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       messageDao.deleteData(messageEntities.get(position));
//                       messageEntities.remove(position);
//                       mMessageAdapter.notifyDataSetChanged();
//                   }
//               }).setPositiveButton("取消",null).show();
                if (messageEntity != null) {
                    if (messageEntity.getType() == 1 || messageEntity.getType() == 10) {//新闻
                        mIntent = new Intent(MyMessageActivity.this, WebActivity.class);
                        mIntent.putExtra("url", messageEntity.getUrl());
                        mIntent.putExtra("title", messageEntity.getTitle());
                        mIntent.putExtra("objid", messageEntity.getObjId());
                        mIntent.putExtra("type", messageEntity.getType());
                        startActivity(mIntent);
                    } else if (messageEntity.getType() == 6) {//活动
                    } else if (messageEntity.getType() == 7) {//服务
                        WebEntity webEntity = new WebEntity();
                        webEntity.setTitle(messageEntity.getTitle());
                        webEntity.setUrl(messageEntity.getUrl());
                        mIntent = new Intent(MyMessageActivity.this, ServerWebActivity.class);
                        mIntent.putExtra("webEntity", webEntity);
                        startActivity(mIntent);
                    } else if (messageEntity.getType() == 12) {//审核通知
                        mIntent = new Intent(MyMessageActivity.this, CertificationNoticeActivity.class);
                        mIntent.putExtra("objid", messageEntity.getObjId());
                        startActivity(mIntent);
                    }else if(messageEntity.getType() == 13){//维权任务通知
                        mIntent = new Intent(MyMessageActivity.this, RightsDetailsActivity.class);
                        mIntent.putExtra("id", messageEntity.getObjId());
                        startActivity(mIntent);
                    }
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MyMessageActivity.this).setMessage("是否删除这条消息").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        messageDao.deleteData(messageEntities.get(position));
                        messageEntities.remove(position);
                        mMessageAdapter.notifyDataSetChanged();
                    }
                }).setPositiveButton("取消", null).show();
                return true;
            }
        });
    }
}
