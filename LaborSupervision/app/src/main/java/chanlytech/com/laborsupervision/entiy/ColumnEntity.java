package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lyy on 2015/7/22.
 * 栏目实体
 */
public class ColumnEntity implements Parcelable{
    private String id;
    private String name;

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

    public ColumnEntity(String id, String name){
        this.name = name;
        this.id = id;
    }

    public ColumnEntity(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Creator<ColumnEntity>CREATOR=new Creator<ColumnEntity>() {
        @Override
        public ColumnEntity createFromParcel(Parcel source) {
            ColumnEntity columnEntity=new ColumnEntity();
            columnEntity.id=source.readString();
            columnEntity.name=source.readString();
            return columnEntity;
        }

        @Override
        public ColumnEntity[] newArray(int size) {
            return new ColumnEntity[size];
        }
    };
}
