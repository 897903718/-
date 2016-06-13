package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyy on 2015/9/10.
 */
public class MessageEntity implements Parcelable {
    private String title;
    private String alert;
    private String description;
    private String sound;
    private String badge;
    private int type;
    private String objId;
    private String url;
    private String imageUrl;//未读消息小红点展示 0未读1已读
    private String id;
    private String time;

    public MessageEntity(){

    }

    public MessageEntity(String id,String title,String description,String imageUrl ,String time){
        this.id=id;
        this.title=title;
        this.description=description;
        this.imageUrl=imageUrl;
        this.time=time;

    }

    public MessageEntity(String id,String title,String description,String imageUrl ,String time,int type,String objId,String url){
        this.id=id;
        this.title=title;
        this.description=description;
        this.imageUrl=imageUrl;
        this.time=time;
        this.type=type;
        this.objId=objId;
        this.url=url;

    }

    private MessageEntity(Parcel in) {
        title = in.readString();
        alert = in.readString();
        description = in.readString();
        sound = in.readString();
        badge = in.readString();
        type = in.readInt();
        objId = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        id = in.readString();
        time = in.readString();
    }

    public static final Creator<MessageEntity> CREATOR = new Creator<MessageEntity>() {
        @Override
        public MessageEntity createFromParcel(Parcel in) {
            return new MessageEntity(in);
        }

        @Override
        public MessageEntity[] newArray(int size) {
            return new MessageEntity[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(id);
        dest.writeString(time);
    }
}
