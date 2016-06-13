package chanlytech.com.laborsupervision.http;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.AndroidUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.config.ServerConfig;

/**
 * Created by Lyy on 2015/8/31.
 */
public class ServerUtil {
    private static HttpUtil mHttpUtil;
    private static Context mContext;

    public static void newInstance(Context context) {
        new ServerUtil(context);
    }

    private ServerUtil(Context context) {
        mHttpUtil = new HttpUtil(context);
        mContext = context;
    }

    /**
     * 是否请求成功
     *
     * @param jsonObject
     * @return
     */
    public static boolean isRequestScuess(JSONObject jsonObject) {
        try {
            return jsonObject.getString("status").equals("1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 组合通用参数
     *
     * @param map
     * @return
     */
    public static Map<String, String> mixParams(Map<String, String> map) {
        Map<String, String> params = new HashMap<>();
        Set set = map.entrySet();
        for (Object aSet : set) {
            Map.Entry entry = (Map.Entry) aSet;
            params.put(entry.getKey() + "", entry.getValue() + "");
        }
        String randomCode = Encryption.getRandomCode();
        //添加通用参数
        params.put("timestamp", randomCode);
        params.put("sign", Encryption.getICityCode(randomCode));
        params.put("userId", AppManager.getUser().getUserId());
        params.put("version", AndroidUtils.getVersionName(mContext) + "");
        params.put("versionCode", AndroidUtils.getVersionCode(mContext) + "");
        params.put("device", "0");
        params.put("cityId", AppManager.getUser().getCityId());
        return params;
    }

    public static void requestData(String url, Map<String, String> params, HttpUtil.Response response) {
        mHttpUtil.post(ServerConfig.BASE_URL + url, mixParams(params), response);
    }

    public static void requestDataforCache(String url, Map<String, String> params, HttpUtil.Response response) {
        mHttpUtil.postFromCanche(ServerConfig.BASE_URL + url, mixParams(params), response);
    }

    /**
     * 获取首页数据
     */
    public static void getHomeListData(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.GET_HOME_LIST_DATA, params, response);
    }

    /**
     * 获取短信验证码
     */

    public static void getSmsCode(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.GET_SMS_CODE, params, response);
    }

    /**
     * 用户注册
     */
    public static void userRegistered(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.USER_REGISTER, params, response);
    }

    /**
     * 用户登录
     */

    public static void userLogin(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.USER_LOGIN, params, response);
    }


    /**
     * 新闻咨讯列表
     */
    public static void getPosterAndNews(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.GET_POSTER_NEWS, params, response);
    }

    /**
     * 修改用户信息
     */
    public static void modifyUserInfo(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.MODIFY_USER_INFO, params, response);
    }

    /**
     * 上传头像
     */
    public static void update(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.UPDATE_Avatar, params, response);
    }

    /**
     * 获取用户信息
     */
    public static void getUserInfo(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.GET_USER_INFO, params, response);
    }

    /**
     * 获取用户菜单
     */
    public static void getUserNewsInfo(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.GET_USER_NEWS_INFO, params, response);
    }

    /**
     * 获取分享信息
     */
    public static void getShareInfo(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.getShareData, params, response);
    }

    /**
     * 修改密码
     */
    public static void changPwd(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.CHANGE_PWD, params, response);
    }

    /**
     * 版本更新
     */
    public static void updateVersion(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.UPDATE, params, response);
    }

    /**
     * 获取栏目列表
     */

    public static void getCategoryList(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.GET_Category_LIST, params, response);
    }

    /**
     * 获取城市列表
     */
    public static void getCityList(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.GET_CITY_LIST, params, response);
    }

    /**
     * 获取启动图片
     */
    public static void getStartImg(Map<String, String> params, HttpUtil.Response response) {
        requestDataforCache(ServerConfig.START_IMG, params, response);
    }

    /**
     * 用户反馈
     */
    public static void submitFeedback(Map<String, String> params, HttpUtil.Response response) {
        requestData(ServerConfig.SUBMIT_FEEBACK, params, response);
    }

    /**
    * 获取评论列表
     * */
    public static void getCommentList(Map<String,String> params,HttpUtil.Response response){
        requestDataforCache(ServerConfig.getCommentList, params, response);
    }

    /**
     * 案列评论
     * */
    public  static void commentSub(Map<String, String> params, HttpUtil.Response response){
        requestData(ServerConfig.commentSub, params, response);
    }

    /**
     * 案列点赞
     * */
    public static void commentZan(Map<String, String> params, HttpUtil.Response response){
        requestData(ServerConfig.commentZan, params, response);
    }

    /**
     * 提交关键字
     * */
    public static void keywordGather(Map<String, String> params, HttpUtil.Response response){
        requestData(ServerConfig.keywordGather, params, response);
    }

    /**
     * 关键词列表
     * */
    public static void keywordList(Map<String, String> params, HttpUtil.Response response){
        requestDataforCache(ServerConfig.keywordList, params, response);
    }

    /**
     * 拉取法条书和章节列表
     * */
    public static void FtBook(Map<String, String> params, HttpUtil.Response response){
        requestDataforCache(ServerConfig.FtBook, params, response);
    }

    /**
     * 维权问题
     * */
    public static void weiQuan(Map<String, String> params, HttpUtil.Response response){
        requestDataforCache(ServerConfig.WeiQuan,params,response);
    }

    /**
     * 维权问题提交
     * */
    public static void tijiao(Map<String,String>params,HttpUtil.Response response){
        requestData(ServerConfig.TIJIAO, params, response);
    }

    /**
     * 获取维权信息
     * */
    public static void getProssList(Map<String,String>params,HttpUtil.Response response){
        requestDataforCache(ServerConfig.GET_PRO_LIST, params, response);
    }

    /**
     * 获取维权任务列表
     * */
    public static void getTaskList(Map<String,String>params,HttpUtil.Response response){
        requestDataforCache(ServerConfig.GET_TASK_LIST,params,response);
    }

    /**
     * 获取维权任务详情
     * */
    public static void getTaskDetail(Map<String,String>params,HttpUtil.Response response){
        requestDataforCache(ServerConfig.GET_TASK_DETAIL,params,response);
    }

    /**
     * 处理维权任务
     *
     * */
    public static void ProTask(Map<String,String>params,HttpUtil.Response response){
        requestData(ServerConfig.HANDLE_FLAG, params, response);
    }

    /**
     * 加入我们---律师
     * */
    public static void LawyerToJoin(Map<String,String>params,HttpUtil.Response response){
        requestData(ServerConfig.LAWYERS_TO_JOIN,params,response);
    }

    /**
     * 加入我们---其他三个
     * */
    public static void JoinUs(Map<String,String>params,HttpUtil.Response response){
        requestData(ServerConfig.LAWYERS_TO_JOIN,params,response);
    }
}
