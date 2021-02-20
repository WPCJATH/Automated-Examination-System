package polyu.hk.comp2411.gp35.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

import polyu.hk.comp2411.gp35.Model.Exam;
import polyu.hk.comp2411.gp35.Model.Question;
import polyu.hk.comp2411.gp35.Model.QuestionType;
import polyu.hk.comp2411.gp35.Model.designer;
import polyu.hk.comp2411.gp35.View.ExamPage.*;
import polyu.hk.comp2411.gp35.View.examEditor.qPage;

public class ViewCentre {
    static MainWindow window;
    public static int i=1;
    static JPanel mainPanel;
    static CardLayout card;
    static String CurPage = "a";
    static Stack back;
    static Stack go;
    static ArrayList<qPage> qs;
    //static ArrayList<qPageS> as;
    static designer curDesigner;
    static Exam CurExam;
    static longPage CurLong = null;

    public static void Open() {
        window = new MainWindow();
        Box b = Box.createVerticalBox();

        card = new CardLayout();
        mainPanel = new JPanel(card);

        back = new Stack();
        go = new Stack();


        mainPanel.add(new NormalPage(),"a");

        card.show(mainPanel,"a");

        b.add(new Modules.StateBar());
        b.add(mainPanel);

        window.add(b);

        qs = new ArrayList<>();
    }

    public static void setInfoPage(String subject_code, String tName, designer de){
        InfoPage info = new InfoPage(subject_code,tName,de);
        String in = String.valueOf(new Date().getTime());
        mainPanel.add(info,in);
        card.show(mainPanel,in);

        back.push(CurPage);
        CurPage = in;
        curDesigner = de;
    }

    public static void setInfoPage(Exam exam,char code,String id){
        String in = String.valueOf(new Date().getTime());
        InfoPage info = new InfoPage(exam,code,id);
        mainPanel.add(info,in);
        card.show(mainPanel,in);

        back.push(CurPage);
        CurPage = in;
    }
    //for stu read
    public static void setLongPage(Exam exam){
        String in = String.valueOf(new Date().getTime());
        ExamPage.longPage longP = new longPage(exam);
        mainPanel.add(longP,in);
        card.show(mainPanel,in);

        back.push(CurPage);
        CurPage = in;
    }
    // for teacher read
    public static void setLongPage(Exam exam,char code){
        String in = String.valueOf(new Date().getTime());
        ExamPage.longPage longP = new longPage(exam,code);
        mainPanel.add(longP,in);
        card.show(mainPanel,in);

        back.push(CurPage);
        CurPage = in;
    }
    // for teacher check
    public static void setLongPage(Exam exam,int code){
        String in = String.valueOf(new Date().getTime());
        ExamPage.longPage longP = new longPage(exam,code);
        CurLong = longP;
        mainPanel.add(longP,in);
        card.show(mainPanel,in);

        back.push(CurPage);
        CurPage = in;
    }

    public static void Back(){
        card.show(mainPanel, "a");
        CurPage = "a";
        go.clear();
        back.clear();
    }

    public static void ExamGo(){
        back.push(CurPage);
        CurPage = String.valueOf(go.pop());
        card.show(mainPanel,CurPage);
    }

    public static void ExamBack(){
        go.push(CurPage);
        CurPage = String.valueOf(back.pop());
        card.show(mainPanel,CurPage);
    }

    public static Boolean canGo(){
        return !go.empty();
    }

    public static void addNewQuestion(QuestionType type,boolean flag){
        examEditor ed = new examEditor(String.valueOf(i),type,flag);
        mainPanel.add(ed,String.valueOf(i));

        card.show(mainPanel,String.valueOf(i));

        back.push(CurPage);
        CurPage = String.valueOf(i);
        i++;


    }

    public static void ExamOpen(Exam exam,String id){
        CurExam = exam;
        HashMap<Integer,Question> qsForStu = exam.getQuestions();
        back.push(CurPage);
        i = 1;
        CurPage = String.valueOf(i);
        for (int i=qsForStu.size();i>=2;i--) go.push(i);
        for (int i=1;i<=qsForStu.size();i++) mainPanel.add(new examEditor(qsForStu.get(i), id),String.valueOf(i));

        card.show(mainPanel,CurPage);
    }

    public static void sSubmit(String id){
        i = 1;
        for (qPage q:qs){
            q.generator(CurExam,id);
        }
        mainPanel.add(new NormalPage(),"a");
        card.show(mainPanel,"a");
        back.clear();
        go.clear();
        CurPage = "a";
    }

    public static void tSubmit(){
        i = 1;
        Exam newExam = curDesigner.newExam();
        for (qPage q:qs){
            q.generator(newExam);
        }
        newExam.updateTo();
        mainPanel.add(new NormalPage(),"a");
        card.show(mainPanel,"a");
        back.clear();
        go.clear();
        CurPage = "a";
    }

    public static void gradeSub(){
        i = 1;

        CurLong.update(CurExam);

        mainPanel.add(new NormalPage(),"a");
        card.show(mainPanel,"a");
        back.clear();
        go.clear();
        CurPage = "a";
    }

    public static void qsAdd(qPage q){
        qs.add(q);
    }
}
