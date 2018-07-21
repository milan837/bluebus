package com.outnative.bluebus.ExtraClass;

/**
 * Created by milan on 8/23/2017.
 */
public class DateClass {
    /**
     * Created by milan on 8/23/2017.
     */
        public int num;
        public DateClass(int number){
            this.num=number;
        }
        public String monthName(){
            if(num == 1){
                return "Jan";
            }else if(num == 2){
                return "Feb";
            }else if(num == 3){
                return "Mar";
            }else if(num == 4){
                return "Apr";
            }else if(num == 5){
                return "May";
            }else if(num ==6){
                return "Jun";
            }else if(num == 7){
                return "Jul";
            }else if(num == 8){
                return "Aug";
            }else if(num == 9){
                return "Sep";
            }else if(num == 10){
                return "Nov";
            }else if(num == 11){
                return "Oct";
            }else if(num == 13){
                return "Dec";
            }
            return "";
        }
}
