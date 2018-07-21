package com.outnative.bluebus.GetterAndSetter;

/**
 * Created by milan on 9/5/2017.
 */
public class BusDetailsRow {
    public String rideName,rideId,ticketPrice,boradingTime,depatureTime,travellingHours,seatAvailable;

    public BusDetailsRow(String name,String id,String price,String bTime,String dTime,String hours,String seat){
        this.setRideName(name);
        this.setRideId(id);
        this.setBoradingTime(bTime);
        this.setDepatureTime(dTime);
        this.setTicketPrice(price);
        this.setTravellingHours(hours);
        this.setSeatAvailable(seat);
    }

    public void setSeatAvailable(String seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public void setBoradingTime(String boradingTime) {
        this.boradingTime = boradingTime;
    }

    public void setDepatureTime(String depatureTime) {
        this.depatureTime = depatureTime;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTravellingHours(String travellingHours) {
        this.travellingHours = travellingHours;
    }

    public String getBoradingTime() {
        return boradingTime;
    }

    public String getDepatureTime() {
        return depatureTime;
    }

    public String getRideName() {
        return rideName;
    }

    public String getRideId() {
        return rideId;
    }

    public String getSeatAvailable() {
        return seatAvailable;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public String getTravellingHours() {
        return travellingHours;
    }
}
