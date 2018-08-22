package thammasat.callforcode.manager;

import java.util.List;

import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.DisasterMap;

public class Singleton {

    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
