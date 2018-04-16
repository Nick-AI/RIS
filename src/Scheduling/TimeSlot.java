package Scheduling;
import java.util.HashMap;

public class TimeSlot implements Comparable{
    private HashMap<String, Event> slot = new HashMap();
    private Appointment time;
    private boolean isFull = false;
    private final int MAX_MODALITIES = 8;

    public TimeSlot(Appointment t){
        this.time = t;
    }

    public boolean getIsFull(){
        return isFull;
    }

    public boolean containsEvent(int procID){
        for (Event temp : slot.values()){
            if(temp.getProcID() == procID)
                return true;
        }
        return false;
    }

    public boolean containsProcedure(Event e){
        return slot.containsKey(e.getModality());
    }

    public Event getProcedure(Event e){
        return slot.get(e.getModality());
    }

    public Event[] getEvents(){
        Event[] e = new Event[this.MAX_MODALITIES];
        return slot.values().toArray(e);
    }

    public boolean addEvent(Event e){
        if(slot.containsKey(e.getModality()))
            return false;
        else {
            slot.put(e.getModality(), e);
            if(slot.size()==this.MAX_MODALITIES)
                isFull = true;
        }
        return true;
    }

    public Appointment getTime() {
        return time;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean removeEvent(Event e){
        if(slot.containsKey(e.getModality())) {
            slot.remove(e.getModality(), e);
            isFull = false;
            return true;
        }
        else
            return false;
    }

    public String toString(){
        String out = time.toString()+":\n";
        for(Event item : slot.values())
            out += "\t" + item.toString() + "\n";
        return out;
    }

    public boolean equals(Object o){
        if (o == null)
            return false;
        if (!(o instanceof TimeSlot))
            return false;
        return ((TimeSlot) o).getTime().equals(this.time);
    }

    public int compareTo(Object o){
        if(!(o instanceof TimeSlot))
            return -1;
        else
            return time.compareTo(((TimeSlot)o).getTime());
    }

}
