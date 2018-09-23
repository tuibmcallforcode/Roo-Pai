package thammasat.callforcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prepareness {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("afterHTML")
    @Expose
    private String afterHTML;
    @SerializedName("afterText")
    @Expose
    private String afterText;
    @SerializedName("descriptionHTML")
    @Expose
    private String descriptionHTML;
    @SerializedName("descriptionText")
    @Expose
    private String descriptionText;
    @SerializedName("duringHTML")
    @Expose
    private String duringHTML;
    @SerializedName("duringText")
    @Expose
    private String duringText;
    @SerializedName("prepareHTML")
    @Expose
    private String prepareHTML;
    @SerializedName("prepareText")
    @Expose
    private String prepareText;
    @SerializedName("tipsHTML")
    @Expose
    private String tipsHTML;
    @SerializedName("tipsText")
    @Expose
    private String tipsText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAfterHTML() {
        return afterHTML;
    }

    public void setAfterHTML(String afterHTML) {
        this.afterHTML = afterHTML;
    }

    public String getAfterText() {
        return afterText;
    }

    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getDuringHTML() {
        return duringHTML;
    }

    public void setDuringHTML(String duringHTML) {
        this.duringHTML = duringHTML;
    }

    public String getDuringText() {
        return duringText;
    }

    public void setDuringText(String duringText) {
        this.duringText = duringText;
    }

    public String getPrepareHTML() {
        return prepareHTML;
    }

    public void setPrepareHTML(String prepareHTML) {
        this.prepareHTML = prepareHTML;
    }

    public String getPrepareText() {
        return prepareText;
    }

    public void setPrepareText(String prepareText) {
        this.prepareText = prepareText;
    }

    public String getTipsHTML() {
        return tipsHTML;
    }

    public void setTipsHTML(String tipsHTML) {
        this.tipsHTML = tipsHTML;
    }

    public String getTipsText() {
        return tipsText;
    }

    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
    }
}
