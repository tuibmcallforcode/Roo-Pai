package thammasat.callforcode.manager;

import java.util.List;

import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;

public class Singleton {

    private List<Disaster> disasterList;
    private List<DisasterMap> disasterMapList;

    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public List<Disaster> getDisasterList() {
        return disasterList;
    }

    public void setDisasterList(List<Disaster> disasterList) {
        this.disasterList = disasterList;
    }

    public List<DisasterMap> getDisasterMapList() {
        return disasterMapList;
    }

    public void setDisasterMapList(List<DisasterMap> disasterMapList) {
        this.disasterMapList = disasterMapList;
    }
}
