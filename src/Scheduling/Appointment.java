package Scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class Appointment {

    private DateValidator validator = new DateValidator();
    private int year;
    private int dayIdx;
    public int monIdx;
    private Month mon;
    private int timeslot; //business hours: 8:00-18:00 | time slots are allocated in 30 minute intervals (20 slots/day)
                          //e.g. time slot 5 would be 8 + 5//2 : 00 + 5%2*30 = 10:30


    public Appointment(int m, int d, int y,  int t) throws InvalidDateException{
        this.year = y;
        this.dayIdx = d;
        this.monIdx = m;
        this.mon = Month.getMonth(m);
        this.timeslot = t;
        if(!this.validator.isThisDateValid(this.toString().substring(0,10), "MM/dd/yyyy"))
            throw new InvalidDateException("The date you entered does not exist");
    }

    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.mon.MONTHINDEX;
    }

    public int getDay(){
        return this.dayIdx;
    }

    public int getTimeSlot(){
        return this.timeslot;
    }

    public String getTime(){
        if(timeslot%2 == 0){
            return (8+timeslot/2) + ":00";
        }
        return (8+(timeslot-1)/2) + ":30";
    }

    public String toString(){
        if(this.dayIdx>9&& this.mon.MONTHINDEX>9)
            return this.mon.MONTHINDEX + "/" + this.dayIdx + "/" + this.year + "-" + getTime();
        else if(this.dayIdx<10)
            return this.mon.MONTHINDEX + "/0" + this.dayIdx + "/" + this.year + " - " + getTime();
        else if(this.mon.MONTHINDEX<10)
            return "0" + this.mon.MONTHINDEX + "/" + this.dayIdx + "/" + this.year + " - " + getTime();
        else
            return "0" + this.mon.MONTHINDEX + "/0" + this.dayIdx + "/" + this.year + " - " + getTime();
    }

    public String toDBFormat(){
        if(this.dayIdx>9&& this.mon.MONTHINDEX>9)
            return this.mon.MONTHINDEX + "/" + this.dayIdx + "/" + this.year + "-" + this.timeslot;
        else if(this.dayIdx<10)
            return this.mon.MONTHINDEX + "/0" + this.dayIdx + "/" + this.year + "-" + this.timeslot;
        else if(this.mon.MONTHINDEX<10)
            return "0" + this.mon.MONTHINDEX + "/" + this.dayIdx + "/" + this.year + "-" + this.timeslot;
        else
            return "0" + this.mon.MONTHINDEX + "/0" + this.dayIdx + "/" + this.year + "-" + this.timeslot;
    }


    public boolean equals(Object o){
        if (o == null)
            return false;
        if (!(o instanceof Appointment))
            return false;
        return (((Appointment) o).timeslot == this.timeslot && ((Appointment) o).year == this.year &&
                ((Appointment) o).dayIdx == this.dayIdx && ((Appointment) o).monIdx == this.monIdx);
    }

    public int compareTo(Object o) {
        if (o == null)
            return -1000;
        if (!(o instanceof Appointment))
            return -1000;
        if (((Appointment) o).getYear()>=this.year){
            if(((Appointment) o).getMonth()>=this.mon.MONTHINDEX){
                if(((Appointment) o).getDay()>=this.dayIdx){
                    if(((Appointment) o).getTimeSlot()>this.timeslot)
                        return 1;
                    else if(((Appointment) o).getTimeSlot() == this.timeslot)
                        return 0;
                    else
                        return -1;
                }
                else
                    return -1;
            }
            else
                return -1;
        }
        else
            return -1;
    }

    public enum Month{
        JANUARY (1),
        FEBRUARY (2),
        MARCH (3),
        APRIL (4),
        MAY (5),
        JUNE (6),
        JULY(7),
        AUGUST (8),
        SEPTEMBER (9),
        OCTOBER (10),
        NOVEMBER (11),
        DECEMBER (12);

        private final int MONTHINDEX;

        Month(int i){
            this.MONTHINDEX = i;
        }

        public static Month getMonth(int idx){
            for(Month mon : Month.values()){
                if(mon.MONTHINDEX == idx)
                    return mon;
            }
            return null;
        }
    }
    public class InvalidDateException extends Exception{

        public InvalidDateException(String message) {
            super(message);
        }
    }

    public class DateValidator {

        public boolean isThisDateValid(String dateToValidate, String dateFromat){

            if(dateToValidate == null){
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
            sdf.setLenient(false);

            try {
                //if not valid, it will throw ParseException
                Date date = sdf.parse(dateToValidate);

            } catch (ParseException e) {

                return false;
            }

            return true;
        }

    }
}
