package chanlytech.com.laborsupervision.entiy;

/**
 * Created by Lyy on 2015/7/15.
 *
 */
public class MenueEntity {
    private int type;//1我的订单   2定制套餐    3咨询管理   4我的积分    5我的活动      6个人信息
    private int isUpdate;// 0 不显示红点 1显示
    private String name;//菜单名称
    private String url;
    private String img;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
