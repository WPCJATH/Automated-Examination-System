package polyu.hk.comp2411.gp35.Model;

import polyu.hk.comp2411.gp35.Controller.AESController;

import java.util.Objects;

public class TeaSignIn {
    private final String id;
    private final Teacher curTeacher;

    public TeaSignIn(String id, String password) throws IllegalArgumentException {
        String pass = Objects.requireNonNull(Connection.getData("SELECT `password` FROM `teacher` WHERE `staff_id`= " + Pack.pack(id)))[0];
        if (!pass.equals(password))
            throw new IllegalArgumentException();

        this.id = id;

        curTeacher = new Teacher(id,pass);

        AESController.dataPool.setCurTeacher(curTeacher);
        AESController.dataPool.setId(id);
    }

    public  String getId() {
        return id;
    }

    public  Teacher getCurTeacher() {return curTeacher;}
}
