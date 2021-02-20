package polyu.hk.comp2411.gp35.View;

import polyu.hk.comp2411.gp35.Model.StuSignIn;
import polyu.hk.comp2411.gp35.Model.TeaSignIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static polyu.hk.comp2411.gp35.View.ViewCentre.Open;

public class signIn extends JFrame {
    static final String imagePath = "src\\img\\signin.png";

    JTextField jtf;
    JTextField jta;

    public signIn() {
        super();
        Label P = new Label("Sign in Page",320,40);
        P.setFont(new Font("",Font.BOLD,15));
        P.setHorizontalAlignment(SwingConstants.CENTER);

        Panel p4 = new Panel(330,30);
        p4.setBackground(Color.WHITE);

        Panel idZone = new Panel(330,30);
        idZone.setBackground(Color.WHITE);
        Label lb1 = new Label("Student/StaffID：",120,25);
        lb1.setHorizontalAlignment(SwingConstants.LEFT);
        jtf = new JTextField(16);
        jtf.setPreferredSize(new Dimension(200,25));
        idZone.add(lb1);
        idZone.add(jtf);

        Panel p1 = new Panel(330,30);
        p1.setBackground(Color.WHITE);

        Panel passZone = new Panel(330,30);
        passZone.setBackground(Color.WHITE);
        Label lb2 = new Label("Password：",120,25);
        lb2.setHorizontalAlignment(SwingConstants.LEFT);
        jta = new JPasswordField(16);
        jta.setPreferredSize(new Dimension(200,25));
        passZone.add(lb2);
        passZone.add(jta);

        Panel p2 = new Panel(330,40);
        p2.setBackground(Color.WHITE);

        Panel bZone = new Panel(330,30);
        bZone.setBackground(Color.WHITE);
        Button b1 = new Button("Sign in",80,25,true);

        Label l1 =new Label("",40,25);
        Button b3 = new Button("Cancel",80,25,true);
        bZone.add(b1);
        bZone.add(l1);
        bZone.add(b3);

        Panel p3 = new Panel(330,30);
        p3.setLayout(new BorderLayout());
        p3.setBackground(Color.WHITE);
        Button b4 = new Button(new ImageIcon("src\\img\\help.png"),30,30);
        b4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                b4.setIcon(new ImageIcon("src\\img\\help1.png"));
                try{
                    Desktop.getDesktop().open(new File("src\\help\\help.txt"));
                }catch (Exception se){
                    se.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                b4.setIcon(new ImageIcon("src\\img\\help1.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                b4.setIcon(new ImageIcon("src\\img\\help.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                b4.setIcon(new ImageIcon("src\\img\\help.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b4.setIcon(new ImageIcon("src\\img\\help.png"));
            }

        });
        p3.add(b4,BorderLayout.EAST);

        ///Register listener events
        DataFind mm = new DataFind();
        b1.addActionListener(mm);


        //for cancel
        b3.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ee){
                        System.exit(0);
                    }
                });

        Box b=Box.createVerticalBox();
        b.add(P);
        b.add(p4);
        b.setBackground(Color.WHITE);
        b.add(idZone);
        b.add(p1);
        b.add(passZone);
        b.add(p2);
        b.add(bZone);
        b.add(p3);
        add(b);

        Toolkit t =Toolkit.getDefaultToolkit();
        Image img = t.getImage(imagePath);
        this.setIconImage(img);
        setBounds(500,300,350,280);
        setTitle("Sign in");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    //event listener
    class DataFind implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str0 = jtf.getText();
            String str1 = jta.getText();

            try {
                if (str0.endsWith("d")) {
                    new StuSignIn(str0, str1);
                    setVisible(false);
                    Open();
                }
                else if (str0.endsWith("s")) {
                    new TeaSignIn(str0, str1);
                    setVisible(false);
                    Open();
                }
                else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception se) {
                se.printStackTrace();
                if (jtf.getText().equals("") || jtf.getText().equals(""))
                    JOptionPane.showMessageDialog(null,"ID or Password cannot be empty","Message",JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null,"Incorrect ID or Password, please try again.","Message",JOptionPane.ERROR_MESSAGE);
                jta.setText("");
            }

        }
    }
}
