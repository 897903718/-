package chanlytech.com.laborsupervision.base;

import android.content.Context;

import com.arialyy.frame.util.SharePreUtil;

import chanlytech.com.laborsupervision.config.Constance;
import chanlytech.com.laborsupervision.entiy.CityEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;

/**
 * Created by Lyy on 2015/7/6.
 */
public class AppManager {
    private static Context mContext;

    public static void newInstance(Context context) {
        new AppManager(context);
    }

    private AppManager(Context context) {
        mContext = context;
    }

    /**
     * 从配置文件获取用户信息
     */
    public static UserEntity getUser() {
        return SharePreUtil.getObject(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.USER, UserEntity.class);
    }

    /**
     * 存储用户信息
     */
    public static void saveUser(UserEntity userEntity) {
        SharePreUtil.putObject(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.USER, UserEntity.class, userEntity);
    }

    /**
     * 获取登录状态
     */
    public static boolean isLogin() {
        return SharePreUtil.getBoolean(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.LOGIN);
    }

    /**
     * 保存登录状态
     */
    public static void setLoginState(boolean loginState) {
        SharePreUtil.putBoolean(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.LOGIN, loginState);
    }

    /**
     * 获取城市信息
     */
    public static CityEntity getCity() {
        return SharePreUtil.getObject(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.CITY, CityEntity.class);
    }

    /**
     * 保存城市信息
     */
    public static void saveCity(CityEntity cityEntity) {
        SharePreUtil.putObject(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.CITY, CityEntity.class, cityEntity);
    }

    /**
     * 保存上次请求接口时间
     */

    public static void saveLastTime(String time) {
        SharePreUtil.putString(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.LAST_TIIME, time);
    }
    /**
    * 获取上次请求服务器时间
    * */
    public static String getLastTime(){
        return    SharePreUtil.getString(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.LAST_TIIME);
    }
    /**
    * 存储String型的用户数据
    * */

    public static void saveuser(String user){
        SharePreUtil.putString(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.USER, user);
    }

    public static String getuser(){
        return    SharePreUtil.getString(Constance.SHARED_PRE.SHARED_NAME, mContext, Constance.SHARED_PRE.USER);
    }
}
