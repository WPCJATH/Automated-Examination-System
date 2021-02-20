package polyu.hk.comp2411.gp35.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import static polyu.hk.comp2411.gp35.Model.Connection.*;

/**
 * This class is built for both student sign in and teacher sign in
 * For student sign in:
 *         obtain data in database and store them in a java's way
 *         store info of the exam itself
 *         act as a bridge between Subject and Record,Answer
 */
public class Exam {
    private final String code;// for both
    private String[] basicInfo;//name date start due fullMark // for both
    private final String sub_code;// for both
    private String date;
    private String begin;
    private String due;
    private String name;// for both
    private int fullMark;// for both
    private Record record;// for both
    private final HashMap<Integer,Question> questions = new HashMap<>();// for both
    private HashMap<Integer,Answer> answers = new HashMap<>();// for both
    private String description;
    private String classNo;
    private String student_id = null;

    Exam(String code,String subject_code,String student_id){
        this.code = code;
        this.sub_code = subject_code;
        basicInfo = new String[7];
        //answers = new HashMap<>();
        this.student_id = student_id;
        setInfo(student_id);
    }

    Exam(String code,String subject_code,String student_id,char c){
        this.code = code;
        this.sub_code = subject_code;
        basicInfo = new String[7];
       // answers = new HashMap<>();
        this.student_id = student_id;
        setInfo(student_id,c);
    }



    Exam(String code,String subject_code,char c){
        this.code = code;
        this.sub_code = subject_code;
        basicInfo = new String[7];
        answers = new HashMap<>();
        setInfo(c);
    }

    Exam(String name, String date, String begin, String due, String description,String exam_code,String classNo,String sub_code){
        this.name = name;
        this.date = date;
        this.begin = begin;
        this.due = due;
        this.description = description;
        this.code = exam_code;
        this.classNo = classNo;
        this.sub_code = sub_code;
        //questions = new HashMap<>();
        fullMark = 0;
        basicInfo = new String[7];//name date start due fullMark description class_no.
        basicInfo[0] = name;
        basicInfo[1] = date;
        basicInfo[2] = begin;
        basicInfo[3] = due;
        basicInfo[4] = "";
        basicInfo[5] = description;
    }

    public void questionAdd(int q_number, Question question){
        questions.put(q_number,question);
        fullMark += question.getFullMark();
        basicInfo[4] = String.valueOf(fullMark);
    }

    public void answerAdd(int q_number,Answer answer){
        answers.put(q_number,answer);
    }

    private void setInfo(String student_id){//name date start due fullMark


        String from = "from `exam` where `exam_code` =" + Pack.pack(code);
        set(from);

        try{
            int Q_number = getCount("from `question` where `exam_code` =" + Pack.pack(code));

            for (int i=1;i<=Q_number;i++) {
                questions.put(i, new Question(code, i));
            }
        }catch (Exception se){se.printStackTrace();}

        try{
            int q_number = getCount(" from `answer` where `exam_code` = " + Pack.pack(code) +" and `stu_id`= "+Pack.pack(student_id));
            System.out.println(q_number);
            for (int i=1;i<=q_number;i++) answers.put(i,new Answer(student_id,code,i));
            System.out.println("11");
            if (getData("select `date` from `record` where `exam_code` and `stu_id` = "+Pack.pack(student_id)) != null) record = new Record(student_id,code,sub_code,basicInfo[0]);
        }catch (Exception ignored){}

    }

    private void setInfo(String student_id,char c){//name date start due fullMark

        String from = "from `exam` where `exam_code` =" + Pack.pack(code);
        set(from);

        try{
            int q_number = getCount("from `answer` where `exam_code` =" + Pack.pack(code) +"and `stu_id`= "+Pack.pack(student_id));
            //System.out.println(student_id+q_number);
            if (q_number!= 0)for (int i=1;i<=q_number;i++) answers.put(i,new Answer(student_id,code,i));

            if (getData("select `date` from `record` where `exam_code` and `stu_id` = "+Pack.pack(student_id)) != null) record = new Record(student_id,code,sub_code,basicInfo[0]);
        }catch (Exception ignored){}

    }


    private void setInfo(char c){
        //String staff_id = AESController.dataPool.getid();
        String from = "from `exam` where `exam_code` =" + Pack.pack(code);
        set(from);

        int q_number = getCount("from `question` where `exam_code` =" + Pack.pack(code));
        for (int i=1;i<=q_number;i++) {
            questions.put(i, new Question(code, i));
        }
    }

    private void set(String from){
        basicInfo = getData("select `exam_name`,`date`,`start_time`,`due_time`,`full_mark`,`description`,`class_no.` "+from,7);

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // try { date = simpleDateFormat.parse(getData("select `data` "+from)[0]); } catch (ParseException e) { e.printStackTrace(); }
        assert basicInfo != null;
        fullMark = Integer.parseInt(basicInfo[4]);
        name = basicInfo[0];
        date = basicInfo[1];
        begin = basicInfo[2];
        due = basicInfo[3];
        fullMark = Integer.parseInt(basicInfo[4]);
        description = basicInfo[5];
        classNo = basicInfo[6];
    }
    ;/**
     `exam_code` char(5),
     `exam_name` char(20) default '',
     `subject_code` char(8) not null,
     `date` DATE not null,
     `start_time` TIME not null,
     `due_time` TIME not null,
     `full_mark` int not null default 0,
     `class_no.` char(5) not null,
     `description` TEXT,
     */
    public void updateTo(){
        updateData("insert into `exam` values(" + Pack.pack(new String[]{code,name,sub_code,date,begin,due, String.valueOf(fullMark),classNo,description}) + ")");
        for (int i:questions.keySet()){
            questions.get(i).updateTo();
        }
    }

    /**
     * `stu_id` char(9),
     *   `exam_code` char(5),
     *   `date` char(10) not null,
     *   `letter_grade` char(2),
     * */
    public void UPdateMark(){
        float Mark = gMark();

        float Grade = Mark/getFullMark();
        String Letter;
        if (Grade<40){ Letter = "F"; }
        else if (40<=Grade && Grade<45) Letter = "D-";
        else if (45<=Grade && Grade<50) Letter = "D";
        else if (50<=Grade && Grade<55) Letter = "D+";
        else if (55<=Grade && Grade<60) Letter = "C-";
        else if (60<=Grade && Grade<65) Letter = "C";
        else if (65<=Grade && Grade<70) Letter = "C+";
        else if (70<=Grade && Grade<75) Letter = "B-";
        else if (75<=Grade && Grade<80) Letter = "B";
        else if (80<=Grade && Grade<85) Letter = "B+";
        else if (85<=Grade && Grade<90) Letter = "A-";
        else if (90<=Grade && Grade<95) Letter = "A";
        else  Letter = "A+";

        DateFormat D = new SimpleDateFormat("mm-MM-yy");
        try{
            updateData("insert into `record` values("+Pack.pack(new String[]{student_id,code, D.format(new Date().getTime()),Letter})+")");

        }catch (Exception se){se.printStackTrace();}
    }

    public HashMap<Integer, Answer> getAnswers() {
        return answers;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public String[] getBasicInfo() {
        return basicInfo;
    }

    public Record getRecord() {
        return record;
    }

    public void setFullMark(int fullMark) {
        this.fullMark = fullMark;
    }

    public String getSub_code() {
        return sub_code;
    }

    public String generateMark(){
        float Mark = 0;
        for (int i:answers.keySet()){
            if (answers.get(i).getMark().equals("-1")) return "Not gone over";
            Mark += Integer.parseInt(answers.get(i).getMark());
        }
        return String.valueOf(Mark);
    }

    public Float gMark(){
        float Mark = 0;
        for (int i:answers.keySet()){
            Mark += Integer.parseInt(answers.get(i).getMark());
        }
        return Mark;
    }

    public String getDate() {
        return date;
    }

    public String getBegin() {
        return begin;
    }

    public String getDue() {
        return due;
    }

    public String getName() {
        return name;
    }

    public int getFullMark() {
        return fullMark;
    }

    public String getDescription() {
        return description;
    }

    public String getCode(){
        return code;
    }
}