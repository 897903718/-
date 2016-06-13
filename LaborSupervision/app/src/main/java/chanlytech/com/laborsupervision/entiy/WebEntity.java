package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyy on 2015/9/11.
 */
public class WebEntity implements Parcelable {
    private String url;
    private String title;
    private String id;
    public WebEntity(){

    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    protected WebEntity(Parcel in) {
        url = in.readString();
        title=in.readString();
    }

    public static final Creator<WebEntity> CREATOR = new Creator<WebEntity>() {
        @Override
        public WebEntity createFromParcel(Parcel in) {
            return new WebEntity(in);
        }

        @Override
        public WebEntity[] newArray(int size) {
            return new WebEntity[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(id);
    }
}
