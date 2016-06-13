package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import chanlytech.com.laborsupervision.base.BaseEntity;


/**
 * Created by Lyy on 2015/7/6.
 */
public class UserEntity extends BaseEntity implements Parcelable {
//    /**
//     * 百度id
//     */
//    private String channel = "";
    /**
     * 用户ID
     */
    private String userId ;
    /**
     * 昵称
     */
    private String nickName ;
    /**
     * 头像图片
     */
    private String avatar ;
    /**
     * 渠道号
     */
    private String channelId = "";
    /**
     * 区域ID
     */
    private String cityId="3145";//3145高新区默认2269双流
    /**
     * 电话号码
     */
    private String telPhone ;
    /**
     * 性别 1:男，2:女，0:未知
     */
    private String sex;
    /**
     * 用户类型 0=个人用户,1=企业用户
     */
    private String userType="4";
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 未支付的数量
     * */
    private String UnpaidCount;

    /**
     * 待处理的数量
     * */
    private String PendingCount;
    /**
     * 已完成的数量
     * */
    private String CompletedCount;
    /**
    * 邮箱地址
    * */
    private String Mail;

    /**
    * 地址
    * */
    private String address="高新区";
    /**
    *用户积分
    * */
    private String integral;
    /**
     * 账户到期时间戳
     * */
    private long endtime;
    /**
     * 优惠码
     * */
    private String couponCode;
    /**
     *优惠码获得方式   0用户输入   1系统赠送
     * */
    private int couponType;
    /**
     * 系统赠送优惠码提示消息
     * */
    private String couponMsg;
    /**
     * 账号是否到期
     * */
    private String isexpire;
    public String getUserId() {
        return TextUtils.isEmpty(userId) ? "1" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCityId() {
        return TextUtils.isEmpty(cityId) ? "3145" : cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUnpaidCount() {
        return UnpaidCount;
    }

    public void setUnpaidCount(String unpaidCount) {
        UnpaidCount = unpaidCount;
    }

    public String getPendingCount() {
        return PendingCount;
    }

    public void setPendingCount(String pendingCount) {
        PendingCount = pendingCount;
    }

    public String getCompletedCount() {
        return CompletedCount;
    }

    public void setCompletedCount(String completedCount) {
        CompletedCount = completedCount;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address)?"高新区":address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getCouponMsg() {
        return couponMsg;
    }

    public void setCouponMsg(String couponMsg) {
        this.couponMsg = couponMsg;
    }

    public String getIsexpire() {
        return isexpire;
    }

    public void setIsexpire(String isexpire) {
        this.isexpire = isexpire;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.nickName);
        dest.writeString(this.avatar);
        dest.writeString(this.channelId);
        dest.writeString(this.cityId);
        dest.writeString(this.telPhone);
        dest.writeString(this.sex);
        dest.writeString(this.userType);
        dest.writeString(this.companyName);
        dest.writeString(this.CompletedCount);
        dest.writeString(this.UnpaidCount);
        dest.writeString(this.PendingCount);
        dest.writeString(this.address);
        dest.writeString(this.integral);
        dest.writeString(this.Mail);
        dest.writeString(this.couponCode);
        dest.writeString(this.couponMsg);
        dest.writeInt(this.couponType);
        dest.writeLong(this.endtime);
        dest.writeString(this.isexpire);
    }

    public UserEntity() {

    }

    protected UserEntity(Parcel in) {
        this.userId = in.readString();
        this.nickName = in.readString();
        this.avatar = in.readString();
        this.channelId = in.readString();
        this.cityId = in.readString();
        this.telPhone = in.readString();
        this.sex = in.readString();
        this.userType = in.readString();
        this.companyName = in.readString();
        this.CompletedCount=in.readString();
        this.UnpaidCount=in.readString();
        this.PendingCount=in.readString();
        this.address=in.readString();
        this.integral=in.readString();
        this.Mail=in.readString();
        this.couponCode=in.readString();
        this.couponMsg=in.readString();
        this.couponType=in.readInt();
        this.endtime=in.readLong();
        this.isexpire=in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
