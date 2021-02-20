package polyu.hk.comp2411.gp35.View;


import polyu.hk.comp2411.gp35.Controller.AESController;
import polyu.hk.comp2411.gp35.Model.Classroom;
import polyu.hk.comp2411.gp35.Model.Exam;
import polyu.hk.comp2411.gp35.Model.Subject;
import polyu.hk.comp2411.gp35.Model.designer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.lang.String.valueOf;


public class Modules {


    /**
     * 顶端状态栏
     */
    public static class StateBar extends JPanel{

        static boolean enable = true;
        static final String imagePath1 = "src\\img\\SettingIcon~1.png";
        static final String imagePath2 = "src\\img\\SettingIcon1.png";
        static final String imagePath3 = "src\\img\\SettingIcon2.png";

        StateBar(){
            super();
            this.setLayout(new BorderLayout());

            String name,t;
            if (AESController.dataPool.getid().endsWith("d")){
                name = AESController.dataPool.getCurStudent().getBasicInfo()[0];
                t = "Class No: "+AESController.dataPool.getCurStudent().getClassNo();
            }else{
                name = AESController.dataPool.getCurTeacher().getBasic_info()[0];
                t = "";
            }


            Label Class = new Label(t,140,30);
            Class.setHorizontalAlignment(SwingConstants.CENTER);
            Class.setFont(new Font("",Font.BOLD,15));

            Label Name = new Label(name,120,30);
            Button Setting = new Button(new ImageIcon(imagePath3),30,30);
            Setting.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Setting.setIcon(new ImageIcon(imagePath1));
                    if (enable) {
                        String[] Options = new String[]{"Change Password", "Sign out", "Cancel"};
                        int m = JOptionPane.showOptionDialog(null, "Select your operation", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[2]);
                        dialogs(m);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"This operation cannot be performed in the exam interface","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    Setting.setIcon(new ImageIcon(imagePath1));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    Setting.setIcon(new ImageIcon(imagePath3));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Setting.setIcon(new ImageIcon(imagePath2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Setting.setIcon(new ImageIcon(imagePath3));
                }
            });


            this.add(Class,BorderLayout.WEST);
            Label label = new Label("",610,30);
            Name.setBorder(null);
            Panel Right = new Panel(180,35);
            Right.setBackground(Color.decode("#87CEFF"));
            Right.add(Name);
            Right.add(Setting);

            this.add(label,BorderLayout.CENTER);
            this.add(Right,BorderLayout.EAST);
            this.setPreferredSize(new Dimension(960,40));
            this.setBackground(Color.decode("#87CEFF"));
            this.setVisible(true);
        }

        private void dialogs(int m){

            JPasswordField pwd = new JPasswordField();
            Object[] message = {"", pwd};
            String[] op = new String[]{"OK","Cancel"};
            int a;
            switch (m){
                case 0:
                    message[0] = "Enter your old password：\n";
                    a = JOptionPane.showOptionDialog(null,message,"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op);
                    if (a == 1) break;
                    String p = valueOf(pwd.getPassword());
                    pwd.setText("");
                    String truePass = (AESController.dataPool.getid().endsWith("d"))? AESController.dataPool.getCurStudent().getPassword():AESController.dataPool.getCurTeacher().getPassword();
                    while (!p.equals(truePass)){
                        message[0] = "Error! Enter your old password：\n";
                        if (a == 1) break;
                        a = JOptionPane.showOptionDialog(null,message,"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op);
                        p = valueOf(pwd.getPassword());
                        pwd.setText("");
                    }
                    if (a == 1) break;

                    message[0] = "Enter your new password：\n";
                    a = JOptionPane.showOptionDialog(null,message,"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op);
                    if (a == 1) break;
                    String b = valueOf(pwd.getPassword());
                    pwd.setText("");
                    while (b.length() < 6 || b.length() > 16){
                        message[0] = "The password must be between 6 and 16 digits.\nEnter your new password：\n";
                        a = JOptionPane.showOptionDialog(null,message,"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op);
                        if (a == 1) break;
                        b = valueOf(pwd.getPassword());
                        pwd.setText("");
                    }
                    if (a == 1) break;
                    try {
                        if (AESController.dataPool.getid().endsWith("d")) AESController.dataPool.getCurStudent().setPassword(b);
                        else AESController.dataPool.getCurTeacher().setPassword(b);
                        JOptionPane.showMessageDialog(null,"Changing password successfully, please sign in again","Message", JOptionPane.PLAIN_MESSAGE);

                    }catch (Exception se){
                        se.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Changing password failed","Message",JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                case 1:
                    ViewCentre.window.setVisible(false);
                    new signIn();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 左侧选择栏
     */
    public static class SideBar extends JPanel{
        private static final Button Main = new Button("Main Page",130,30,true);
        private static final Button Grade = new Button("Grades",130,30,true);

        SideBar(String[] subs){
            super();
            setLayout(new FlowLayout(FlowLayout.LEADING));

            Main.setFont(new Font("",Font.BOLD,20));
            Main.addActionListener(e -> ((NormalPage)getParent()).setInfoPage());

            Grade.setFont(new Font("",Font.BOLD,20));
            JList subList = new JList((subs.length == 0) ? new String[]{"No subject...."} : subs);
            subList.setPreferredSize(new Dimension(130,(subs.length==0)?35:35*subs.length));
            subList.setFixedCellHeight(30);
            subList.setFont(new Font("",Font.BOLD,20));
            subList.addListSelectionListener(e -> ((NormalPage)getParent()).setSubPage(valueOf(subList.getSelectedValue())));

            Grade.addActionListener(e -> ((NormalPage)getParent()).setGradePage());

            add(Main);
            Label su = new Label("Subjects:",130,25);
            su.setFont(new Font("",Font.BOLD,15));
            //su.setHorizontalAlignment(SwingConstants.CENTER);
            add(su);
            add(subList);
            add(Grade);

            setPreferredSize(new Dimension(140,640));
            setBackground(Color.decode("#F0FFFF"));
            setVisible(true);
        }

    }


    /**
     * 学生/老师 信息展示栏
     */
    public static class InfoZone extends JPanel{
        InfoZone(String[] info){
            super();
            setPreferredSize(new Dimension(820,600));
            setBackground(Color.decode("#FFFAFA"));
            this.setLayout(new FlowLayout());

            setZone1(info);
        }

        InfoZone(Subject subject){
            super();
            setPreferredSize(new Dimension(820,600));
            setBackground(Color.decode("#FFFAFA"));
            this.setLayout(new FlowLayout());

            setZone2(subject);
        }

        InfoZone(HashMap<String,Subject> subs){
            super();
            setPreferredSize(new Dimension(820,600));
            setBackground(Color.decode("#FFFAFA"));
            this.setLayout(new FlowLayout());

            setZone3(subs);
        }


        /**
         * 信息展示栏 展示区域
         * @param info 具体信息传入数组
         */
        private void setZone1(String[] info){
            //String[][] infoD = new String[][]{{info[0],info[1]},{info[2],info[3]},{info[4],info[5]},{info[6],info[7]}};
            //Zone = new JTable(infoD,new String[]{"",""});
            JPanel zone = new JPanel();
            zone.setLayout(new FlowLayout(FlowLayout.LEADING));
            zone.setBackground(Color.decode("#FDF5E6"));
            zone.setPreferredSize(new Dimension(720,400));
            //Zone.setRowHeight(85);

            JLabel label;
            for (int i=0;i<info.length;i++){
                label = new JLabel(info[i]);
                label.setFont(new Font("", Font.BOLD,20));
                if (i==3 || i==0)
                    label.setPreferredSize(new Dimension(500,80));
                else
                    label.setPreferredSize(new Dimension(250,80));
                zone.add(label);
            }
            JLabel label1 = new JLabel("Main Page");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font("", Font.BOLD,30));
            label1.setPreferredSize(new Dimension(820,100));

            add(label1,"North");
            add(zone,BorderLayout.CENTER);
        }


        /**
         * 科目展示Page
         */
        void setZone2(Subject subject){// 820*600
            HashMap<String, Classroom> c = null;
            JComboBox cmb = new JComboBox();
            if (AESController.dataPool.getid().endsWith("s")) {

                Label classNo=new Label("class no.",80,30);
                classNo.setFont(new Font("", Font.BOLD,15));

                 c = subject.getClasses();

                if (c.size()!=0) for (String s : c.keySet()) { cmb.addItem(s); }
                else cmb.addItem("No class");
                cmb.setPreferredSize(new Dimension(80, 20));

                add(classNo);
                add(cmb);
            }
            else{
                Label classNo=new Label("",160,30);
                add(classNo);
            }

            Label label1 = new Label("",600,40);
            Label sub = new Label(subject.getCode()+' '+subject.getTopic(),800,80);
            sub.setHorizontalAlignment(SwingConstants.CENTER);
            sub.setFont(new Font("", Font.BOLD,25));
            Label label2 = new Label("",800,40);

            add(label1);
            add(sub);
            add(label2);


            if (AESController.dataPool.getid().endsWith("s")){

                if (c!=null && c.size()!=0){

                    CardLayout card = new CardLayout();
                    JPanel down = new JPanel(card);
                    down.setBackground(Color.decode("#FFFAFA"));
                    //down.setPreferredSize(new Dimension(780,300));

                    boolean isFirst = true;
                    String fsc = "";
                    for (String sc:c.keySet()){
                        if (isFirst){fsc = valueOf(sc);isFirst = false;}
                        if (c.get(sc).getExam()==null){
                            Panel Exam = new Panel(650,30);
                            Label ex = new Label("Exam",638,20);
                            ex.setFont(new Font("", Font.BOLD,15));
                            Exam.setBackground(Color.decode("#BEBEBE"));
                            Exam.add(ex);

                            Button Designer = new Button("Test Designer",120,30,false);
                            Designer.setBackground(Color.decode("#E6E6FA"));
                            Panel l = new Panel(780,35);
                            l.setBackground(Color.decode("#FFFAFA"));
                            l.add(Exam);
                            l.add(Designer);

                            Panel carry = new Panel(790,300);
                            carry.setBackground(Color.decode("#FFFAFA"));

                            carry.add(l);

                            Label e = new Label("No exam yet. Your can click \"Test Designer\" to design and upload a exam for this subject",800,80);
                            e.setHorizontalAlignment(SwingConstants.CENTER);

                            carry.add(e);
                            down.add(carry,sc);


                            Designer.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    designer de = new designer(subject,String.valueOf(cmb.getSelectedItem()));
                                    ViewCentre.setInfoPage(sc,AESController.dataPool.getCurTeacher().getBasic_info()[0],de);
                                }
                            });
                        }
                        else{
                            Panel Exam = new Panel(650,30);
                            Label ex = new Label("Exam",638,20);
                            ex.setFont(new Font("", Font.BOLD,15));
                            Exam.setBackground(Color.decode("#BEBEBE"));
                            Exam.add(ex);

                            Button Designer = new Button("Test Designer",120,30,false);
                            Designer.setBackground(Color.decode("#E6E6FA"));
                            Designer.setEnabled(false);

                            Panel l = new Panel(790,35);
                            l.setBackground(Color.decode("#E6E6FA"));
                            l.add(Exam);
                            l.add(Designer);

                            Panel carry = new Panel(790,120);
                            carry.add(l);


                            Panel ExamInfo = new Panel(690,65);
                            ExamInfo.setBackground(Color.decode("#FFE4B5"));
                            ExamInfo.setLayout(new FlowLayout());

                            String[] info = c.get(sc).getExam().getBasicInfo();//name date start due fullMark
                            Label ExamName = new Label(info[0],80,30);
                            ExamName.setFont(new Font("", Font.PLAIN,13));
                            Panel Center = new Panel(500,60);

                            Center.setBackground(Color.decode("#FFE4B5"));
                            Label Begin = new Label("Begin:"+info[2],490,25);
                            Begin.setHorizontalAlignment(SwingConstants.CENTER);
                            Label Due = new Label("Due: " +info[3],490,25);
                            Due.setHorizontalAlignment(SwingConstants.CENTER);
                            Center.add(Begin);
                            Center.add(Due);

                            Label Date = new Label("At "+info[1],85,30);
                            Date.setHorizontalAlignment(SwingConstants.CENTER);

                            Button Open = new Button("View",80,65,true);
                            HashMap<String, Classroom> finalC = c;
                            Open.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ViewCentre.setInfoPage(finalC.get(sc).getExam(),'0',AESController.dataPool.getid());
                                    //ViewCentre.setLongPage();
                                }
                            });

                            ExamInfo.add(ExamName);
                            ExamInfo.add(Center);
                            ExamInfo.add(Date);

                            carry.add(ExamInfo);
                            carry.add(Open);

                            down.add(carry,sc);

                        }

                    }
                    card.show(down,fsc);
                    add(down);
                    cmb.addActionListener(e -> card.show(down, valueOf(cmb.getSelectedItem())));
                }

            }
            else{
                Label teacher = new Label("Teacher: "+subject.getTeacherName(),790,20);
                teacher.setHorizontalAlignment(SwingConstants.RIGHT);
                Panel Ex = new Panel(790,30);
                Label ex = new Label("Exam",780,20);
                ex.setFont(new Font("", Font.BOLD,15));
                Ex.setBackground(Color.decode("#BEBEBE"));
                Ex.add(ex);

                add(teacher);
                add(Ex);

                Exam exam = subject.getExam();
                if (exam!=null){
                    Panel ExamInfo = new Panel(710,60);
                    ExamInfo.setBackground(Color.decode("#FFE4B5"));
                    ExamInfo.setLayout(new FlowLayout());

                    String[] info = exam.getBasicInfo();//name date start due fullMark
                    Label ExamName = new Label(info[0],80,25);
                    ExamName.setFont(new Font("", Font.PLAIN,13));
                    Panel Center = new Panel(520,60);

                    Center.setBackground(Color.decode("#FFE4B5"));
                    Label Begin = new Label("Begin:"+info[2],510,20);
                    Begin.setHorizontalAlignment(SwingConstants.CENTER);
                    Label Due = new Label("Due: " +info[3],510,20);
                    Due.setHorizontalAlignment(SwingConstants.CENTER);
                    Center.add(Begin);
                    Center.add(Due);

                    Label Date = new Label("At "+info[1],80,25);
                    Date.setHorizontalAlignment(SwingConstants.CENTER);

                    Button Open = new Button("",80,60,true);

                    DateFormat D = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
                    Date start;
                    Date due;
                    try{
                        start = D.parse(info[1] +' '+ info[2]);
                        due = D.parse(info[1] + ' '+info[3]);

                        if ((new Date()).after(start) && (new Date()).before(due)){
                            if (exam.getAnswers().size()==0) {
                                Open.setText("Enter");
                                Open.addActionListener(e -> ViewCentre.setInfoPage(exam,'1',AESController.dataPool.getid()));
                            }

                            else {
                                Open.setText("View");
                                Open.addActionListener(e -> ViewCentre.setLongPage(exam,'1'));
                            }
                        }
                        else if ((new Date()).before(start)){
                            long a = new Date().getTime();
                            long b = start.getTime();
                            int d = (int)((b - a) / 1000);
                            if (d<=300)
                                if (exam.getAnswers().size()==0) {
                                    Open.setText("Enter");
                                    Open.addActionListener(e -> ViewCentre.setInfoPage(exam,'1',AESController.dataPool.getid()));
                                }

                                else {
                                    Open.setText("View");
                                    Open.addActionListener(e -> ViewCentre.setLongPage(exam,'1'));
                                }
                            else{
                                Open.setText("Unopen");
                                Open.setEnabled(false);
                            }
                        }
                        else if ((new Date()).after(due)){
                            if (exam.getAnswers().size()==0){
                                Open.setText("Absent");
                                Open.setEnabled(false);
                            }
                            else Open.setText("View");
                        }

                    }catch (ParseException se){se.printStackTrace();}



                    ExamInfo.add(ExamName);
                    ExamInfo.add(Center);
                    ExamInfo.add(Date);

                    add(ExamInfo);
                    add(Open);
                }else{
                    Label e = new Label("Exam of this subject has not yet been released.",800,80);
                    e.setHorizontalAlignment(SwingConstants.CENTER);
                    add(e);
                }
            }




        }

        void setZone3(HashMap<String,Subject> subjects){

            String[] color = new String[]{"#96CDCD","#668B8B"};
            int index;


            if (AESController.dataPool.getid().endsWith("d")){
                Panel Grades = new Panel(800,580);
                Panel titles = new Panel(800,30);
                titles.setBackground(Color.decode("#BEBEBE"));

                Label Name = new Label("Subject",130,25);
                Name.setHorizontalAlignment(SwingConstants.CENTER);
                Label Id = new Label("Exam date",130,25);
                Id.setHorizontalAlignment(SwingConstants.CENTER);
                Label Mark = new Label("Mark",130,25);
                Mark.setHorizontalAlignment(SwingConstants.CENTER);
                Label Letter = new Label("Letter Grade",130,25);
                Letter.setHorizontalAlignment(SwingConstants.CENTER);
                Label label2 = new Label("View",130,25);
                label2.setHorizontalAlignment(SwingConstants.CENTER);

                titles.add(Name);
                titles.add(Id);
                titles.add(Mark);
                titles.add(Letter);
                titles.add(label2);

                Grades.add(titles);
                boolean flag = false;
                String[] info = new String[4];
                Subject s;
                index = 0;
                for (String sub:subjects.keySet()){
                    s = subjects.get(sub);
                    if (s.getExam()!=null){
                        if (s.getExam().getRecord()==null) continue;
                        flag = true;
                        info[0] = s.getExam().getRecord().getTitle();
                        info[1] = s.getExam().getDate() +' '+ s.getExam().getBegin();
                        info[2] = valueOf(s.getExam().generateMark());
                        info[3] = s.getExam().getRecord().getLetter_grade();

                        GradeZone e = new GradeZone(info,s.getExam());
                        e.setBackground(Color.decode(color[index]));
                        Grades.add(e);
                        if (index==0) index = 1;
                        else index = 0;
                    }
                }


                if (!flag){
                    Label e = new Label("You haven't have any exam yet.",800,80);
                    e.setHorizontalAlignment(SwingConstants.CENTER);
                    Grades.add(e);
                }


                JScrollPane forGradeZone = new JScrollPane(Grades,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                forGradeZone.setPreferredSize(new Dimension(800,580));
                add(forGradeZone);

            }else{

                HashMap<String,Classroom> cls;
                if (subjects!=null && !subjects.isEmpty()){
                    Panel top1 = new Panel(800,30);

                    JLabel Sub=new JLabel("Subject");
                    Sub.setFont(new Font("", Font.BOLD,15));
                    Sub.setHorizontalAlignment(SwingConstants.CENTER);
                    Sub.setPreferredSize(new Dimension(80,20));
                    Label label1 = new Label("",600,20);
                    var cmb1=new JComboBox();
                    cmb1.setPreferredSize(new Dimension(100,20));

                    top1.add(Sub);
                    top1.add(cmb1);
                    top1.add(label1);
                    add(top1);

                    CardLayout card1 = new CardLayout();
                    JPanel l1  = new JPanel(card1);
                    l1.setPreferredSize(new Dimension(800,565));



                    for (String sub:subjects.keySet()){
                        cmb1.addItem(sub);

                        Panel c1 = new Panel(800,550);

                        cls = subjects.get(sub).getClasses();
                        if (cls!=null && !cls.isEmpty()){

                            Panel top2 = new Panel(800,25);
                            JLabel ClassNo=new JLabel("Class No");
                            ClassNo.setFont(new Font("", Font.BOLD,15));
                            ClassNo.setHorizontalAlignment(SwingConstants.CENTER);
                            ClassNo.setPreferredSize(new Dimension(80,20));
                            Label label2 = new Label("",600,20);
                            var cmb2=new JComboBox();
                            cmb2.setPreferredSize(new Dimension(100,20));

                            top2.add(ClassNo);
                            top2.add(cmb2);
                            top2.add(label2);
                            c1.add(top2);
                            c1.add(titleBuilder());

                            CardLayout card2 = new CardLayout();
                            JPanel l2  = new JPanel(card2);
                            l2.setPreferredSize(new Dimension(800,450));


                            for (String cl:cls.keySet()){
                                cmb2.addItem(cl);

                                Panel c2 = new Panel(790,500);
                                JScrollPane forc2 = new JScrollPane(c2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                forc2.setPreferredSize(new Dimension(800,490));
                                forc2.getVerticalScrollBar().setUnitIncrement(15);

                                HashMap<String,Exam> ExamOfStu = cls.get(cl).getExamOfStu();
                                HashMap<String,String> Names = cls.get(cl).getStudentNames();// name id mark letter
                                index = 0;
                                if (ExamOfStu!=null&&!ExamOfStu.isEmpty()){
                                    for (String id:ExamOfStu.keySet()){
                                        String mark = ExamOfStu.get(id).generateMark();
                                        String letter;
                                        if (ExamOfStu.get(id).getRecord()==null) {
                                            if (ExamOfStu.get(id).getAnswers().size()==0) continue;
                                            else letter = "Ungraded";
                                        }
                                        else letter = ExamOfStu.get(id).getRecord().getLetter_grade();

                                        GradeZone gz = new GradeZone(new String[]{Names.get(id),id,mark,letter},ExamOfStu.get(id),letter);
                                        gz.setBackground(Color.decode(color[index]));
                                        if (index==0) index = 1;
                                        else index = 0;

                                        c2.add(gz);

                                    }
                                }
                                else{ c2.add(new JLabel("No exam yet.")); }

                                l2.add(forc2,cl);
                                c1.add(l2);
                            }
                            card2.show(l2,String.valueOf(cmb2.getItemAt(0)));
                            cmb2.addActionListener(e -> card2.show(l2, valueOf(cmb2.getSelectedItem())));
                        }
                        else{ c1.add(new JLabel("No class")); }

                        l1.add(c1,sub);
                        add(l1);
                    }
                    card1.show(l1,String.valueOf(cmb1.getItemAt(0)));
                    cmb1.addActionListener(e -> card1.show(l1, valueOf(cmb1.getSelectedItem())));
                }
                else { add(new JLabel("No subject")); }
            }

        }

        private Panel titleBuilder(){
            Panel titles = new Panel(800,30);
            Label Name = new Label("Student Name",130,25);
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Label Id = new Label("Student ID",130,25);
            Id.setHorizontalAlignment(SwingConstants.CENTER);
            Label Mark = new Label("Mark",130,25);
            Mark.setHorizontalAlignment(SwingConstants.CENTER);
            Label Letter = new Label("Letter Grade",130,25);
            Letter.setHorizontalAlignment(SwingConstants.CENTER);
            Label label2 = new Label("View",130,25);
            label2.setHorizontalAlignment(SwingConstants.CENTER);


            titles.setBackground(Color.decode("#BEBEBE"));

            titles.add(Name);
            titles.add(Id);
            titles.add(Mark);
            titles.add(Letter);
            titles.add(label2);
            return titles;
        }
    }

    public static class GradeZone extends JPanel{
        GradeZone(String[] info,Exam exam){
            super();
            setPreferredSize(new Dimension(800,30));
            Label Name = new Label(info[0],130,25);
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Label Id = new Label(info[1],130,25);
            Id.setHorizontalAlignment(SwingConstants.CENTER);
            Label Mark = new Label(info[2],130,25);
            Mark.setHorizontalAlignment(SwingConstants.CENTER);
            Label Letter = new Label(info[3],130,25);
            Letter.setHorizontalAlignment(SwingConstants.CENTER);
            Button Go = new Button("View",130,25,false);
            Go.setBackground(Color.decode("#E0FFFF"));
            Go.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewCentre.setLongPage(exam);
                }
            });

            add(Name);
            add(Id);
            add(Mark);
            add(Letter);
            add(Go);
        }

        GradeZone(String[] info,Exam exam,String Grade){
            super();
            setPreferredSize(new Dimension(800,30));
            Label Name = new Label(info[0],130,25);
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Label Id = new Label(info[1],130,25);
            Id.setHorizontalAlignment(SwingConstants.CENTER);
            Label Mark = new Label(info[2],130,25);
            Mark.setHorizontalAlignment(SwingConstants.CENTER);
            Label Letter = new Label(Grade,130,25);
            Letter.setHorizontalAlignment(SwingConstants.CENTER);
            Button Go = new Button("View",130,25,false);
            Go.setBackground(Color.decode("#E0FFFF"));
            Go.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Grade.equals("Ungraded")) ViewCentre.setLongPage(exam,1);
                    else ViewCentre.setLongPage(exam,'1');
                }
            });

            add(Name);
            add(Id);
            add(Mark);
            add(Letter);
            add(Go);
        }
    }



}
