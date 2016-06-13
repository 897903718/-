package chanlytech.com.laborsupervision.config;

/**
 * Created by Lyy on 2015/7/7.
 * Module返回码
 */
public class ResultCode {
    public static final String EORROR_MSG="网络不给力";
    public static final int GET_HOME_LIST = 0x00;//获取首页数据
    public static final int GET_USER_INFO = 0x01;
    public static final int GET_CITY_INFO = 0x02;
    public static final int GET_SERVER_DATA = 0x03;
    public static final int GET_LEGAL_NEWS_LIST_DATA = 0x04;
    public static final int GET_SERVER_DETAIL_DATA = 0x05; //获取服务详情数据
    public static final int GET_GOODS_DETAIL_DATA = 0x06; //获取商品详情数据
    public static final int CHOSE_DIALOG = 0x08; //选择对话框回调
    public static final int POST_ORDER = 0x09; //提交订单
    public static final int GET_INFOR_DATA = 0x10;//获取资讯首页
    public static final int COMMENT_PROBLEM_RESULT=0x11;//提交资讯问题
    public static final int GET_CHAT_LIST_RESULT=0x12;//获取资讯聊天列表
    public static final int GET_SMS_CODE=0X13;//短信验证码
    public static final int REGISTER=0X14;//用户注册
    public static final int USER_LOGIN=0X15;//用户登录
    public static final int GET_USER_NEWS_INFO=0X16;//获取用户最新信息
    public static final int SUBMIT_ORDER=0X17;//提交订单
    public static final int CHECK_COUPON_CODE=0x18;//优惠码验证
    public static final int ORDER_LIST=0X19;//获取订单列表
    public static final int TAOCAN_LIST=0X20;//获取套餐列表
    public static final int SUBMIT_TAOCAN_ORDER=0x21;//提交套餐订单
    public static final int GET_Column_LIST=0x22;//获得栏目列表
    public static final int GET_POSTER_NEWS=0x23;//获得新闻和广告列表
    public static final int UPDATE_USER_ICON=0x24;//上传用户头像
    public static final int MODIFY_USER_INFO=0x25;//修改用户资料
    public static final int CHANGE_PWD=0x26;//修改密码
    public static final int GET_CITY_LIST=0x27;//获取城市列表
    public static final int CLEAN_CACHE=0x28;//清楚缓存
    public static final int SUBMIT_FEEBACK=0x29;//意见反馈
    public static final int UPDATE =0x30;//意见反馈
    public static final int EORROR_CODE =0x31;//错误返回
    public static final int START_IMG =0x32;//启动图
    public static final int GET_TYPE_LIST =0x33;//咨询列表
    public static final int BAI_DU_ID =0x34;//推送ID
    public static final int COMMENT_ZAN=0x35;//案列点赞
    public static final int COMMENT_SUB=0x36;//案列评论
    public static final int GET_COMMENT_LIST=0x37;//案列分析评论列表
    public static final int UPDATE_INFO=0x38;//版本升级
    public static final int SHARE_CODE=0x39;//分享信息
    public static final int UPDATE_USER_REQ=0x40;//用户升级资料提交
    public static final int keywordList=0x41;//关键词列表
    public static final int keywordGather=0x42;//关键词收集提交接口
    public static final int FtBook=0x43;//拉取法条书和章节列表
    public static final int LEGAL=0x44;//拉取法律条文
    public static final int WEIQUAN=0x45;//维权问题
    public static final int TIJIAO=0x46;//维权问题提交
    public static final int PRO_LIST=0x47;//维权进度列表
    public static final int GET_TASK_LIST=0x48;//维权任务列表
    public static final int TASK_DETAIL=0x49;//维权任务详情
    public static final int TASK_PRO=0x50;//维权任务处理
    public static final int JOIN_LAWYER=0x51;//律师加入







}
