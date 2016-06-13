package chanlytech.com.laborsupervision.config;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/7/6.
 * 服务器接口
 */
public class ServerConfig {
    //http://api.ldbzcd.com:8088/laodongjianguan/admin.php 下次升级的域名
    //测试地址http://ldsbcd.chanlytech.com/laodongjianguan/
    public static final String BASE_URL = "http://ldsbcd.chanlytech.com/laodongjianguan/index.php/";
    /**
     * 获取首页服务更新信息
     */
    public static final String UPDATE_SERVE = "Home/Home/updateServe";

    /**
     * 获取首页数据
     */
    public static final String GET_HOME_LIST_DATA = "Home/Home/getHomeList";

    /**
     * 获取活动数据
     */
    public static final String GET_ACTIVITY_LIST_DATA = "/Activity/Activity/getActivityList";

    /**
     * 获取业务数据
     */
    public static final String GET_BUSINESS_DATA = "Fw/Fwdetail/Fdetail";

    /**
     * 获取商品详情信息
     */
    public static final String GET_GOODS_DETAIL = "Fw/Fwdetail/goodsDetail";

    /**
     * 提交订单
     */
    public static final String POST_GOODS_ORDER = "Fw/Fwdetail/putOrder";

    /**
     * 咨询首页数据
     */
    public static final String GET_INFOR_DATA = "Fw/Flzx/getHomeList";

    /**
     * 提交咨询问题
     */
    public static final String COMMENT_PROBLEM = "Fw/Flzx/putContent";

    /**
     * 获取咨询问题列表
     */
    public static final String GET_CHAT_LIST = "Fw/Flzx/getFeedbackList";
    /**
     * 获取短信验证码
     **/
    public static final String GET_SMS_CODE = "User/User/sendCode";

    /**
     * 注册接口
     **/
    public static final String USER_REGISTER = "User/User/register";
    /**
     * 用户登陆接口
     */
    public static final String USER_LOGIN = "User/User/login";
    /**
     * 获取用户菜单（如是否有新的订单、活动等）
     */
    public static final String GET_USER_NEWS_INFO = "User/User/getUserData";

    /**
     * 提交订单
     */
    public static final String SUBMIT_ORDER = "Fw/Fwdetail/putOrder";

    /**
     * 验证优惠码
     */
    public static final String CHECK_COUPON_CODE = "User/User/CheckCouponCode";

    /**
     * 用户信息
     */
    public static final String GET_USER_INFO = "User/User/getUserInfo";

    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "Order/Order/getOrderList";
    /**
     * 套餐列表
     */
    public static final String TAO_CAN_LIST = "Taocan/Taocan/taocanList";
    /**
     * 提交套餐订单
     */
    public static final String SUBMIT_TAOCAN_ORDER = "Taocan/Taocan/putTaocanOrder";
    /**
     * 法律资讯----获取栏目列表
     */
    public static final String GET_Category_LIST = "News/News/getCategoryList";

    /**
     * 法律资讯----获取新闻和广告列表
     */
    public static final String GET_POSTER_NEWS = "News/News/getPosterAndNews";
    /**
     * 上传用户头像
     */
    public static final String UPDATE_Avatar = "User/User/uploadAvatar";
    /**
     * 用户升级资料提交
     * */
    public static final String UPDATE_gradeReq = "User/User/userUpgradeReq";
    /**
     * 修改用户信息
     */
    public static final String MODIFY_USER_INFO = "User/User/modifyUserInfo";
    /**
     * 修改密码
     */
    public static final String CHANGE_PWD = "User/User/modifyPassword";

    /**
     * 获取城市列表
     */
    public static final String GET_CITY_LIST = "City/City/getCityList";

    /**
     * 意见反馈
     */
    public static final String SUBMIT_FEEBACK = "Feedback/Feedback/submitFeedback";

    /**
     * 版本更新
     */
    public static final String UPDATE = "Version/Version/update";

    /**
     * 获取首页启动图
     */
    public static final String START_IMG = "Startimg/Startimg/getList";
    /**
     * 咨询类型列表
     */
    public static final String GET_TYPE_LIST = "Fw/Flzx/getTypeList";

    /**
     * 合同制作
     * */
    public static final String GET_CDETAIL="Contract/Contract/Cdetail";
    /**
     * 合同制作订单提交
     * */
    public static final String POST_ORDER="Contract/Contract/putContentOrder";
    /**
     * 合同模板列表
     * */
    public static final String ContractTplList="Contract/Contract/contractTplList";
    /**
     * 获取分享信息
     * */
    public static final String getShareData="Share/Share/getShareData";
    /**
     * 获取评论列表
     * */
    public static final String getCommentList="News/News/commentList";
    /**
     * 案列点赞
     *
     * */
    public static  final  String commentZan   ="News/News/commentZan";
    /**
     * 案例评论提交
     * */
    public static  final  String commentSub   ="News/News/commentSub";
    /**
     * 关键词列表
     * */
    public static  final  String keywordList   ="News/News/keywordList";
    /**
     * 关键词收集提交接口
     * */
    public static  final  String keywordGather   ="News/News/keywordGather";
    /**
     * 拉取法条书和章节列表
     * */
    public static  final  String FtBook   ="News/News/FtBook";
    /**
     * 维权
     * */
    public static final String WeiQuan="Weiquan/Weiquan/getWeiquanOption";
    /**
     * 维权信息提交
     * */
    public static final String TIJIAO="Weiquan/Weiquan/subWeiquanInfo";
    /**
     * 获取维权进度列表
     * */
    public static final String GET_PRO_LIST="Weiquan/Weiquan/weiquanInfoList";
    /**
     * 获取维权任务列表
     * */
    public static final String GET_TASK_LIST="Weiquan/Weiquan/weiquanTaskList";

    /**
     * 获取维权任务详情
     *
     * */
    public static final String GET_TASK_DETAIL="Weiquan/Weiquan/weiquanTaskDetail";
    /**
     * 维权任务处理状态
     * */
    public static final String HANDLE_FLAG="Weiquan/Weiquan/handleFlag";
    /**
     * 加入我们---律师
     * */
    public  static  final  String LAWYERS_TO_JOIN="Daohang/Daohang/joinSub";

}
