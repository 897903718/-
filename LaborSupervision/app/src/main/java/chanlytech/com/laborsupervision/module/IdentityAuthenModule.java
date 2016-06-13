package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.show.L;
import com.loopj.android.http.RequestParams;

import java.io.File;

import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.http.Encryption;
import chanlytech.com.laborsupervision.http.LoadDatahandler;
import chanlytech.com.laborsupervision.http.MyAsyncHttpResponseHandler;
import chanlytech.com.laborsupervision.http.MyHttpClient;

/**
 * Created by Lyy on 2015/11/24.
 */
public class IdentityAuthenModule extends BaseModule {
    private Context mContext;
    public IdentityAuthenModule(Context context) {
        super(context);
        this.mContext=context;
    }

    /**
     * 用户升级资料提交
     * params realname 姓名
     * params idcard 身份证
     * params enterpriseName 企业名
     * params enterpriseCode 企业编码
     * params organizationCode 组织机构代码
     * params img 组织机构代码证件图
     * */
    public void userUpgradeReq(String realname,String idcard,String enterpriseName,String gangwei, final File file){
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
        params.put("realname",realname);
        params.put("idcard",idcard);
        params.put("enterpriseName",enterpriseName);
        params.put("gangwei",gangwei);
        try {
            params.put("img", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyHttpClient.getInstance(mContext).post(ServerConfig.BASE_URL+ServerConfig.UPDATE_gradeReq, params, new MyAsyncHttpResponseHandler(new LoadDatahandler() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                L.i("用户资料升级", data);
                L.i("图片文件大小",file.length()+"");
                callback(ResultCode.UPDATE_USER_REQ, data);
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                L.i("用户资料升级失败", message);
            }
        }));

    }
}
