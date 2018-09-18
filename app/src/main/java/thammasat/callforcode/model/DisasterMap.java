package thammasat.callforcode.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class DisasterMap implements ClusterItem, Serializable {

    @SerializedName("loc")
    @Expose
    private Loc loc;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("pdc_id")
    @Expose
    private String pdcId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("position")
    @Expose
    private LatLng position;
    @SerializedName("profilePhoto")
    @Expose
    private int profilePhoto;

    public DisasterMap(LatLng position, String id, String pdcId, Integer v, String description, String severity, String source, String time, String title, int profilePhoto) {
        this.position = position;
        this.id = id;
        this.pdcId = pdcId;
        this.v = v;
        this.description = description;
        this.severity = severity;
        this.source = source;
        this.time = time;
        this.title = title;
        this.profilePhoto = profilePhoto;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdcId() {
        return pdcId;
    }

    public void setPdcId(String pdcId) {
        this.pdcId = pdcId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}