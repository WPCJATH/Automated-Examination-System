package polyu.hk.comp2411.gp35.View;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    static final String imagePath = "src\\img\\Icon.png";


    public MainWindow(){
        super();
        this.setBackground(Color.WHITE);
        this.setBounds(350,200,960,640);
        //this.setLayout(new CardLayout());
        this.setImage();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    private void setImage(){
        Toolkit t =Toolkit.getDefaultToolkit();
        Image img = t.getImage(imagePath);
        this.setIconImage(img);
    }
}
