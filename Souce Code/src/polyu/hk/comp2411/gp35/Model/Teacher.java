package polyu.hk.comp2411.gp35.Model;

import java.util.HashMap;
import java.util.Objects;

import static polyu.hk.comp2411.gp35.Model.Connection.*;


public class Teacher {
    private String[] basic_info;// name, depart, email
    private final String id;
    private String name;
    private String password;
    private final HashMap<String,Subject> subjects;
    private String ClassCharge;

    Teacher(String id,String pass){
        this.id = id;
        this.password = pass;
        subjects = new HashMap<>();
        basic_info = new String[3];
        setInfo();
    }

    private void setInfo(){
        basic_info = getData("select `name`,`depart`,`email` from `teacher` where `staff_id` = "+Pack.pack(id),3);
        this.name = basic_info[0];
        String[] subs = getData("SELECT `subject_code` from `has` where `staff_id`= " + Pack.pack(id));

        if (subs!=null)
            for (int i = 0; i < 2; i++) { subjects.put(subs[i], new Subject(subs[i], id, '0')); }

        try{
            ClassCharge = (Objects.requireNonNull(getData("select `class_no.` from `class` where `staff_id` = " + Pack.pack(id))))[0];
        }catch (Exception ignored){}
    }

    public String[] getBasic_info(){return basic_info;};

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String,Subject> getSubjects() {
        return subjects;
    }

    public String getClassCharge() {
        return ClassCharge;
    }

    public void setPassword(String password) {
        this.password = password;
        updateData("update `teacher` set `password` = "+Pack.pack(password)+" where `staff_id` = "+Pack.pack(id));
    }

    public String getName() {
        return name;
    }
}
