package polyu.hk.comp2411.gp35.Model;


import polyu.hk.comp2411.gp35.Controller.AESController;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Once a student is going to sign in, a variable of class StuSignIn will be init.
 * The constructor check whether there has such student ID in database and the ID is match the password.
 * If not, Exception occurs and the constructor will stop, system will ask the user to enter again.
 * Until the right and ID and password are legal.
 * The class will store the current student ID and current student in Student class(created below)
 */
public class StuSignIn{//'19629510d'
    private static String id;
    private final Student curStudent;

    public StuSignIn(String id, String password) throws IllegalArgumentException {
        String pass = Objects.requireNonNull(Connection.getData("SELECT `password` FROM `student` WHERE `stu_id`= " + Pack.pack(id)))[0];
        if (!pass.equals(password))
            throw new IllegalArgumentException();

        StuSignIn.id = id;

        curStudent = new Student(id,pass);// create a Student to store the info of current student

        AESController.dataPool.setCurStudent(curStudent);
        AESController.dataPool.setId(id);
    }

    public  String getId() {
        return id;
    }

    public  Student getCurStudent() {
        return curStudent;
    }
}
