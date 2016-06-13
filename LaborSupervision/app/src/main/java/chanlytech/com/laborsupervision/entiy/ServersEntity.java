package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 应用
 */
public class ServersEntity extends BaseEntity implements Parcelable {
    @SerializedName("id")
    private String appId;
    private String imageUrl;
    private String source;
    private String name;
    private int type;
    /**
     * 是否阅读 1是新的  0不是
     */
    @SerializedName("isNew")
    private String newState;
    /**
     * 应用的位置
     */
    private int position;

    /**
     * 跳转链接
     */
    private String url;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.source);
        dest.writeString(this.name);
        dest.writeString(this.newState);
        dest.writeInt(this.position);
        dest.writeString(this.url);
        dest.writeInt(this.type);
    }

    public ServersEntity() {
    }

    private ServersEntity(Parcel in) {
        this.appId = in.readString();
        this.imageUrl = in.readString();
        this.source = in.readString();
        this.name = in.readString();
        this.newState = in.readString();
        this.position = in.readInt();
        this.url = in.readString();
        this.type=in.readInt();
    }

    public static Creator<ServersEntity> CREATOR = new Creator<ServersEntity>() {
        public ServersEntity createFromParcel(Parcel source) {
            return new ServersEntity(source);
        }

        public ServersEntity[] newArray(int size) {
            return new ServersEntity[size];
        }
    };
}
