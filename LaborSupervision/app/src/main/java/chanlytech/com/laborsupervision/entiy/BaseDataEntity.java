package chanlytech.com.laborsupervision.entiy;

import java.util.List;

/**
 * Created by Lyy on 2015/7/10.
 */
public class BaseDataEntity {
    private boolean isBlock;//版本是否可用
    private boolean isFeedback;//是否有反馈记录
    private  int maxContent;//消息最大字数
    private String type ;//  咨询消息所属类型id
    private String category;//问题分类对象
    private String telList;//咨询电话
    private List<AdsEntity>banner;//广告实体
    private List<ServersEntity>apps;//服务实体
    private List<ServersEntity>apps2;//服务实体
    private List<NewsEntity> news;//新闻实体
    private String imageUrl;
    private String url;
    private String comid;//评论id
    private List<CommentEntity>commentlist;
    private int count;
    private int iszan;//1是 2否
    private int zan;
    private List<QuestionEntity> option;
    private List<NewsEntity>linkobj;
    public boolean isBlock() {
        return isBlock;
    }

    public BaseDataEntity setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
        return this;
    }

    public boolean isFeedback() {
        return isFeedback;
    }

    public BaseDataEntity setIsFeedback(boolean isFeedback) {
        this.isFeedback = isFeedback;
        return this;
    }

    public int getMaxContent() {
        return maxContent;
    }

    public BaseDataEntity setMaxContent(int maxContent) {
        this.maxContent = maxContent;
        return this;
    }

    public String getType() {
        return type;
    }

    public BaseDataEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getTelList() {
        return telList;
    }

    public BaseDataEntity setTelList(String telList) {
        this.telList = telList;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public BaseDataEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public List<AdsEntity> getBanner() {
        return banner;
    }

    public void setBanner(List<AdsEntity> banner) {
        this.banner = banner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ServersEntity> getApps() {
        return apps;
    }

    public void setApps(List<ServersEntity> apps) {
        this.apps = apps;
    }



    public List<ServersEntity> getApps2() {
        return apps2;
    }

    public void setApps2(List<ServersEntity> apps2) {
        this.apps2 = apps2;
    }

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public List<CommentEntity> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<CommentEntity> commentlist) {
        this.commentlist = commentlist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIszan() {
        return iszan;
    }

    public void setIszan(int iszan) {
        this.iszan = iszan;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<NewsEntity> getNews() {
        return news;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public List<QuestionEntity> getOption() {
        return option;
    }

    public void setOption(List<QuestionEntity> option) {
        this.option = option;
    }

    public List<NewsEntity> getLinkobj() {
        return linkobj;
    }

    public void setLinkobj(List<NewsEntity> linkobj) {
        this.linkobj = linkobj;
    }
}
