package chanlytech.com.laborsupervision.entiy;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.Constance;


/**
 * Created by Lyy on 2015/7/6.
 * 城市实体
 */
public class CityEntity extends BaseEntity implements Parcelable{
    /**
     * 地区id
     */
    private String cityId;
    /**
     * 百度城市id
     */
    private String baiduCityId;
    /**
     * 城市名字
     */
    private String name;
    /**
     * 城市首字母
     */
    private String area;
    /**
     * 纬度
     */
    private double longitude=104.072007;
    /**
     * 经度
     */
    private double latitude=30.663732;
    private String address;
    private int isCheck=1;//是否选中
    public String getCityId()
    {
        return TextUtils.isEmpty(cityId) ? Constance.DEFAULT.DEFAULT_CITY_CODE + "" : cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getBaiduCityId()
    {
        return baiduCityId;
    }

    public void setBaiduCityId(String baiduCityId)
    {
        this.baiduCityId = baiduCityId;
    }

    public String getName()
    {
        return TextUtils.isEmpty(name) ? "高新区" : name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public int isCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CityEntity()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.cityId);
        dest.writeString(this.baiduCityId);
        dest.writeString(this.name);
        dest.writeString(this.area);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeInt(this.isCheck);
        dest.writeString(this.address);
    }

    private CityEntity(Parcel in)
    {
        this.cityId = in.readString();
        this.baiduCityId = in.readString();
        this.name = in.readString();
        this.area = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.isCheck=in.readInt();
        this.address=in.readString();

    }

    public static Creator<CityEntity> CREATOR = new Creator<CityEntity>()
    {
        public CityEntity createFromParcel(Parcel source)
        {
            return new CityEntity(source);
        }

        public CityEntity[] newArray(int size)
        {
            return new CityEntity[size];
        }
    };
}
