package chanlytech.com.laborsupervision.config;

/**
 * Created by Lyy on 2015/7/6.
 * 基础配置信息
 */
public class Constance {

    /**
     * 配置文件key
     */
    public static class SHARED_PRE {
        public static final String SHARED_NAME = "EDIANGFA_CONFIG";
        public static final String USER = "USER";
        public static final String LOGIN = "LOGIN";
        public static final String CITY = "CITY";
        public static final String LAST_TIIME="LAST_TIIME";
    }

    /**
     * 默认配置
     */
    public static class DEFAULT{
        public static final int DEFAULT_CITY_CODE = 3145;//默认配置高新区
    }

    /**
     * 其它数据
     */
    public static class OTHER{
        public static final String PARCELABLE = "PARCELABLE";
        public static final String INT = "INT";
        public static final String STRING = "STRING";
    }

    /**
     * Type
     */
    public static class TYPE{
        public static final int SYSTEM_MSG = 0x00;
        public static final int NEWS = 0x01;
        public static final int ACTIVITY = 0x06;
        public static final int SERVER = 0x07;
        public static final int OUTSIDE_LINE = 0x10;
        public static final int LEGAL_INFO = 0x11;
        public static final int LEGAL_WORD = 0x12;
    }

    /**
     * 原生服务选择
     */
    public static class SERVER_TYPE{
        public static final int COMPANY_CREATE = 0x01;
        public static final int COMPANY_USAGR = 0x02;
        public static final int INTELLECTUAL_PROPERTY_RIGHTS = 0x03;
        public static final int CONTRACT_PROPERTY = 0x04;
        public static final int MARRIAGE_AND_FAMILY = 0x05;
        public static final int CIVIL_COMPENSATION = 0x06;
        public static final int NOTARIZATION_SERVICE = 0x07;
        public static final int HETONG_ZHIZUO=0x09;
    }


    public static class DATE_FORMAT {
        public static final String ENCRYPTION_RANDOM = "yyyyMMDDHHmmss";
        public static final String Y_M_D_H_M_S_ = "yyyy-MM-DD HH-mm-ss";
    }

}
