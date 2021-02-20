package polyu.hk.comp2411.gp35.View;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    String name;

    Button(String ms,int width,int height,Boolean set){
        super(ms);
        setPreferredSize(new Dimension(width,height));
        if (set) setLucency();
    }

    Button(ImageIcon icon,int width,int height){
        super();
        setPreferredSize(new Dimension(width,height));
        setIcon(icon);
        setBorderPainted(false);//不打印边框
        setBorder(null);//除去边框
        setFocusPainted(false);//除去焦点的框
        setContentAreaFilled(false);//除去默认的背景填充
    }

    public void setLucency(){
        setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0
        setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        setFocusPainted(false);//除去焦点的框
        setContentAreaFilled(false);//除去默认的背景填充
    }

    public void setColor(String color){
        setBackground(Color.decode(color));
    }

}
