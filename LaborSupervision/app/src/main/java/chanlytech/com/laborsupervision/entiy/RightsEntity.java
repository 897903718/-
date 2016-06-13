package chanlytech.com.laborsupervision.entiy;

/**
 * Created by Lyy on 2016/1/28.
 */
public class RightsEntity {
    private String code;
    private String enterpriseName;
    private String address;
    private String phone;
    private String addtime;
    private String userphone;
    private String problemstr;
    private int flag;
    private String flagname;
    private String remark;
    private String title;
    private String id;
    private String time;
    private String buttext;//按钮文字
    private String admin;//指派人
    private String zhi;//执行人
    private int ishandle;// 0 不显示

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getProblemstr() {
        return problemstr;
    }

    public void setProblemstr(String problemstr) {
        this.problemstr = problemstr;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFlagname() {
        return flagname;
    }

    public void setFlagname(String flagname) {
        this.flagname = flagname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getButtext() {
        return buttext;
    }

    public void setButtext(String buttext) {
        this.buttext = buttext;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getZhi() {
        return zhi;
    }

    public void setZhi(String zhi) {
        this.zhi = zhi;
    }

    public int getIshandle() {
        return ishandle;
    }

    public void setIshandle(int ishandle) {
        this.ishandle = ishandle;
    }
}
