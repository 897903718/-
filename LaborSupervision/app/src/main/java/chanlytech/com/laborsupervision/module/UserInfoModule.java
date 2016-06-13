package chanlytech.com.laborsupervision.module;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.show.L;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.IdentityAuthenActivity;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.http.Encryption;
import chanlytech.com.laborsupervision.http.LoadDatahandler;
import chanlytech.com.laborsupervision.http.MyAsyncHttpResponseHandler;
import chanlytech.com.laborsupervision.http.MyHttpClient;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.SharedPreferData;

/**
 * Created by Lyy on 2015/9/10.
 */
public class UserInfoModule extends BaseModule {
    private Context mContext;
    private PopupWindow mPopupWindow;
    public UserInfoModule(Context context) {
        super(context);
        this.mContext=context;
    }



    /**
     * 登录
     * */
    public void login(String telphone,String password){
        Map<String ,String>map=new HashMap<>();
        map.put("telphone",telphone);
        map.put("password", password);
        ServerUtil.userLogin(map,new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.USER_LOGIN,data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }
        });
    }



    //获取用户信息
    public void getUserInfo() {
        ServerUtil.getUserInfo(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("用户信息", data);
                callback(ResultCode.GET_USER_INFO, data);
            }
        });
    }

    /**
     * 上传头像
     */
    public void updateAvatar(File file) {
        RequestParams params = new RequestParams();
        String randomCode = Encryption.getRandomCode();
        //添加通用参数
        params.put("timestamp", randomCode);
        params.put("sign", Encryption.getICityCode(randomCode));
        params.put("userId", AppManager.getUser().getUserId());
        params.put("version", AndroidUtils.getVersionName(mContext) + "");
        params.put("versionCode", AndroidUtils.getVersionCode(mContext) + "");
        params.put("device", "0");
        params.put("cityId", AppManager.getUser().getCityId());
        try {
            params.put("avatar", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyHttpClient.getInstance(mContext).post(ServerConfig.BASE_URL+ServerConfig.UPDATE_Avatar, params, new MyAsyncHttpResponseHandler(new LoadDatahandler() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                L.i("上传头像", data);
                callback(ResultCode.UPDATE_USER_ICON, data);
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                L.i("上传头像失败", message);
            }
        }));


    }

    /**
     * 用户----修改用户信息
     */
    public void modifyUserInfo(String nickname,int userType,String mail,String address,String cityId) {
        Map<String,String>map=new HashMap<>();
        map.put("nickname",nickname);
        map.put("userType",userType+"");
        map.put("mail",mail);
        map.put("address",address);
        map.put("cityId",cityId);
        ServerUtil.modifyUserInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("修改用户信息返回", data);
                callback(ResultCode.MODIFY_USER_INFO, data);
            }
        });
    }


    //获取用户菜单

    public void getUserNewInfor(String lasttime) {
        Map<String, String> map = new HashMap<>();
        map.put("lasttime", lasttime);
        ServerUtil.getUserNewsInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("用户菜单栏目", data);
                callback(ResultCode.GET_USER_NEWS_INFO, data);
            }
        });
    }


    /**
     * 提示弹窗
     * */
    public void dialogPop(){
        View mView = View.inflate(mContext, R.layout.dialogpop_view, null);
        FrameLayout mFrameLayout = (FrameLayout) mView.findViewById(R.id.framelayout);
        Button button= (Button) mView.findViewById(R.id.btn_ok);
        Button button_canle= (Button) mView.findViewById(R.id.btn_canle);
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(mContext, IdentityAuthenActivity.class);
                mContext.startActivity(intent);
            }
        });

        button_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }
    /**
     * 绑定推送id
     *
     *
     * */
    public void bingCid(){
        Map<String,String>map=new HashMap<>();
        map.put("baiduId", SharedPreferData.readString(mContext, "CID").toString());
        ServerUtil.modifyUserInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                com.chanlytech.unicorn.imageloader.utils.L.i("推送id绑定结果", data);
            }
        });
    }
}
