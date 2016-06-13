package chanlytech.com.laborsupervision.entiy;

/**
 * Created by Lyy on 2016/1/28.
 */
public class QuestionEntity {
    private String id;
    private String title;
    private int isCheck=1;//是否选中
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }
}
