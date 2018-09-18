package thammasat.callforcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Disaster implements Serializable {
    @SerializedName("loc")
    @Expose
    private Loc loc;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("relief_id")
    @Expose
    private Integer reliefId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("brief_body")
    @Expose
    private String briefBody;
    @SerializedName("categories")
    @Expose
    private String categories;
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

    public Integer getReliefId() {
        return reliefId;
    }

    public void setReliefId(Integer reliefId) {
        this.reliefId = reliefId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBriefBody() {
        return briefBody;
    }

    public void setBriefBody(String briefBody) {
        this.briefBody = briefBody;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
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
