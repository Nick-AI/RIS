package Scheduling;

import java.sql.DriverManager;

public class TestDriver {

    static java.sql.Connection con;

    public static void main(String[] args){

        Scheduler sched = new Scheduler(createConnection());
//        sched.createEvent(new Event("CAT", "Shoulder is messed up", 10, 1000000000, 1));
//        sched.createEvent(new Event("XRAY", "Hip sucks", 10, 1000000000, 1));
//        sched.createEvent(new Event("FMRI", "Knees", 10, 1000000000, 1));
//        sched.insertEvent(new Event("CAT", "Medium Important Shit", 10, 1000000000, 1), 3);
//        sched.removeEvent(new Event("CAT", "Shoulder is messed up", 10, 1000000000, 1));

    }
    public static java.sql.Connection createConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/irSystem","root", "");
            //CHANGME Variable changes depending on your assigned password for MySQLWorkBench
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
