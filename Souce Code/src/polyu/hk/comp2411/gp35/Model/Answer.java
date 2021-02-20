package polyu.hk.comp2411.gp35.Model;

import java.util.ArrayList;
import java.util.Objects;

import static polyu.hk.comp2411.gp35.Model.Connection.getData;
import static polyu.hk.comp2411.gp35.Model.Connection.updateData;

/**
 * This class is built for both student sign in and teacher sign in
 * For student sign in:
 *         obtain data in database and store them in a java's way
 *         store the info of answer for the student
 */
public class Answer extends Question {
    private String mark; // for both
    private String[] content;// for both
    private String comment;// for both
    private String student_id;
    //private final int q_number;
    //private final String exam_code;
    private String from; // for student


    Answer(String student_id,String exam_code,int q_number){
        super(exam_code,q_number);
        this.student_id = student_id;
        try {
            from = "from `answer` where `exam_code` =" + Pack.pack(exam_code) + " and `stu_id` = " + Pack.pack(student_id) + " and `question_no.` = " + Pack.pack(q_number);
            mark = Objects.requireNonNull(getData("select `mark` " + from))[0];
            content = splitA(Objects.requireNonNull(getData("select `content` " + from))[0]);
            comment = Objects.requireNonNull(getData("select `comment` " + from))[0];
        }catch (Exception se){se.printStackTrace();}
    }

    /**
     * `stu_id` char(9),
     *   `exam_code` char(5),
     *   `question_no.` int,
     *   `content` TEXT,
     *   `mark` int not null default -1,
     *   `comment` TEXT,
     * */
    public Answer(int q_number, String code, String answer,String id){
        super(code,q_number);
        content = splitA(answer);
        mark = CalumMark(content);
        comment = "";
        this.student_id = id;
        updateData("insert into `answer` values("+Pack.pack(new String[]{id,code, String.valueOf(q_number),answer,mark,comment})+")");
    }



    private String CalumMark(String[] answer){
        if (getType()==QuestionType.FILLIN){
            String[] stand_answer = getStandardAnswer();
            int counter = 0;
            for (int i = 0;i<stand_answer.length-1;i++){
                if (stand_answer[i].equals(answer[i])) { counter++; }
            }
            return String.valueOf(getFullMark()*counter/(stand_answer.length-1));

        }else if (getType()==QuestionType.MUTI){
            String[] stand_answer = getStandardAnswer();
            if (stand_answer[stand_answer.length-1].equals("n") || answer[0].equals("n")) return "0";
            if (stand_answer[stand_answer.length-1].equals(answer[0])) return String.valueOf(getFullMark());
            if (stand_answer[stand_answer.length-1].contains(answer[0])) return String.valueOf(getFullMark()/2);
            return "0";
        }else{
            if (answer[0]==null|| answer[0].equals("")) return "0";
        }
        return "-1";
    }

    private String[] splitA(String s) {
        s = "[]" +s;
        ArrayList<String> a  = new ArrayList<>();
        StringBuilder single = new StringBuilder();
        for (int i=0;i<s.length();i++){
            if (s.charAt(i)=='['){
                a.add(single.toString());
                single = new StringBuilder();
                while (i<s.length() && s.charAt(i)!=']'){
                    i++;
                }
            }
            else{
                single.append(s.charAt(i));
            }
        }
        a.add(single.toString());
        a.remove(0);
        return (a.toArray(new String[0]));
    }

    public void setContent(String newCon){
        content = splitA(newCon);
        //updateData("update `answer` set `content` = '"+ newCon + "'" + from);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
        updateData("update `answer` set `mark`= "+Pack.pack(mark)+"where `exam_code` =" + Pack.pack(getCode()) + " and `stu_id` = " + Pack.pack(student_id) + " and `question_no.` = " + Pack.pack(getQ_number()));
    }

    public String[] getAnswer() {
        return content;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
