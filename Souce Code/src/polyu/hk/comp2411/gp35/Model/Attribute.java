package polyu.hk.comp2411.gp35.Model;

public enum Attribute {
    STAFFID("`staff_id`"),
    NAME("`name`"),
    PASSWORD("`password`"),
    EMAIL("`email`"),
    DEPART("`depart`"),
    SUBJECTCODE("`subject_code`"),
    TOPIC("`topic`"),
    CLASSNO("`class_no.`"),
    STATE("staff_id"),
    EXAMCODE("`exam_code`"),
    EXAMNAME("`exam_name`"),
    DATE("`date`"),
    STARTTIME("`start_time`"),
    DUETIME("`due_time`"),
    FULLMARK("`full_mark`"),
    QUESTIONNO("`question_no.`"),
    STANDANSWER("`stand_answer`"),
    FLAG("`flag`"),
    TEXT("`text`"),
    AUDIO("`auido`"),
    GRAPHIC("`grahic`"),
    CONTENT("`content`"),
    MARK("`mark`"),
    COMMENT("`comment`"),
    LETTER("`letter_grade`");

    private final String  s;

    Attribute(String s){ this.s = s; }

    public String getValue(){return s;}
}
