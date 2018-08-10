package thammasat.callforcode.model;

import java.io.Serializable;

public class Disaster implements Serializable {
    private String title;
    private String status;
    private String body;
    private double lat;
    private double lon;
    private String source;
    private String country;
    private String disaster;
    private String url;
    private String url_thumb;

    public Disaster(String title, String status, String body, double lat, double lon, String source, String country, String disaster, String url, String url_thumb) {
        this.title = title;
        this.status = status;
        this.body = body;
        this.lat = lat;
        this.lon = lon;
        this.source = source;
        this.country = country;
        this.disaster = disaster;
        this.url = url;
        this.url_thumb = url_thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisaster() {
        return disaster;
    }

    public void setDisaster(String disaster) {
        this.disaster = disaster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_thumb() {
        return url_thumb;
    }

    public void setUrl_thumb(String url_thumb) {
        this.url_thumb = url_thumb;
    }
}
