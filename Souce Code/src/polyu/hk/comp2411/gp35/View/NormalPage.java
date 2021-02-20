package polyu.hk.comp2411.gp35.View;

import polyu.hk.comp2411.gp35.Controller.AESController;
import polyu.hk.comp2411.gp35.Model.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static polyu.hk.comp2411.gp35.View.Modules.*;

/**
 *
 */
public class NormalPage extends JPanel {
     CardLayout card;
     JPanel mainZone;

    public NormalPage(){
        super();
        setSize(960,600);
        setBackground(Color.decode("#DCDCDC"));

        card = new CardLayout();
        this.setLayout(new BorderLayout());
        mainZone = new JPanel(card);

        String id = AESController.dataPool.getid();
        HashMap<String,Subject> subjects;

        InfoZone infoZone1;
        if (id.endsWith("d")){
            subjects = AESController.dataPool.getCurStudent().getSubjects();
            String[] info = AESController.dataPool.getCurStudent().getBasicInfo();
            infoZone1 = new InfoZone(new String[]{"Name: "+info[0],"ID: " + AESController.dataPool.getid(),"Depart: " + info[1],"E-mail: " + info[2], "State: "+AESController.dataPool.getCurStudent().getState()});
        }else{
            subjects = AESController.dataPool.getCurTeacher().getSubjects();
            String[] info = AESController.dataPool.getCurTeacher().getBasic_info();// name, depart, email
            String classInCharge = AESController.dataPool.getCurTeacher().getClassCharge();
            infoZone1 = new InfoZone(new String[]{"Name: "+info[0],"ID: " + AESController.dataPool.getid(),"Depart: " + info[1],"E-mail: " + info[2], "Class in charge: "+classInCharge});
        }


        String[] subs = new String[subjects.size()];
        int i=0;
        for(String sub: subjects.keySet()) {
            subs[i++] = sub;
            InfoZone infoZone2 = new InfoZone(subjects.get(sub));
            mainZone.add(infoZone2,sub);
        }

        SideBar sideBar = new SideBar(subs);  // left side bar
        add(sideBar,BorderLayout.WEST);

        InfoZone gradeZone = new InfoZone(subjects);
        mainZone.add(infoZone1,"1");

        mainZone.add(gradeZone,"2");


        add(mainZone,BorderLayout.CENTER);
        setInfoPage();
        setVisible(true);
    }

    public void setInfoPage(){
        card.show(mainZone,"1");
    }

    public void setGradePage(){
        card.show(mainZone,"2");
    }

    public void setSubPage(String sub){
        card.show(mainZone,sub);
    }

}
