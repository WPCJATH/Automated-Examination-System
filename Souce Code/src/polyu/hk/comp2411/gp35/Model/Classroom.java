package polyu.hk.comp2411.gp35.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Classroom {
    private final String NO;
    private Exam exam;
    private final HashMap<String,Exam> examOfStu;// id Exam
    private final HashMap<String,String> studentNames;// id name
    private final String subject_code;

    Classroom(String class_no,String subject_code){
        NO = class_no;
        this.subject_code = subject_code;
        examOfStu = new HashMap<>();
        studentNames = new HashMap<>();
        setInfo(class_no);
    }

    private void setInfo(String classNo){
        String[] students = Connection.getData("select `stu_id` from `student` where `class_no.` = "+Pack.pack(classNo));
        if (students == null) return;

        for (String id:students){
            String name = Objects.requireNonNull(Connection.getData("select `name` from `student` where `class_no.`= " + Pack.pack(classNo) + " and `stu_id`= " + Pack.pack(id)))[0];
            studentNames.put(id,name);
        }
        String exam_code = null;
        try{
            exam_code = Objects.requireNonNull(Connection.getData("select `exam_code` from `exam` where `subject_code` = " + Pack.pack(subject_code) + " and `class_no.` = " + Pack.pack(NO)))[0];
            exam = new Exam(exam_code,subject_code,'1');

        }catch (Exception ignored){}

        try{
            if (exam_code!=null)
                for (String student: studentNames.keySet()) examOfStu.put(student, new Exam(exam_code, subject_code,student,'1'));
        }catch (Exception ignored){}
    }

    public String getNO() {
        return NO;
    }

    public HashMap<String,Exam> getExamOfStu(){
        return examOfStu;
    }

    public HashMap<String, String> getStudentNames() {
        return studentNames;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam){
        if (this.exam!=null) throw new IllegalArgumentException("exam exists");
        this.exam = exam;
    }
}