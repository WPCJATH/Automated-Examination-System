package polyu.hk.comp2411.gp35.Model;

import java.util.ArrayList;
import java.util.Objects;

import static polyu.hk.comp2411.gp35.Model.Connection.*;

/**
 * This class is built for both student sign in and teacher sign in
 * For student sign in:
 *         obtain data in database and store them in a java's way
 *         act as Answer's superclass, so that the student can access both answers and questions but only use 1 class instead of 2 to store them
 */
public class Question {
    private String content;// for both
    private QuestionType type;// for both
    private Boolean isCompulsory = true;
    private String Graphic="";// for both
    private String Audio="";// for both
    private String[] StandardAnswer;// for teacher
    private int fullMark;// for both
    private  int q_number;// for teacher
    private String code;

    Question(String exam_code,int q_number){
        try{
            String from = "from `question` where `exam_code` = " + Pack.pack(exam_code) + " and `question_no.` = " + q_number;
            code = exam_code;
            this.q_number = q_number;
            //System.out.println(q_number);
            try{
                content = Objects.requireNonNull(getData("select `text`" + from))[0];
            }catch (Exception e){e.printStackTrace();}
            try {
                StandardAnswer = SplitA(Objects.requireNonNull(getData("select `stand_answer`" + from))[0]);
            } catch (Exception ignored) {
                System.out.println("no answer");
            }
            try {
                fullMark = toInt(Objects.requireNonNull(getData("select `full_mark`" + from))[0]);
            } catch (Exception ignored) {
                System.out.println("full mark");
            }
            try {
                Graphic = getPic("select `graphic`" + from);
            } catch (Exception ignored) {
                System.out.println("no graphic");
            }
            try {
                Audio = getPic("select `audio`" + from);
            } catch (Exception ignored) {
                System.out.println("no audio");
            }
            type = getT(Objects.requireNonNull(getData("select `type`" + from))[0]);
            isCompulsory = getF(Objects.requireNonNull(getData("select `flag`" + from))[0]);
        }catch (Exception se){se.printStackTrace();}
        //System.out.println("111");
    }

    public Question(int q_number, String code, String content, String stand_answer, QuestionType type, boolean flag, int fullMark){
        this.q_number = q_number;
        this.code = code;
        this.content = content;
        this.StandardAnswer = SplitA(stand_answer);
        this.type = type;
        this.isCompulsory = flag;
        this.fullMark = fullMark;
    }

    private QuestionType getT(String s){
        return switch (s) {
            case "Multiple-choices" -> QuestionType.MUTI;
            case "Fill in blank" -> QuestionType.FILLIN;
            case "Standard full-length" -> QuestionType.STANDARD;
            default -> throw new IllegalArgumentException();
        };
    }

    public static String[] SplitA(String s){
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

    private Boolean getF(String flag){
        if (flag.equals("Compulsory")) return true;
        else if (flag.equals("Optional")) return false;
        else throw new IllegalArgumentException();
    }

    private int toInt(String mark){
        try {
            return Integer.parseInt(mark);
        }catch (Exception se){ se.printStackTrace();}
        return -1;
    }

    /**
     *  `exam_code` char(5),
     *  `question_no.` int,
     *  `stand_answer` TEXT,
     *  `full_mark` int not null default 0,
     *  `flag` enum('Compulsory','Optional') not null default 'Compulsory',
     *  `type` enum('Multiple-choices','Fill in blank','Standard full-length') not null default 'Multiple-choices',
     *  `text` TEXT not null,
     *  `audio` LONGBLOB,
     *  `graphic` LONGBLOB,
     */
    public void updateTo(){
        updateData("insert into `question` values(" + Pack.pack(new String[]{code, String.valueOf(q_number), CombineSA(), String.valueOf(fullMark),(isCompulsory)?"Compulsory":"Optional", type.getType(),content,Audio,Graphic}) + ")");

    }

    public String getContent() {
        return content;
    }

    public QuestionType getType() {
        return type;
    }

    public String getGraphic() {
        return Graphic;
    }

    public String getAudio() {
        return Audio;
    }

    public String[] getStandardAnswer() {
        return StandardAnswer;
    }

    public String CombineSA(){
        if (StandardAnswer.length==0) return "";
        StringBuilder sb = new StringBuilder(StandardAnswer[0]);
        for (int i=1;i<StandardAnswer.length;i++) {
            sb.append("[]");
            sb.append(StandardAnswer[i]);
        }
        return sb.toString();
    }

    public int getFullMark() {
        return fullMark;
    }

    public Boolean getCompulsory() {
        return isCompulsory;
    }

    public String getCode() {
        return code;
    }

    public int getQ_number() {
        return q_number;
    }
}
