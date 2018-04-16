package Scheduling;

public class Event implements Comparable{

    private String modality;
    private String descr;
    private int priority = 10;
    private int patientID;
    private int orderID;
    private int procID = -1;
    private int status = 0; //Status moves through [0 -> not here, 1 -> waiting area, 2 -> vitals, 3-> imaging, 4-> report]

    public Event(String m, String d, int prio, int pID, int oID){
        this.modality = m;
        this.descr = d;
        this.priority = prio;
        this.patientID = pID;
        this.orderID = oID;
    }

    public int getProcID(){
        return this.procID;
    }

    public int getStatus(){
        return this.status;
    }

    public void advStatus(){
        this.status++;
    }

    public String getDescr(){
        return this.descr;
    }


    public int getPriority(){
        return this.priority;
    }

    public String getModality() {

        return modality;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setPriority(int p){

        this.priority = p;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int hashCode(){
        return modality.hashCode();
    }

    public void setProcID(int newID){
        this.procID = newID;
    }

    public int compareTo(Object o){
        if(o == null)
            return -1;
        else if(!(o instanceof Event))
            return -1;
        else if(((Event)o).getPriority() < this.priority)
            return 1;
        else if(((Event)o).getPriority() == this.priority)
            return 0;
        return -1;
    }

    public String toString(){
        return "Type: " + modality + "\nUrgency: " + priority + "\nPatient: " + patientID + "\nOrder:" + orderID;
    }

    public boolean equals(Object o){
        if (o == null)
            return false;
        if (!(o instanceof Event))
            return false;
        return (((Event)o).getOrderID()==this.orderID && ((Event)o).getPatientID()==this.getPatientID());
    }
}
