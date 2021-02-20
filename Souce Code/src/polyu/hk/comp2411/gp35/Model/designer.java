package polyu.hk.comp2411.gp35.Model;

import java.util.HashMap;
import java.util.Random;

import static polyu.hk.comp2411.gp35.Model.Connection.getData;

public class designer {
    String exam_code;
    HashMap<Integer,Answer> answers;
    HashMap<Integer, Question> questions;
    Exam curExam;
    private Subject subject;
    private String setter;
    private String date;
    private String begin;
    private String due;
    private String name;
    private String getSub_code;
    private String classNo;
    private String description;

    public designer(Subject subject, String classNo){
        this.subject = subject;
        this.classNo = classNo;
        exam_code = randomCode();


    }

    public void setter(String name, String date, String begin, String due, String description){
        //System.out.println("123");
        this.name = name;
        this.date = date;
        this.begin = begin;
        this.due = due;
        this.description = description;
    }

    public Exam newExam(){
        Exam exam = new Exam(name,date,begin,due,description,exam_code,classNo,subject.getCode());
        subject.getClasses().get(classNo).setExam(exam);
        return exam;
    }

    public static String randomCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=0 ; i<5 ;  i++) {
           int ran1 = r.nextInt(10);
           code.append(ran1);
        }
        if (getData("Select `exam_code` from `exam` where `exam_code` = "+ Pack.pack(code.toString()))!=null){
            return randomCode();
        }
        return code.toString();
    }
}
