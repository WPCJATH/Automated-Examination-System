package polyu.hk.comp2411.gp35.Model;

import java.util.HashMap;
import java.util.Objects;

import static polyu.hk.comp2411.gp35.Model.Connection.updateData;

/**
 * This class is built for both student sign in and teacher sign in
 * For student sign in:
 *         obtain data in database and store them in a java's way so that UI can easily access and update the data of the current student
 *         store personally direct information: name id password state depart email
 *         store subjects codes and link to them, so that exam,record,answers for each subject also maintained
 */
public class Student {
    private final String id;//for both
    private String classNo = null;//for student
    private String[] basicInfo;// name depart email // for student
    //private String name;
    private String state;// get from class in mysql
    private String password;//for student
    private HashMap<String,Subject> subjects;// for student
    private Subject subject;// for teacher

    // for login student
    Student(String id,String password){
        this.id =id;
        this.password = password;

        try{ classNo = (Objects.requireNonNull(Connection.getData("SELECT `class_no.` from `student` where `stu_id`=" + Pack.pack(id))))[0];}
        catch (Exception se){se.printStackTrace();}
        subjects = new HashMap<>();
        setInfo();
    }

    // for teacher login with teach model
    Student(String id,String name,Subject subject){
        this.id = id;
    }

    public void setInfo(){// name depart email
        basicInfo = Connection.getData("SELECT `name`,`depart`,`email` from `student` where `stu_id`=" +Pack.pack(id) ,3);
        state = Objects.requireNonNull(Connection.getData("SELECT `state` from `class` where `class_no.`=" + Pack.pack(classNo)))[0];
        String[] subs = Connection.getData("SELECT `subject_code` from `has` where `class_no.`= " + Pack.pack(classNo));

        if (subs!=null)
            for (String sub:subs){ subjects.put(sub,new Subject(sub,classNo,id)); }
    }

    public String getClassNo(){
        return classNo;
    }

    public void setPassword(String newPass) {
        if (password.equals(newPass)) return;
        try{
            updateData("update `student` set `password` = "+Pack.pack(password)+" where `stu_id` = "+Pack.pack(id));
        }catch (Exception se){
            se.printStackTrace();
        }

    }

    public HashMap<String,Subject> getSubjects(){
        return subjects;
    }

    public String getState() {
        return state;
    }

    public Subject getSubject() {
        return subject;
    }

    public String[] getBasicInfo() {
        return basicInfo;
    }

    public String getPassword(){
        return password;
    }
}