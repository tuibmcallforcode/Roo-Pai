package thammasat.callforcode.manager;

import java.util.List;

import thammasat.callforcode.model.Disaster;

public class Singleton {

    private List<Disaster> disasterList;

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
}
