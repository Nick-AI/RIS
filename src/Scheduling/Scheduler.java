package Scheduling;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.sql.SQLException;


public class Scheduler {

    private ArrayList<TimeSlot> schedule = new ArrayList();
    private java.sql.Connection con;
    private java.sql.Statement stmt;
    private int maxProcID;

    public Scheduler(java.sql.Connection c){
        this.con = c;
        try {
            stmt = con.createStatement();
            String sql = "SELECT procID FROM schedule ORDER BY procID DESC LIMIT 1";
            ResultSet entry = stmt.executeQuery(sql);
            if(entry.next())
                maxProcID = entry.getInt("procID");
            this.fetchSchedule();
        }
        catch(SQLException e){
            System.out.println("There was a problem with getting the newest procedure ID");
            System.out.println(e.getErrorCode());
        }
    }

    public boolean createEvent(Event e){
        this.maxProcID++;
        e.setProcID(this.maxProcID);
        boolean wasAdded = false;
        for(TimeSlot slot : schedule){
            if(!slot.isFull() && !slot.containsProcedure(e)) {
                if (slot.addEvent(e)){
                    wasAdded = true;
                    break;
                }
            }
        }
        if(!wasAdded) {
            Collections.sort(schedule);
            Collections.reverse(schedule);
            Appointment prev = schedule.get(schedule.size() - 1).getTime();
            try {
                if (prev.getTimeSlot() < 20) {
                    TimeSlot temp = new TimeSlot(new Appointment(prev.getMonth(), prev.getDay(), prev.getYear(),
                            prev.getTimeSlot() + 1));
                    temp.addEvent(e);
                    schedule.add(temp);
                }
                else {
                    String newTime = getNextDate(prev.toString().substring(0, 10));
                    TimeSlot temp = new TimeSlot(new Appointment(Integer.parseInt(newTime.substring(0, 2)),
                            Integer.parseInt(newTime.substring(3, 5)), Integer.parseInt(newTime.substring(6, 10)), 1));
                    temp.addEvent(e);
                    schedule.add(temp);
                }
            }
            catch(Appointment.InvalidDateException | ParseException ex){
                this.maxProcID--;
                System.out.println(ex.getMessage());
                return false;
            }
        }
        this.propChangesToDB();
        return true;
    }

    public boolean removeEvent(Event e){
        for(TimeSlot temp : schedule) {
            for (Event tempEv : temp.getEvents()){
                if (tempEv.equals(e)) {
                    temp.removeEvent(e);
                    this.propChangesToDB();
                    return true;
                }
            }
        }
        return false;
    }

    public void startTreatment(int patientID){
        Date date = new Date();
        Time time = new Time(date.getTime());
        int staffID = 0;
        int procID = ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).getProcID();
        ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).advStatus();
        try {
            stmt = con.createStatement();
            String presentQuery = "SELECT * FROM presentstaff";
            String staffQuery = "SELECT stafftype FROM staff where 'staffID' = ?";
            PreparedStatement getStaff = con.prepareStatement(staffQuery);
            ResultSet present = stmt.executeQuery(presentQuery);
            while(present.next()){
                getStaff.setInt(1, present.getInt("staffID"));
                ResultSet currStaff = getStaff.executeQuery();
                while(currStaff.next()){
                    if(currStaff.getString("staffType").equals("Nurse")){
                        staffID = currStaff.getInt("staffID");
                        break;
                    }
                }
            }
        }
        catch(SQLException e){
            System.out.println("There was a problem with fetching the present staff from the database");
            System.out.println(e.getErrorCode());
            System.out.println(this.schedule.toString());
        }

        try {
            String query = "INSERT INTO status (nextStaffID, currentlyWaiting, waitingSince, patientID, procID) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement upd = con.prepareStatement(query);
            upd.setInt(1, staffID);
            upd.setBoolean(2, true);
            upd.setTime(3, time);
            upd.setInt(4, patientID);
            upd.setInt(5, procID);
            upd.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("There was a problem with adding the current procedure to the table");
            System.out.println(e);
        }
    }

    public void nextTreatmentStep(int patientID){
        Date date = new Date();
        Time time = new Time(date.getTime());
        boolean treatmentFinished = false;
        int staffID = 0;
        int procID = ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).getProcID();
        int currStep = ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).getStatus();
        ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).advStatus();
        try {
            stmt = con.createStatement();
            String presentQuery = "SELECT * FROM presentstaff";
            String staffQuery = "SELECT stafftype FROM staff where 'staffID' = ?";
            PreparedStatement getStaff = con.prepareStatement(staffQuery);
            ResultSet present = stmt.executeQuery(presentQuery);
            while(present.next()){
                getStaff.setInt(1, present.getInt("staffID"));
                ResultSet currStaff = getStaff.executeQuery();
                while(currStaff.next()){
                    if(currStep==2) {
                        if (currStaff.getString("staffType").equals("Technician")) {
                            staffID = currStaff.getInt("staffID");
                            break;
                        }
                    }
                    else if(currStep==3){
                        if (currStaff.getString("staffType").equals("Radiologist")) {
                            staffID = currStaff.getInt("staffID");
                            break;
                        }
                    }
                    else{
                        treatmentFinished = true;
                    }
                }
            }
        }
        catch(SQLException e){
            System.out.println("There was a problem with fetching the present staff from the database");
            System.out.println(e.getErrorCode());
            System.out.println(this.schedule.toString());
        }
        if(!treatmentFinished) {
            try {
                String query = "INSERT INTO status (nextStaffID, currentlyWaiting, waitingSince, patientID, procID) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement upd = con.prepareStatement(query);
                upd.setInt(1, staffID);
                upd.setBoolean(2, true);
                upd.setTime(3, time);
                upd.setInt(4, patientID);
                upd.setInt(5, procID);
                upd.executeUpdate();
            } catch (SQLException e) {
                System.out.println("There was a problem with adding the current procedure to the table");
                System.out.println(e);
            }
        }
        else{
            //add way of notifying that radorder is finished
            try {
                String query = "DELETE FROM status WHERE 'patientID' = ?";
                PreparedStatement upd = con.prepareStatement(query);
                upd.setInt(1, patientID);
                upd.executeUpdate();
            } catch (SQLException e) {
                System.out.println("There was a problem with deleting the procedure");
                System.out.println(e);
            }
            this.removeEvent(new Event(null, null, 0, patientID,
                    ((Event)this.getPatientEvents(patientID).entrySet().toArray()[0]).getOrderID()));

        }
    }

    public void insertEvent(Event e, int priority){
        e.setPriority(priority);
        this.maxProcID++;
        e.setProcID(this.maxProcID);
        for(TimeSlot temp : schedule){
            Appointment a;
            if(!temp.containsProcedure(e)){
                temp.addEvent(e);
                break;
            }
            else{
                if(temp.getProcedure(e).getPriority()>priority){
                    a = temp.getTime();
                    this.shiftEvents(a);
                    break;
                }
            }
        }
        this.createEvent(e);
        this.propChangesToDB();
    }

    private void shiftEvents(Appointment a){
        Collections.sort(schedule);
        Collections.reverse(schedule);
        ArrayList<TimeSlot> shiftedSchedule = new ArrayList();
        boolean isBefore = true;
        for(TimeSlot temp : schedule){
            boolean shiftSlot = true;
            if(temp.getTime().equals(a) && isBefore){
                isBefore = false;
                shiftedSchedule.add(new TimeSlot(a));
            }
            else if(isBefore){
                shiftedSchedule.add(temp);
                shiftSlot = false;
            }
            if(shiftSlot){
                try {
                    TimeSlot slot;
                    if (temp.getTime().getTimeSlot() < 20) {
                        slot = new TimeSlot(new Appointment(temp.getTime().getMonth(), temp.getTime().getDay(),
                                temp.getTime().getYear(), temp.getTime().getTimeSlot() + 1));
                        for(Event e : temp.getEvents()){
                            if(e!=null)
                                slot.addEvent(e);
                        }
                    }
                    else {
                        String newTime = getNextDate(temp.getTime() .toString().substring(0, 10));
                        slot = new TimeSlot(new Appointment(Integer.parseInt(newTime.substring(0, 2)),
                                Integer.parseInt(newTime.substring(3, 5)), Integer.parseInt(newTime.substring(6, 10)), 1));
                        for(Event e : temp.getEvents()){
                            if(e!=null)
                                slot.addEvent(e);
                        }
                    }
                    shiftedSchedule.add(slot);
                }
                catch(Appointment.InvalidDateException | ParseException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        this.schedule = shiftedSchedule;
    }

    public HashMap<Appointment, Event> getPatientEvents(int patientID){
        HashMap<Appointment, Event> patientAppointments = new HashMap();
        for (TimeSlot temp: this.schedule){
            for(Event slotEvent : temp.getEvents()){
                if(slotEvent.getPatientID() == patientID)
                    patientAppointments.put(temp.getTime(), slotEvent);
            }
        }
        return patientAppointments;
    }

    private Appointment findEvent(int procID){
        for(TimeSlot temp : schedule){
            if(temp.containsEvent(procID)){
                return temp.getTime();
            }
        }
        return null;
    }

    public ArrayList<TimeSlot> showSchedule(){
        return this.schedule;
    }

    private void fetchSchedule(){
        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM schedule";
            ResultSet entries = stmt.executeQuery(sql);
            while(entries.next()){
                Event tempEvent = null;
                Appointment tempAppointment = null;
                //create new Event for ArrayList
                String modality = entries.getString("modality");
                String descr = entries.getString("procType");
                int priority = 10;
                int patientId = entries.getInt("patientID");
                int orderId = entries.getInt("orderID");
                int procId = this.maxProcID+1;
                this.maxProcID++;
                tempEvent = new Event(modality, descr, priority, patientId, orderId);
                tempEvent.setProcID(procId);

                //create new Appointment for ArrayList
                String date = entries.getString("ProcTime");
                int year = Integer.parseInt(date.substring(6, 10));
                int dayIdx = Integer.parseInt(date.substring(3, 5));
                int monIdx = Integer.parseInt(date.substring(0, 2));
                int timeslot = Integer.parseInt(date.substring(11));
                try {
                    tempAppointment = new Appointment(monIdx, dayIdx, year, timeslot);
                }
                catch(Appointment.InvalidDateException e){
                    System.out.println(e.getMessage());
                    System.out.println("The date was:" + monIdx + "/" + dayIdx + "/" + year);
                }

                //add Appointment to schedule ArrayList
                boolean wasAdded = false;
                for(TimeSlot item : schedule){
                    if(item.getTime().equals(tempAppointment)) {
                        if(item.addEvent(tempEvent)){
                            wasAdded = true;
                            break;
                        }
                    }
                }
                if(!wasAdded){
                    TimeSlot temp = new TimeSlot(tempAppointment);
                    temp.addEvent(tempEvent);
                    schedule.add(temp);
                }
            }
        }
        catch(SQLException e){
            System.out.println("There was a problem with fetching the information from the database");
            System.out.println(e.getErrorCode());
            System.out.println(this.schedule.toString());
        }
    }

    private void propChangesToDB(){
        //INSERT INTO `schedule` (`procType`, `procTime`, `modality`, `patientID`, `procID`, `orderID`) VALUES ('Blabla shoulder bla', '12/12/2012-2', 'XRAY', '1000000000', '1', '1');
        try {
            String sql = "TRUNCATE TABLE schedule";
            stmt.executeUpdate(sql);
            for (TimeSlot item : schedule) {
                for (Event temp : item.getEvents()) {
                    if(temp!=null) {
                        try {
                            String query = "INSERT INTO schedule (procType, procTime, modality, patientID, procID, " +
                                    "orderID) VALUES (?, ?, ?, ?, ?, ?)";
                            PreparedStatement upd = con.prepareStatement(query);
                            upd.setString(1, temp.getDescr());
                            upd.setString(2, item.getTime().toDBFormat());
                            upd.setString(3, temp.getModality());
                            upd.setInt(4, temp.getPatientID());
                            upd.setInt(5, temp.getProcID());
                            upd.setInt(6, temp.getOrderID());
                            upd.executeUpdate();

                        }
                        catch(SQLException e){
                            System.out.println("There was a problem with adding the information to the database");
                            System.out.println(e);
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
                System.out.println("There was a problem with deleting the information from the database");
                System.out.println(e);
        }
    }

    private static String getNextDate(String  curDate) throws ParseException{
        final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }

}
