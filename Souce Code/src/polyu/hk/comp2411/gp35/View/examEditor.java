package polyu.hk.comp2411.gp35.View;

import polyu.hk.comp2411.gp35.Model.Answer;
import polyu.hk.comp2411.gp35.Model.Exam;
import polyu.hk.comp2411.gp35.Model.Question;
import polyu.hk.comp2411.gp35.Model.QuestionType;

import javax.swing.*;
import java.awt.*;

import static polyu.hk.comp2411.gp35.Model.QuestionType.*;

public class examEditor extends JPanel {
    //for students answer questions
    examEditor(Question CurQs,String id){
        super();
        setPreferredSize(new Dimension(960,600));
        //QuestionType type = askType();
        //boolean flag = askFlag();

        Box Page = Box.createVerticalBox();


        //Page.add(new ExamPage.QuestionsPage("1",type));
        //Page.add(new Panel(920,20));
        qPage q = new qPage(CurQs);
        Page.add(q);
        Page.add(new Panel(920,20));//for add new questions

        Page.add(new ExamPage.BottomBar(id));
        JScrollPane scrollPage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPage.getVerticalScrollBar().setUnitIncrement(15);
        scrollPage.setPreferredSize(new Dimension(940,550));
        scrollPage.setViewportView(Page);


        add(scrollPage);
        ViewCentre.qsAdd(q);

    }


    // for teacher setting questions
    examEditor(String q_number,QuestionType type,Boolean flag){
        super();
        setPreferredSize(new Dimension(960,600));
        //QuestionType type = askType();
        //boolean flag = askFlag();

        Box Page = Box.createVerticalBox();

        //Page.add(new ExamPage.QuestionsPage("1",type));
        //Page.add(new Panel(920,20));
        qPage q = new qPage(type,q_number,flag);
        Page.add(q);
        Page.add(new Panel(920,20));//for add new questions

        Page.add(new ExamPage.BottomBar('1'));
        JScrollPane scrollPage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPage.getVerticalScrollBar().setUnitIncrement(15);
        scrollPage.setPreferredSize(new Dimension(940,550));
        scrollPage.setViewportView(Page);


        add(scrollPage);
        ViewCentre.qsAdd(q);

    }


    public static QuestionType askType() {
        JComboBox cmb = new JComboBox();
        cmb.addItem(MUTI.getType());
        cmb.addItem(FILLIN.getType());
        cmb.addItem(STANDARD.getType());

        JOptionPane.showMessageDialog(null, cmb, "Select the question type.", JOptionPane.PLAIN_MESSAGE);
        return switch (String.valueOf(cmb.getSelectedItem())) {
            case "Multiple-choices" -> MUTI;////'Multiple-choices','Fill in blank','Standard full-length'
            case "fFill in blank" -> FILLIN;
            case "Standard full-length" -> STANDARD;
            default -> throw new IllegalStateException("Unexpected value: " + cmb.getSelectedItem());
        };

    }

    public static Boolean askFlag() {
        JComboBox cmb = new JComboBox();
        cmb.addItem("Compulsory");
        cmb.addItem("Optional");

        JOptionPane.showMessageDialog(null,cmb,"Select the question type.",JOptionPane.PLAIN_MESSAGE);
        return switch (String.valueOf(cmb.getSelectedItem())) {
            case "Compulsory" -> true;
            case "Optional" -> false;
            default -> throw new IllegalStateException("Unexpected value: " + cmb.getSelectedItem());
        };
    }

    class qPage extends JPanel{
        private Label[] options;
        private JTextField[] texts;
        private final JComboBox Mark;
        private final JTextArea question;
        private final QuestionType type;
        private final String q_number;
        private boolean flag;
        //private final JTextField AnswerZone;
        private JCheckBox[] CB;
        private JTextArea answer;

        qPage(QuestionType type, String q_number, Boolean f){
            super(new FlowLayout());
            String flag = (f)? "Compulsory":"Optional";
            this.type = type;
            this.q_number = q_number;
            this.flag = f;

            setBackground(Color.decode("#E0FFFF"));
            setPreferredSize(new Dimension(860,400));
            Mark = new JComboBox();
            for (int i=0;i<=20;i++){ Mark.addItem(i); }

            Label label1 = new Label("Question "+q_number+" (",100,20);
            Label label11= new Label(" Points) "+flag,710,20);
            label1.setFont(new Font("",Font.BOLD,15));
            add(label1);
            add(Mark);
            add(label11);
            add(new Label("",860,5));


            Label label2 = new Label("",860,20);
            add(label2);

            add(new Label("",860,5));

            question = new JTextArea();
            question.setFont(new Font("", Font.PLAIN,15));
            question.setForeground(Color.BLACK);
            question.setPreferredSize(new Dimension(860,60));
            question.setLineWrap(true);
            add(question);
            add(new Label("",860,10));

            //add(new Panel(860,400));//for pic
            add(new Label("",860,10));

            JPanel chooseZone = new JPanel();
            chooseZone.setBackground(Color.WHITE);
            //AnswerZone = new JTextField();

            if (type.equals(QuestionType.MUTI)){
                chooseZone.setPreferredSize(new Dimension(850,35*6+10));
                label2.setText("Muti-choice Question");
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                texts = new JTextField[6];
                CB = new JCheckBox[6];
                for (int i=0;i<=5;i++){
                    texts[i] = new JTextField("");
                    CB[i] = new JCheckBox();
                    addOption(texts[i],abcs[i],chooseZone,CB[i]);
                }
            }
            else if (type.equals(QuestionType.FILLIN)){
                chooseZone.setPreferredSize(new Dimension(830,35*6+10));
                label2.setText("Fill-in Blank Question");
                texts = new JTextField[6];
                for (int i=0;i<=5;i++){
                    texts[i] = new JTextField();
                    addBlanks(chooseZone,i+1,texts[i]);
                }
            }
            else {
                label2.setText("Standard full-length Question");
                chooseZone.setPreferredSize(new Dimension(860,0));
                //JTextArea answer = new JTextArea();
                //JScrollPane forAnswer = new JScrollPane(answer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                //forAnswer.setPreferredSize(new Dimension(800,120));
                //answer.setFont(new Font("", Font.PLAIN,15));
                // answer.setEditable(true);
                //answer.setLineWrap(true);
                //chooseZone.add(new Label("Enter your answer here: ",800,15));
                //chooseZone.add(forAnswer);

            }
            add(chooseZone);
        }

        // for students make answers
        qPage(Question QsForStu){
            super(new FlowLayout());
            String flag = (QsForStu.getCompulsory())? "Compulsory":"Optional";
            this.type = QsForStu.getType();
            this.q_number = String.valueOf(QsForStu.getQ_number());
            this.flag = QsForStu.getCompulsory();

            setBackground(Color.decode("#E0FFFF"));
            setPreferredSize(new Dimension(860,400));
            Mark = new JComboBox();
            for (int i=0;i<=20;i++){ Mark.addItem(i); }

            Label label1 = new Label("Question "+q_number+" ("+QsForStu.getFullMark()+" Points) "+flag,860,20);
            label1.setFont(new Font("",Font.BOLD,15));
            add(label1);
            add(new Label("",860,5));


            Label label2 = new Label("",860,20);
            add(label2);

            add(new Label("",860,5));

            question = new JTextArea(QsForStu.getContent());
            question.setFont(new Font("", Font.PLAIN,15));
            question.setForeground(Color.BLACK);
            question.setPreferredSize(new Dimension(860,60));
            question.setLineWrap(true);
            question.setEditable(false);
            add(question);
            add(new Label("",860,10));

            //add(new Panel(860,400));//for pic
            add(new Label("",860,10));

            JPanel chooseZone = new JPanel();
            chooseZone.setBackground(Color.WHITE);
            //AnswerZone = new JTextField();

            if (type.equals(QuestionType.MUTI)){
                String[] ops = QsForStu.getStandardAnswer();
                chooseZone.setPreferredSize(new Dimension(850,30*ops.length+10));
                label2.setText("Muti-choice Question");
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                options = new Label[ops.length-1];
                CB = new JCheckBox[6];
                for (int i=0;i<ops.length-1;i++){
                    options[i] = new Label(ops[i],760,20);
                    CB[i] = new JCheckBox();
                    addOption(options[i],abcs[i],chooseZone,CB[i]);
                }
            }
            else if (type.equals(QuestionType.FILLIN)){
                String[] ops = QsForStu.getStandardAnswer();
                chooseZone.setPreferredSize(new Dimension(830,30*ops.length+10));
                label2.setText("Fill-in Blank Question");
                texts = new JTextField[6];
                for (int i=0;i<ops.length-1;i++){
                    texts[i] = new JTextField("");
                    addBlanks(chooseZone,i+1,texts[i]);
                }
            }
            else {
                label2.setText("Standard full-length Question");
                chooseZone.setPreferredSize(new Dimension(860,150));
                answer = new JTextArea("");
                JScrollPane forAnswer = new JScrollPane(answer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                forAnswer.setPreferredSize(new Dimension(800,120));
                answer.setFont(new Font("", Font.PLAIN,15));
                answer.setEditable(true);
                answer.setLineWrap(true);
                chooseZone.add(new Label("Enter your answer here: ",800,15));
                chooseZone.add(forAnswer);

            }
            add(chooseZone);
        }

        public void generator(Exam exam,String id){
            String stand_answer;
            if (type.equals(STANDARD)){
                stand_answer = answer.getText();
            }else if (type.equals(MUTI)){
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                StringBuilder sb = new StringBuilder();
                boolean flag = true;
                for (int i=0;i<=5;i++){
                    if (CB[i].isSelected()){
                        flag = false;
                        sb.append(abcs[i]);
                    }
                }
                if (flag) sb.append('n');
                stand_answer = sb.toString();
            }else if (type.equals(FILLIN)){
                StringBuilder sb = new StringBuilder();
                for (int i=0;i<texts.length;i++){
                    if(i!= texts.length-1) {
                        if (texts[i] == null) sb.append("").append("[]");
                        else sb.append(texts[i].getText()).append("[]");
                    }
                    else{
                        if (texts[i] == null) sb.append("");
                        else sb.append(texts[i].getText());
                    }
                }
                stand_answer = sb.toString();
            }else throw new IllegalArgumentException();//int q_number, String code, String answer
            Answer a = new Answer(Integer.parseInt(q_number),exam.getCode(),stand_answer, id);
            //Question q = new Question(Integer.parseInt(q_number),exam.getCode(),question.getText(),stand_answer,type,flag,Integer.parseInt(String.valueOf(Mark.getSelectedItem())));
            exam.answerAdd(Integer.parseInt(q_number),a);
        }

        public void generator(Exam exam){
            String stand_answer;
            if (type.equals(STANDARD)){
                stand_answer = "";
            }else if (type.equals(MUTI)){
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                StringBuilder sb = new StringBuilder();
                for (int i=0;i<=5;i++){
                    if (!texts[i].getText().equals(""))
                        sb.append(texts[i].getText()).append("[]");
                }
                boolean flag = true;
                int s = 0;
                for (int i=0;i<=5;i++){
                    if (CB[i].isSelected() && !texts[i].getText().equals("")){
                        flag = false;
                        sb.append(abcs[s]);
                        s++;
                    }
                }
                if (flag) sb.append('n');
                stand_answer = sb.toString();
            }else if (type.equals(FILLIN)){
                StringBuilder sb = new StringBuilder();
                for (int i=0;i<=5;i++){
                    if (!texts[i].getText().equals("")) {
                        if (i!=5) {
                            sb.append(texts[i].getText()).append("[]");
                        }
                    }
                }
                stand_answer = sb.toString();
            }else throw new IllegalArgumentException();
            //System.out.println(stand_answer);
        //int q_number,String code,String content,String stand_answer,QuestionType type,boolean flag,int fullMark
            Question q = new Question(Integer.parseInt(q_number),exam.getCode(),question.getText(),stand_answer,type,flag,Integer.parseInt(String.valueOf(Mark.getSelectedItem())));
            exam.questionAdd(Integer.parseInt(q_number),q);
        }

        private void addOption(JTextField t,char op,JPanel chooseZone,JCheckBox option){
            t.setPreferredSize(new Dimension(760,20));
            Panel item = new Panel(840,30);
            item.setBackground(Color.decode("#F0FFFF"));
            option.setText(op+"");
            option.setBackground(Color.decode("#F0FFFF"));
            item.add(option);
            item.add(t);
            chooseZone.add(item);
        }

        private void addOption(Label t,char op,JPanel chooseZone,JCheckBox option){
            t.setPreferredSize(new Dimension(760,20));
            Panel item = new Panel(840,30);
            item.setBackground(Color.decode("#F0FFFF"));
            option.setText(op+"");
            option.setBackground(Color.decode("#F0FFFF"));
            item.add(option);
            item.add(t);
            chooseZone.add(item);
        }

        private void addBlanks(JPanel chooseZone, int op,JTextField t){
            Panel item = new Panel(820,30);
            item.setBackground(Color.decode("#F0FFFF"));
            Label Blank = new Label("Blank "+op+": ",60,20);
            Blank.setBackground(Color.decode("#F0FFFF"));
            item.add(Blank);
            t.setBackground(Color.white);
            t.setPreferredSize(new Dimension(720,20));
            item.add(t);
            chooseZone.add(item);
        }
    }

}

