package chanlytech.com.laborsupervision.entiy;

/**
 * Created by Lyy on 2015/9/15.
 * 评论实体
 */
public class CommentEntity {
    private String id;
    private String uname;
    private String headimg;
    private int zan;
    private String content;
    private String time;
    private int iszan;//1点过赞，0没点过赞
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIszan() {
        return iszan;
    }

    public void setIszan(int iszan) {
        this.iszan = iszan;
    }
}
