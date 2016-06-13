package chanlytech.com.laborsupervision.entiy;

import java.util.List;

/**
 * Created by Lyy on 2015/12/3.
 */
public class BookEntity {
    private String id;
    private String name;
    private List<ChapterEntity>zhangjie;
    private boolean isCheck;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterEntity> getZhangjie() {
        return zhangjie;
    }

    public void setZhangjie(List<ChapterEntity> zhangjie) {
        this.zhangjie = zhangjie;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
