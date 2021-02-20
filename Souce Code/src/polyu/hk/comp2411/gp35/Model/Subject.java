package polyu.hk.comp2411.gp35.Model;

import java.util.HashMap;
import java.util.Objects;

/**
 * This class is built for both student sign in and teacher sign in
 * For student sign in:
 *         obtain data in database and store them in a java's way
 *         store the info of subject for the student
 *         act as a bridge between Student and Exam,Answer,Record
 */
public class Subject {
    private final String code;//for both
    private final String topic;// for both
    private Exam exam = null;//flag to ensure whether there is a exam of the subject of the class // for both
    private String TeacherName;// for student
    private HashMap<String,Classroom>  classes;// for teacher
    private String staff_id;

    Subject(String subject_code,String classNo,String studen_id){
        this.code = subject_code;
        topic = Objects.requireNonNull(Connection.getData("SELECT `topic` from `subject` where `subject_code`=" + Pack.pack(code)))[0];
        setInfo(classNo,studen_id);
    }

    Subject(String subject_code,String staff_id,char c){
        this.code = subject_code;
        this.staff_id = staff_id;
        classes = new HashMap<>();
        topic = Objects.requireNonNull(Connection.getData("SELECT `topic` from `subject` where `subject_code`=" + Pack.pack(code)))[0];
        setInfo(staff_id,c);
    }

    private void setInfo(String classNo,String student_id) {
        String exam_code = "";
        try {
            TeacherName = Objects.requireNonNull(Connection.getData("SELECT `staff_id` from `has` where `subject_code`=" + Pack.pack(code) + " and `class_no.` = " + Pack.pack(classNo)))[0];
            TeacherName = Objects.requireNonNull(Connection.getData("SELECT `name` from `teacher` where `staff_id`=" + Pack.pack(TeacherName) ))[0];
        }catch (Exception se){se.printStackTrace(); }

        try {
            exam_code = Objects.requireNonNull(Connection.getData("SELECT `exam_code` from `exam` where `subject_code`=" + Pack.pack(code) + " and `class_no.` = " + Pack.pack(classNo)))[0];
            exam = new Exam(exam_code,code,student_id);

        }catch (Exception ignored){}
    }

    private void setInfo(String staff_id,char c){
        String[] classrooms = Connection.getData("SELECT `class_no.` from `has` where `subject_code`= " + Pack.pack(code)+" and `staff_id` ="+Pack.pack(staff_id));

        if (classrooms!=null)
            for (String classroom:classrooms){ classes.put(classroom,new Classroom(classroom,code)); }
    }

    public String getTeacherName(){ return TeacherName;}

    public String getCode() {
        return code;
    }

    public String getTopic() {
        return topic;
    }

    public Exam getExam() { return exam; }


    public HashMap<String, Classroom> getClasses() {
        return classes;
    }

    public String getStaff_id() {
        return staff_id;
    }
}
