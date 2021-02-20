package polyu.hk.comp2411.gp35.Controller;

import polyu.hk.comp2411.gp35.Model.*;
import polyu.hk.comp2411.gp35.View.ManualConnect;
import polyu.hk.comp2411.gp35.View.ViewCentre;
import polyu.hk.comp2411.gp35.View.signIn;

import static polyu.hk.comp2411.gp35.Model.Connection.makeConnection;

public class AESController {
    private static Connection connect;

    private static void tryManualConnection(ManualConnect con){
        String[] info = con.getInfo();
        //System.out.println("Please enter HostName, Port, ConnectionName, user, password manually.");
        //String HostName = CLIin("Enter your HostName: ");//"127.0.0.1";
        //String Port = CLIin("Enter your Port: ");//"3306";
        //String ConnectionName = CLIin("Enter your ConnectionName: ");//"AESData";
        //String USER = CLIin("Enter your User Name: ");//"root";
        //String PASS = CLIin("Enter your Password: ");//"123456";
        connect = new Connection(info[0],info[1],info[2],info[3],info[4]);
        try {
            makeConnection();
        } catch (Exception e) {
            ManualConnect con2 = new ManualConnect();
            tryManualConnection(con2);
        }
    }

    public static class dataPool {
        static Student curStudent =null;
        static String id = "";
        static Teacher curTeacher = null;

        public static String getid() {
            return id; }

        public static Teacher getCurTeacher(){return curTeacher;}

        public static Student getCurStudent(){return curStudent;}

        public static void setId(String ID){
            id = ID;
        }

        public static void setCurStudent(Student student){
            curStudent = student;
        }

        public static void setCurTeacher(Teacher teacher){
            curTeacher = teacher;
        }
    }


    public static void main(String[] args) {
        connect = new Connection();
        try{
            makeConnection();
        } catch (Exception se){
            ManualConnect con = new ManualConnect();
            tryManualConnection(con);
        }

       // String id = "80970411s";//19378031d
       // String pass = "123456";

       // TeaSignIn tea = new TeaSignIn(id,pass);

        //AESController.dataPool.setCurTeacher(tea.getCurTeacher());

        //StuSignIn stu = new StuSignIn(id,pass);
        //AESController.dataPool.setId(id);
        //AESController.dataPool.setCurStudent(stu.getCurStudent());

       // ViewCentre.Open();


        new signIn();
    }
}

