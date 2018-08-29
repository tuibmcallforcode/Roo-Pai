package thammasat.callforcode.manager;

import thammasat.callforcode.model.Disaster;

public interface OnItemClick {
    void onItemClick(Disaster disaster, long time, int distance);
}
