package polyu.hk.comp2411.gp35.Model;

import static polyu.hk.comp2411.gp35.Model.Connection.getData;

public class Record {
    String[] basicInfo;// date,letter_grade // for student
    String date;// for student
    String letter_grade;// for both
    //int grade;
    String title;// for student
    String condition;// for student

    Record(String id,String exam_code,String subject_code,String examName){
        String from = "from `record` where `exam_code` =" + exam_code + " and `stu_id` = " + Pack.pack(id);
        basicInfo = getData("select `date`,`letter_grade`"+from,2);
        assert basicInfo != null;

        this.date = basicInfo[0];
        this.letter_grade = basicInfo[1];
        this.title = subject_code + ' ' + examName;
    }

    public String getTitle(){
        return title;
    }

    public String getLetter_grade(){
        return letter_grade;
    }
}
