package chanlytech.com.laborsupervision.entiy;

import java.util.List;

/**
 * Created by Lyy on 2015/9/10.
 */
public class DataEntity {
    private List<NewsEntity> news;
    private String info;
    public List<NewsEntity> getNews() {
        return news;
    }

    public void setNews(List<NewsEntity> news) {
        this.news = news;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
