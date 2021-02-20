package polyu.hk.comp2411.gp35.View;

import polyu.hk.comp2411.gp35.Model.*;
import static polyu.hk.comp2411.gp35.Model.QuestionType.*;
import polyu.hk.comp2411.gp35.Model.QuestionType;

import javax.naming.TimeLimitExceededException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static polyu.hk.comp2411.gp35.Model.QuestionType.*;
import static polyu.hk.comp2411.gp35.View.examEditor.askFlag;
import static polyu.hk.comp2411.gp35.View.examEditor.askType;

/**
 * A class group, contains all classes related to showing the exam paper.
 */
public class ExamPage {

    /**
     * Panel for the exam result review page (parts other than state bar).
     */
    static class longPage extends JPanel{

        // teacher read
        longPage(Exam exam){
            super();
            setPreferredSize(new Dimension(960,600));

            HashMap<Integer,Question> qs = exam.getQuestions();

            Box Page = Box.createVerticalBox();

            for (int i=1;i<=qs.size();i++){
                Page.add(new QuestionsPage(String.valueOf(i),qs.get(i)));
                Page.add(new Panel(920,20));
            }
            Page.add(new BottomBar());
            JScrollPane scrollPage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPage.getVerticalScrollBar().setUnitIncrement(15);
            scrollPage.setPreferredSize(new Dimension(940,550));
            scrollPage.setViewportView(Page);

            add(scrollPage);
        }

        // student read
        longPage(Exam exam,char c){
            super();
            setPreferredSize(new Dimension(960,600));

            HashMap<Integer,Answer> as = exam.getAnswers();

            Box Page = Box.createVerticalBox();

            for (int i=1;i<=as.size();i++){
                Page.add(new QuestionsPage(String.valueOf(i),as.get(i)));
                Page.add(new Panel(920,20));
            }
            Page.add(new BottomBar());
            JScrollPane scrollPage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPage.getVerticalScrollBar().setUnitIncrement(15);
            scrollPage.setPreferredSize(new Dimension(940,550));
            scrollPage.setViewportView(Page);

            add(scrollPage);
        }




        private HashMap<Integer,JComboBox> cmbs = new HashMap<>();
        private HashMap<Integer,Answer> ans = new HashMap<>();
        //teacher check
        longPage(Exam exam,int c){
            super();
            setPreferredSize(new Dimension(960,600));

            HashMap<Integer,Answer> as = exam.getAnswers();
            ans = as;

            Box Page = Box.createVerticalBox();

            for (int i=1;i<=as.size();i++){

                Page.add(new QuestionsPage(String.valueOf(i),as.get(i),c));
                Panel p = new Panel(920,30);

                if (as.get(i).getType()== STANDARD && as.get(i).getMark().equals("-1")){
                    JComboBox cmb = new JComboBox();
                    cmbs.put(i,cmb);
                    for (int j=0;j<=as.get(i).getFullMark();j++){
                        cmb.addItem(j);
                    }
                    p.add(new Label("Grade: ",50,20));
                    p.add(cmb);
                    cmb.setPreferredSize(new Dimension(60,20));
                    p.add(new Label("/"+as.get(i).getFullMark(),700,20));
                }
                Page.add(p);
            }
            Page.add(new BottomBar(c));
            JScrollPane scrollPage = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPage.getVerticalScrollBar().setUnitIncrement(15);
            scrollPage.setPreferredSize(new Dimension(940,550));
            scrollPage.setViewportView(Page);

            add(scrollPage);
        }

        public void update(Exam exam){
            for (int i:cmbs.keySet()){
                ans.get(i).setMark(String.valueOf(cmbs.get(i).getSelectedItem()));
            }
            exam.UPdateMark();
        }

    }


     static class QuestionsPage extends JPanel {

        // student read
        QuestionsPage(String No, Answer answer){
            super(new FlowLayout());

            QuestionType type = answer.getType();
            String flag = (answer.getCompulsory())? "Compulsory":"Optional";

            String Mark = answer.getMark();
            int FullMark = answer.getFullMark();
            String content = answer.getContent();

            setBackground(Color.decode("#E0FFFF"));
            Label label1 = new Label("Question "+No+" ("+FullMark+" Points) "+flag,860,20);
            label1.setFont(new Font("",Font.BOLD,15));
            add(label1);
            add(new Label("",860,5));
            Label label2 = new Label("",860,20);
            add(label2);
            add(new Label("",860,5));
            JTextArea question = new JTextArea(content);
            question.setFont(new Font("", Font.PLAIN,15));
            question.setEditable(false);
            question.setForeground(Color.BLACK);
            question.setPreferredSize(new Dimension(860,60));
            add(question);
            add(new Label("",860,10));

           // add(new Panel(860,400));//for pic
            //add(new Label("",860,10));

            JPanel chooseZone = new JPanel();
            chooseZone.setBackground(Color.WHITE);


            if (type.equals(MUTI)){
                String[] a = answer.getStandardAnswer();
                String[] b = answer.getAnswer();
                chooseZone.setPreferredSize(new Dimension(860,50*(a.length-1)+40));
                setPreferredSize(new Dimension(860,250+50*(a.length-1)+40));
                label2.setText(MUTI.name());
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                for (int i=0;i<a.length-1;i++){
                    addOption(chooseZone,a[i],abcs[i]);
                }
                Label ans = new Label("Standard answer:"+a[a.length-1],840,30);
                Label an = new Label("Your answer:"+b[0],840,30);
                chooseZone.add(ans);
                chooseZone.add(an);
            }
            else if (type.equals(FILLIN)){
                String[] a = answer.getStandardAnswer();
                String[] b = answer.getAnswer();
                chooseZone.setPreferredSize(new Dimension(860,120*(a.length-1)+10));
                setPreferredSize(new Dimension(860,250+120*(a.length-1)+10));
                label2.setText(FILLIN.name());
                chooseZone.add(new Label("Standard answer:",840,30));
                for (int i=0;i<a.length-1;i++){
                    addBlanks(chooseZone,a[i],i+1);
                }
                chooseZone.add(new Label("Your answer:",840,30));
                for (int i=0;i<a.length-1;i++){
                    addBlanks(chooseZone,b[i],i+1);
                }

            }
            else {
                label2.setText("Standard full-length ");
                setPreferredSize(new Dimension(860,400));
                chooseZone.setPreferredSize(new Dimension(860,150));
                JTextArea ans = new JTextArea(answer.getAnswer()[0]);
                JScrollPane forAnswer = new JScrollPane(ans,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                forAnswer.setPreferredSize(new Dimension(800,120));
                ans.setFont(new Font("", Font.PLAIN,15));
                ans.setEditable(false);
                ans.setLineWrap(true);
                chooseZone.add(new Label("Your answer is:",800,15));
                chooseZone.add(forAnswer);

            }
            add(chooseZone);
            add(new Label("Grade: "+(answer.getMark().equals("-1")?"Ungraded":answer.getMark())+'/'+FullMark,800,15));
        }

        //teacher check
         QuestionsPage(String No, Answer answer,int  c){
             super(new FlowLayout());

             QuestionType type = answer.getType();
             String flag = (answer.getCompulsory())? "Compulsory":"Optional";

             String Mark = answer.getMark();
             int FullMark = answer.getFullMark();
             String content = answer.getContent();

             setBackground(Color.decode("#E0FFFF"));
             Label label1 = new Label("Question "+No+" ("+FullMark+" Points) "+flag,860,20);
             label1.setFont(new Font("",Font.BOLD,15));
             add(label1);
             add(new Label("",860,5));
             Label label2 = new Label("",860,20);
             add(label2);
             add(new Label("",860,5));
             JTextArea question = new JTextArea(content);
             question.setFont(new Font("", Font.PLAIN,15));
             question.setEditable(false);
             question.setForeground(Color.BLACK);
             question.setPreferredSize(new Dimension(860,60));
             add(question);
             add(new Label("",860,10));

             // add(new Panel(860,400));//for pic
             //add(new Label("",860,10));

             JPanel chooseZone = new JPanel();
             chooseZone.setBackground(Color.WHITE);


             if (type.equals(MUTI)){
                 String[] a = answer.getStandardAnswer();
                 String[] b = answer.getAnswer();
                 chooseZone.setPreferredSize(new Dimension(860,50*(a.length-1)+40));
                 setPreferredSize(new Dimension(860,250+50*(a.length-1)+40));
                 label2.setText(MUTI.name());
                 char[] abcs = new char[]{'A','B','C','D','E','F'};
                 for (int i=0;i<a.length-1;i++){
                     addOption(chooseZone,a[i],abcs[i]);
                 }
                 Label ans = new Label("Standard answer:"+a[a.length-1],840,30);
                 Label an = new Label("Student's answer:"+b[0],840,30);
                 chooseZone.add(ans);
                 chooseZone.add(an);
             }
             else if (type.equals(FILLIN)){
                 String[] a = answer.getStandardAnswer();
                 String[] b = answer.getAnswer();
                 chooseZone.setPreferredSize(new Dimension(860,120*(a.length-1)+10));
                 setPreferredSize(new Dimension(860,250+120*(a.length-1)+10));
                 label2.setText(FILLIN.name());
                 chooseZone.add(new Label("Standard answer:",840,30));
                 for (int i=0;i<a.length-1;i++){
                     addBlanks(chooseZone,a[i],i+1);
                 }
                 chooseZone.add(new Label("Students answer:",840,30));
                 for (int i=0;i<a.length-1;i++){
                     addBlanks(chooseZone,b[i],i+1);
                 }

             }
             else {
                 label2.setText("Standard full-length ");
                 setPreferredSize(new Dimension(860,400));
                 chooseZone.setPreferredSize(new Dimension(860,150));
                 JTextArea ans = new JTextArea(answer.getAnswer()[0]);
                 JScrollPane forAnswer = new JScrollPane(ans,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                 forAnswer.setPreferredSize(new Dimension(800,120));
                 ans.setFont(new Font("", Font.PLAIN,15));
                 ans.setEditable(false);
                 ans.setLineWrap(true);
                 chooseZone.add(new Label("Student's answer is:",800,15));
                 chooseZone.add(forAnswer);

             }
             add(chooseZone);
             if (!answer.getMark().equals("-1"))
                add(new Label("Grade: "+(answer.getMark())+'/'+FullMark,800,15));
         }


         // teacher read
        QuestionsPage(String No,Question q){
            super(new FlowLayout());
            QuestionType type = q.getType();
            String flag = (q.getCompulsory())? "Compulsory":"Optional";

            int Mark = q.getFullMark();
            String content = q.getContent();


            setBackground(Color.decode("#E0FFFF"));

            Label label1 = new Label("Question "+No+" ("+Mark+" Points) "+flag,860,20);
            label1.setFont(new Font("",Font.BOLD,15));
            add(label1);
            add(new Label("",860,5));
            Label label2 = new Label("",860,20);
            add(label2);
            add(new Label("",860,5));
            JTextArea question = new JTextArea(content);
            question.setFont(new Font("", Font.PLAIN,15));
            question.setEditable(false);
            question.setForeground(Color.BLACK);
            question.setPreferredSize(new Dimension(860,60));
            add(question);
            add(new Label("",860,10));

            //add(new Panel(860,400));//for pic
            add(new Label("",860,10));

            JPanel chooseZone = new JPanel();
            chooseZone.setBackground(Color.WHITE);


            if (type.equals(MUTI)){
                String[] answer = q.getStandardAnswer();
                setPreferredSize(new Dimension(860,200+35*(answer.length)+10));
                chooseZone.setPreferredSize(new Dimension(860,35*(answer.length)+10));
                label2.setText("Muti-choice Question");
                char[] abcs = new char[]{'A','B','C','D','E','F'};
                System.out.println(answer.length);
                for (int i=0;i<answer.length-1;i++){
                    if (!answer[i].equals(""))
                        addOption(chooseZone,answer[i],abcs[i]);
                }
                Label an = new Label("Answer is "+answer[answer.length-1],840,30);
                chooseZone.add(an);
            }
            else if (type.equals(FILLIN)){
                String[] answer = q.getStandardAnswer();
                setPreferredSize(new Dimension(860,200+35*(answer.length)));
                chooseZone.setPreferredSize(new Dimension(860,35*(answer.length)));
                label2.setText("Fill in Blank Question");
                for (int i=0;i<answer.length;i++){
                    if (!answer[i].equals(""))
                        addBlanks(chooseZone,answer[i],i+1);
                }

            }
            else {
                label2.setText("Standard full-length Question");
                setPreferredSize(new Dimension(860,200));
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



        private void addOption(JPanel chooseZone, String ms, char op){
            Panel item = new Panel(840,30);
            item.setBackground(Color.decode("#F0FFFF"));
            JCheckBox option = new JCheckBox(op+"");
            option.setBackground(Color.decode("#F0FFFF"));
            item.add(option);
            item.add(new Label(ms,760,20));
            chooseZone.add(item);
        }



        private void addBlanks(JPanel chooseZone,String con,int op){
            Panel item = new Panel(840,30);
            item.setBackground(Color.decode("#F0FFFF"));
            Label Blank = new Label("Blank "+op+": ",60,20);
            Blank.setBackground(Color.decode("#F0FFFF"));
            item.add(Blank);
            Label blank = new Label(con,720,20);
            //blank.setEditable(false);
            //blank.setBackground(Color.white);
            //blank.setPreferredSize(new Dimension(720,20));
            item.add(blank);
            chooseZone.add(item);
        }
    }

    static class InfoPage extends JPanel{

        InfoPage(Exam exam,char code,String id){
            super();
            setBackground(Color.white);
            //add(new Modules.StateBar());
            //setLayout(new BorderLayout());
            setPreferredSize(new Dimension(960,600));
            Panel Center = new Panel(800,360);
            Center.setBounds(80,50,800,360);
            Center.setLayout(new FlowLayout());
            Center.setBackground(Color.white);

            Panel left = new Panel(600,100);
            left.setBackground(Color.white);
            Panel right = new Panel(190,100);
            right.setBackground(Color.white);
            Label label1 = new Label("Subject code:",100,30);
            Label label2 = new Label("Exam name:",100,30);
            Label label3 = new Label("",100,30);
            Label label4 = new Label("Date:",100,30);
            //label4.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label5 = new Label("Start time:",100,30);
            //label5.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label6 = new Label("Due time:",100,30);
            //label6.setHorizontalAlignment(SwingConstants.RIGHT);

            String[] info = exam.getBasicInfo();//name date start due fullMark description class_no.
            Label Sub = new Label(exam.getSub_code(),450,30);
            Label Name = new Label(info[0],450,30);
            Label Setter = new Label("",450,30);
            Label Date = new Label(info[1],80,30);
            Label Start = new Label(info[2],80,30);
            Label Due = new Label(info[3],80,30);

            Label label7 = new Label(" Description: ",800,20);
            label7.setFont(new Font("", Font.PLAIN,15));
            JTextArea Description = new JTextArea(info[5]);
            Description.setEditable(false);
            Description.setPreferredSize(new Dimension(800,250));
            Description.setBackground(Color.decode("#F5F5F5"));
            Description.setFont(new Font("", Font.PLAIN,15));
            Description.setForeground(Color.BLACK);

            Button Enter = new Button("Enter",70,50,true);
            Enter.setBounds(700,525,70,50);
            Button Back = new Button("Back",70,50,true);
            Back.setBounds(810,525,70,50);

            left.add(label1);
            left.add(Sub);
            right.add(label4);
            right.add(Date);
            left.add(label2);
            left.add(Name);
            right.add(label5);
            right.add(Start);
            left.add(label3);
            left.add(Setter);
            right.add(label6);
            right.add(Due);

            Center.add(left);
            Center.add(right);
            Center.add(new Label("",800,20));
            Center.add(label7);
            Center.add(Description);

            Label label8 = new Label("Exam Information",960,60);
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            label8.setFont(new Font("", Font.BOLD,30));
            add(label8);
            add(new Label("",40,400));
            add(Center);
            add(new Label("",40,400));
            add(Enter);
            add(new Label("",40,50));
            add(Back);
            Enter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (code=='0')
                        ViewCentre.setLongPage(exam);
                    else
                        ViewCentre.ExamOpen(exam,id);
                }
            });
            Back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String op[] = new String[]{"Confirm","Cancel"};
                    int i=1;
                    if (code!='0') i = JOptionPane.showOptionDialog(null, "If you quit now, you will lost all your answers.", "Ensure", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op);

                    if (i==0) ViewCentre.Back();
                }
            });
        }

        InfoPage(String subject_code,String tName,designer de){
            super();
            setBackground(Color.white);
            //add(new Modules.StateBar());
            //setLayout(new BorderLayout());
            setPreferredSize(new Dimension(960,600));
            Panel Center = new Panel(800,360);
            Center.setBounds(80,50,800,360);
            Center.setLayout(new FlowLayout());
            Center.setBackground(Color.white);

            Panel left = new Panel(460,100);
            left.setBackground(Color.white);
            Panel right = new Panel(280,100);
            right.setBackground(Color.white);
            Label label1 = new Label("Subject code:",100,30);
            Label label2 = new Label("Exam name:",100,30);
            Label label3 = new Label("Setter name: ",100,30);
            Label label4 = new Label("Date:",100,30);
            //label4.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label5 = new Label("Start time:",100,30);
            //label5.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label6 = new Label("Due time:",100,30);
            //label6.setHorizontalAlignment(SwingConstants.RIGHT);

            Label Sub = new Label(subject_code,350,30);
            JTextField Name = new JTextField("");
            Name.setPreferredSize(new Dimension(350,30));
            Label Setter = new Label(tName,350,30);
            JComboBox D = new JComboBox();
            for (int i=1;i<=9;i++){ D.addItem("0"+i); }
            for (int i=10;i<=31;i++){D.addItem(String.valueOf(i));}

            JComboBox M = new JComboBox();
            for (int i=1;i<=9;i++){ M.addItem("0"+i); }
            for (int i=10;i<=12;i++){M.addItem(String.valueOf(i));}

            JComboBox Y = new JComboBox();
            Y.addItem("2020");
            Y.addItem("2021");

            JComboBox S = new JComboBox();
            JComboBox E = new JComboBox();
            for (int i=1;i<=9;i++){ S.addItem("0"+i); E.addItem("0"+i);}
            for (int i=10;i<=24;i++){S.addItem(String.valueOf(i));E.addItem(String.valueOf(i));}


            Label a = new Label("/",5,30);
            Label b = new Label("/",5,30);
           // Label Date = new Label("12/12/2020",80,30);
            Label Start = new Label(":00",80,30);
            Label Due = new Label(":00",80,30);

            Label label7 = new Label(" Description: ",800,20);
            label7.setFont(new Font("", Font.PLAIN,15));
            JTextArea Description = new JTextArea("");
            Description.setLineWrap(true);
            Description.setPreferredSize(new Dimension(800,250));
            Description.setBackground(Color.decode("#F5F5F5"));
            Description.setFont(new Font("", Font.PLAIN,15));
            Description.setForeground(Color.BLACK);

            Button Enter = new Button("Enter",70,50,true);
            Enter.setBounds(700,525,70,50);
            Button Back = new Button("Back",70,50,true);
            Back.setBounds(810,525,70,50);

            Back.addActionListener(e -> {
                ViewCentre.Back();
                ViewCentre.i = 1;
            });

            Enter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {//String name,String date,String begin,String due,String description
                    String date = D.getSelectedItem() + "-" + M.getSelectedItem() + "-" + Y.getSelectedItem();
                    String begin = S.getSelectedItem() + ":00:00";
                    String due = E.getSelectedItem() + ":00:00";
                    //System.out.println(date+begin+due);
                   // DateFormat DateF = new SimpleDateFormat("dd-MM-yy");
                    DateFormat TimeF = new SimpleDateFormat("hh:mm:ss");
                    DateFormat TD = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
                    try{
                        //long a = TimeF.parse(begin).getTime();
                        //long b = TimeF.parse(due).getTime();
                        //if (!(((TD.parse(date+' '+begin).getTime() - (new Date().getTime()))/1000 >= 86400))) throw new TimeLimitExceededException();
                        //System.out.println(due+' '+begin);
                        //System.out.println(Integer.parseInt(E.getSelectedItem()+"")-Integer.parseInt(S.getSelectedItem()+"")>=1);
                        if (Integer.parseInt(E.getSelectedItem()+"")-Integer.parseInt(S.getSelectedItem()+"")<1) throw new TimeLimitExceededException();

                        if (ViewCentre.canGo()){ViewCentre.ExamGo();}
                        else {
                            de.setter(Name.getText(), date, begin, due, Description.getText());
                            ViewCentre.addNewQuestion(askType(), askFlag());
                        }

                    }catch (Exception se){
                        se.printStackTrace();
                        JOptionPane.showMessageDialog(null,"The date or time setting is not valid. Please edit it again.","Message",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            left.add(label1);
            left.add(Sub);

            right.add(label4);
            right.add(D);
            right.add(a);
            right.add(M);
            right.add(b);
            right.add(Y);

            left.add(label2);
            left.add(Name);

            right.add(label5);
            right.add(S);
            right.add(Start);

            left.add(label3);
            left.add(Setter);

            right.add(label6);
            right.add(E);
            right.add(Due);

            Center.add(left);
            Center.add(right);
            Center.add(new Label("",800,20));
            Center.add(label7);
            Center.add(Description);

            Label label8 = new Label("Exam Information",960,60);
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            label8.setFont(new Font("", Font.BOLD,30));
            add(label8);
            add(new Label("",40,400));
            add(Center);
            add(new Label("",40,400));
            add(Enter);
            add(new Label("",40,50));
            add(Back);
        }

        InfoPage(){
            super();
            setBackground(Color.white);
            //add(new Modules.StateBar());
            //setLayout(new BorderLayout());
            setPreferredSize(new Dimension(960,600));
            Panel Center = new Panel(800,360);
            Center.setBounds(80,50,800,360);
            Center.setLayout(new FlowLayout());
            Center.setBackground(Color.white);

            Panel left = new Panel(600,100);
            left.setBackground(Color.white);
            Panel right = new Panel(190,100);
            right.setBackground(Color.white);
            Label label1 = new Label("Subject code:",100,30);
            Label label2 = new Label("Exam name:",100,30);
            Label label3 = new Label("Setter name: ",100,30);
            Label label4 = new Label("Date:",100,30);
            //label4.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label5 = new Label("Start time:",100,30);
            //label5.setHorizontalAlignment(SwingConstants.RIGHT);
            Label label6 = new Label("Due time:",100,30);
            //label6.setHorizontalAlignment(SwingConstants.RIGHT);

            Label Sub = new Label("COMP1001",450,30);
            Label Name = new Label("Quiz",450,30);
            Label Setter = new Label("ZHANG Yubo",450,30);
            Label Date = new Label("12/12/2020",80,30);
            Label Start = new Label("8:00",80,30);
            Label Due = new Label("10:00",80,30);

            Label label7 = new Label(" Description: ",800,20);
            label7.setFont(new Font("", Font.PLAIN,15));
            JTextArea Description = new JTextArea("This is a quiz.");
            Description.setEditable(false);
            Description.setPreferredSize(new Dimension(800,250));
            Description.setBackground(Color.decode("#F5F5F5"));
            Description.setFont(new Font("", Font.PLAIN,15));
            Description.setForeground(Color.BLACK);

            Button Enter = new Button("Enter",70,50,true);
            Enter.setBounds(700,525,70,50);
            Button Back = new Button("Back",70,50,true);
            Back.setBounds(810,525,70,50);

            left.add(label1);
            left.add(Sub);
            right.add(label4);
            right.add(Date);
            left.add(label2);
            left.add(Name);
            right.add(label5);
            right.add(Start);
            left.add(label3);
            left.add(Setter);
            right.add(label6);
            right.add(Due);

            Center.add(left);
            Center.add(right);
            Center.add(new Label("",800,20));
            Center.add(label7);
            Center.add(Description);

            Label label8 = new Label("Exam Information",960,60);
            label8.setHorizontalAlignment(SwingConstants.CENTER);
            label8.setFont(new Font("", Font.BOLD,30));
            add(label8);
            add(new Label("",40,400));
            add(Center);
            add(new Label("",40,400));
            add(Enter);
            add(new Label("",40,50));
            add(Back);
        }
    }

    static class BottomBar extends JPanel{

        BottomBar(){
            super();
            setBackground(Color.decode("#B0C4DE"));

            Panel state = new Panel(700,60);
            state.setBackground(Color.decode("#B0C4DE"));
            state.add(new Label("",480,27));
            state.add(new Label("",480,27));

            Button quit = new Button("Back",200,55,true);

            add(state);
            add(quit);
            quit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewCentre.Back();
                }
            });
        }

        BottomBar(char c){
            super();
            setBackground(Color.decode("#B0C4DE"));

            Panel state = new Panel(300,60);
            state.setBackground(Color.decode("#B0C4DE"));
            state.add(new Label("",280,27));
            state.add(new Label("",280,27));

            Button quit = new Button("Back",200,55,true);

            Button next;

            Button submit = new Button("Save&Submit",200,55,true);
            submit.setColor("#FDF5E6");

            next= new Button("Add New/Next",200,55,true);

            add(state);
            add(quit);
            add(submit);
            add(next);

            /*setPreferredSize(new Dimension(900,30));
            Button add = new Button("Add New",100,25,true);
            Label label = new Label("",790,27);
            add(label);
            add(add);
             */

            next.addActionListener(e -> {

                if (!ViewCentre.canGo()){//'Multiple-choices','Fill in blank','Standard full-length'
                    JComboBox cmb1 = new JComboBox();
                    cmb1.addItem("Muti-choice Question");
                    cmb1.addItem("Fill-in Blank Question");
                    cmb1.addItem("Standard Full text Question");

                    JComboBox cmb2 = new JComboBox();
                    cmb2.addItem("Compulsory question");
                    cmb2.addItem("Optional question");

                    JComboBox[] items = new JComboBox[]{cmb1, cmb2};
                    String[] op = new String[]{"OK", "Cancel"};

                    int i = JOptionPane.showOptionDialog(null, items, "Select the question type", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op);

                    QuestionType type = switch (String.valueOf(cmb1.getSelectedItem())) {
                        case "Muti-choice Question" -> MUTI;
                        case "Fill-in Blank Question" -> FILLIN;
                        case "Standard Full text Question" -> STANDARD;
                        default -> throw new IllegalStateException("Unexpected value: " + cmb1.getSelectedItem());
                    };

                    boolean flag = switch (String.valueOf(cmb2.getSelectedItem())) {
                        case "Compulsory question" -> true;
                        case "Optional question" -> false;
                        default -> throw new IllegalStateException("Unexpected value: " + cmb2.getSelectedItem());
                    };

                    if (i==0) ViewCentre.addNewQuestion(type, flag);
                }
                else{
                    ViewCentre.ExamGo();
                }
            });
            quit.addActionListener(e -> ViewCentre.ExamBack());
            submit.addActionListener(e -> {
                String[] op = new String[]{"OK", "Cancel"};
                int i = JOptionPane.showOptionDialog(null, "You will submit the exam to the system database.", "Ensure", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op);
                if (i==0) {
                    JOptionPane.showMessageDialog(null,"Successfully upload!","Message",JOptionPane.PLAIN_MESSAGE);
                    ViewCentre.tSubmit();
                }
            });
        }

        BottomBar(String id){
            super();
            setBackground(Color.decode("#B0C4DE"));

            Panel state = new Panel(300,60);
            state.setBackground(Color.decode("#B0C4DE"));
            state.add(new Label("",280,27));
            state.add(new Label("",280,27));

            Button quit = new Button("Back",200,55,true);

            Button next;

            Button submit = new Button("Save&Submit",200,55,true);
            submit.setColor("#FDF5E6");

            next= new Button("Next",200,55,true);

            add(state);
            add(quit);
            add(submit);
            add(next);

            /*setPreferredSize(new Dimension(900,30));
            Button add = new Button("Add New",100,25,true);
            Label label = new Label("",790,27);
            add(label);
            add(add);
             */

            next.addActionListener(e -> {
                if (!ViewCentre.canGo())  JOptionPane.showMessageDialog(null,"Question in this page is already the last question.","Message",JOptionPane.PLAIN_MESSAGE);
                else ViewCentre.ExamGo();
            });
            quit.addActionListener(e -> ViewCentre.ExamBack());
            submit.addActionListener(e -> {
                String[] op = new String[]{"OK", "Cancel"};
                int i = JOptionPane.showOptionDialog(null, "You will submit your answers to the system database and you will no longer to modify them.", "Ensure", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op);
                if (i==0) {
                    ViewCentre.sSubmit(id);
                    JOptionPane.showMessageDialog(null,"Successfully upload!","Message",JOptionPane.PLAIN_MESSAGE);
                }
            });


        }

        BottomBar(int c){
            super();
            setBackground(Color.decode("#B0C4DE"));

            Panel state = new Panel(500,60);
            state.setBackground(Color.decode("#B0C4DE"));
            state.add(new Label("",480,27));
            state.add(new Label("",480,27));

            Button quit = new Button("Back",200,55,true);

            Button next;

            Button submit = new Button("Save&Submit",200,55,true);
            submit.setColor("#FDF5E6");


            add(state);
            add(quit);
            add(submit);

            /*setPreferredSize(new Dimension(900,30));
            Button add = new Button("Add New",100,25,true);
            Label label = new Label("",790,27);
            add(label);
            add(add);
             */


            quit.addActionListener(e -> ViewCentre.ExamBack());
            submit.addActionListener(e -> {
                String[] op = new String[]{"OK", "Cancel"};
                int i = JOptionPane.showOptionDialog(null, "Confirm submission? It cannot be changed after submission.", "Ensure", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op);
                if (i==0) {
                    JOptionPane.showMessageDialog(null,"Successfully upload!","Message",JOptionPane.PLAIN_MESSAGE);
                    ViewCentre.gradeSub();
                    ViewCentre.Back();
                }
            });
         }
    }
}
