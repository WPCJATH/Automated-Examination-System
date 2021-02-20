package polyu.hk.comp2411.gp35.View;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    Label(String text, int width, int height){
        super(text);
        setPreferredSize(new Dimension(width,height));
    }
}
