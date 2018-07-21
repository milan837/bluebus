package com.outnative.bluebus.GetterAndSetter;

/**
 * Created by milan on 8/22/2017.
 */
public class LocationRows {
    public String locationId;
    public String locationName;

    public LocationRows(String id,String name){
        this.setLocationId(id);
        this.setLocationName(name);
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }
}
