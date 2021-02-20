package polyu.hk.comp2411.gp35.Model;

public enum  QuestionType {
    MUTI("Multiple-choices"),
    FILLIN("Fill in blank"),
    STANDARD("Standard full-length");
    //'Multiple-choices','Fill in blank','Standard full-length'
    private final String s;
    QuestionType(String s){this.s = s;}

    public String getType(){return s;}
}
