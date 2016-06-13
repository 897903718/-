package chanlytech.com.laborsupervision.entiy;

/**
 * Created by Lyy on 2015/7/23.
 */
public class VersionEntity {
    private String id;//版本id
    private String versionCode;
    private String download;
    private String description;
    private String deletedata;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeletedata() {
        return deletedata;
    }

    public void setDeletedata(String deletedata) {
        this.deletedata = deletedata;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
