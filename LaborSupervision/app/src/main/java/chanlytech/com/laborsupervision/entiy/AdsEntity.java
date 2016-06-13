package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 广告实体类
 *
 * @author huanjun
 */
public class AdsEntity implements Parcelable {
    private String id;
    private String type;
    private String imageUrl;
    private String objId;
    private String title;
    private String url;
    private String description;

    public AdsEntity(String imageUrl){
        this.imageUrl=imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdsEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.imageUrl);
        dest.writeString(this.objId);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.description);
    }

    private AdsEntity(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.imageUrl = in.readString();
        this.objId = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.description = in.readString();
    }

    public static final Creator<AdsEntity> CREATOR = new Creator<AdsEntity>() {
        public AdsEntity createFromParcel(Parcel source) {
            return new AdsEntity(source);
        }

        public AdsEntity[] newArray(int size) {
            return new AdsEntity[size];
        }
    };
}