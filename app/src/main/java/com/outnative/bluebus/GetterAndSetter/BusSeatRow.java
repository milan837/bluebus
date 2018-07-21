package com.outnative.bluebus.GetterAndSetter;

/**
 * Created by milan on 9/4/2017.
 */

public class BusSeatRow {
    public String seatNumber;
    public String bookedNumber;

    public BusSeatRow(String snum,String bnum){
        this.setBookedNumber(bnum);
        this.setSeatNumber(snum);
    }

    public void setBookedNumber(String bookedNumber) {
        this.bookedNumber = bookedNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    public String getBookedNumber() {
        return bookedNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}
