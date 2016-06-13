package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyy on 2015/7/28.
 */
public class PushEntity implements Parcelable{
    private String title;
    private String alert;
    private String description;
    private String sound;
    private String badge;
    private int type;
    private String objId;
    private String url;
    private String imageUrl;

    public PushEntity(){

    }

    private PushEntity(Parcel in) {
        title = in.readString();
        alert = in.readString();
        description = in.readString();
        sound = in.readString();
        badge = in.readString();
        type = in.readInt();
        objId = in.readString();
        url = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<PushEntity> CREATOR = new Creator<PushEntity>() {
        @Override
        public PushEntity createFromParcel(Parcel in) {
            return new PushEntity(in);
        }

        @Override
        public PushEntity[] newArray(int size) {
            return new PushEntity[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(alert);
        dest.writeString(description);
        dest.writeString(sound);
        dest.writeString(badge);
        dest.writeInt(type);
        dest.writeString(objId);
        dest.writeString(url);
        dest.writeString(imageUrl);
    }
}
