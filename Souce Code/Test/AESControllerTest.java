import org.junit.Test;
import polyu.hk.comp2411.gp35.Controller.*;
import polyu.hk.comp2411.gp35.Model.Pack;
import polyu.hk.comp2411.gp35.Model.Question;
import polyu.hk.comp2411.gp35.Model.designer;
import polyu.hk.comp2411.gp35.View.examEditor;

public class AESControllerTest {
    @Test
    public void ConstructorTest(){
        AESController AESController = new AESController();
    }

    @Test
    public void splitTest(){
        String[] a = Question.SplitA("dahudauiwhdaad[da]dhad[gsfs]fafaff[]sfd");
        System.out.println(a[0]);
        System.out.println(a[1]);
        System.out.println(a[2]);
        System.out.println(a[3]);
        a = Question.SplitA("");
        System.out.println(a[0]);
        a = Question.SplitA("iii");
        System.out.println(a[0]);
        a = Question.SplitA("[][][]");
        System.out.println(a.length);
    }

    @Test
    public void randomCodeTest(){
        designer.randomCode();
    }

    @Test
    public void askTypeTest(){
        examEditor.askType();
    }

    @Test
    public void askFlagTest(){
        examEditor.askFlag();
    }

    @Test
    public void PackTest(){
        System.out.println(Pack.pack(new String[]{"aaaa","bbbb","cccc","dddd"}));
    }
}
