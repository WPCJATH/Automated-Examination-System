package polyu.hk.comp2411.gp35.View;

import javax.swing.*;

public class ManualConnect  {
    String[] info;
    final int CONFIRM = 0, CANCEL = 1;  // value of user's choices of option dialog
    public ManualConnect(){
        info = new String[5];
        JTextField Host = new JTextField("127.0.0.1");
        JTextField Port = new JTextField("3306");
        JTextField Connection = new JTextField();
        JTextField user = new JTextField("root");
        JPasswordField pwd = new JPasswordField();
        Object[] message = {"Database connection failed.\nPlease enter HostName, Port, ConnectionName, user, password manually.\nHost name"
                ,Host,"Port",Port,"Connection Name",Connection,"User Name",user,"Password",pwd};

        String[] op = new String[]{"OK","Cancel"};
        //System.out.println("Please enter HostName, Port, ConnectionName, user, password manually.");
        //String HostName = CLIin("Enter your HostName: ");//;
        //String Port = CLIin("Enter your Port: ");//"3306";
        //String ConnectionName = CLIin("Enter your ConnectionName: ");//"AESData";
        //String USER = CLIin("Enter your User Name: ");//;
        //String PASS = CLIin("Enter your Password: ");//"123456";
        int userChoice = JOptionPane.showOptionDialog(null,message,"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op);
        if (userChoice == CONFIRM) {
            info[0] = Host.getText();
            info[1] = Port.getText();
            info[2] = Connection.getText();
            info[3] = user.getText();
            info[4] = pwd.getText();
        }
        else System.exit(0);
    }

    public static String[] connect(){
        return null;
    }

    public String[] getInfo() {
        return info;
    }
}
