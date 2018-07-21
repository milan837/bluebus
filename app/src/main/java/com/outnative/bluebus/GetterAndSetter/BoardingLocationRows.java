package com.outnative.bluebus.GetterAndSetter;


/**
 * Created by milan on 9/11/2017.
 */
public class BoardingLocationRows {
    String id,station;
    public BoardingLocationRows(String id,String station){
        this.setId(id);
        this.setStation(station);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getId() {
        return id;
    }

    public String getStation() {
        return station;
    }
}
