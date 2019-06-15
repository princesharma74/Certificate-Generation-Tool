package basic_image_Edit;
//import java.io.File;
import java.awt.*;
import javax.swing.*;
public class ImageEdit extends JFrame // because we are editing the imbuilt JFrame class
{
    JTextArea m_resultArea = new JTextArea(6, 30);
    public static final long serialVersionUID = 1L;
    //static JFrame f;
    //static JLabel l;
    ImageEdit(){}// constructor
    public static void main(String[] args){
        //File f = new File("C:/Users/user/Desktop/Prince Sharma/Sample_certi.jpg");
        JFrame f = new JFrame("Frame_Name");
        ImageIcon i = new ImageIcon("C:/Users/user/Desktop/Prince Sharma/Sample_certi.jpg");
        ImageLabel l = new ImageLabel("C:/Users/user/Desktop/Prince Sharma/Sample_certi.jpg");
        l.setIcon(i);        
        l.setPreferredSize(new Dimension(1366, 768));
        JPanel p = new JPanel();
        p.add(l);
        f.add(p);
        f.setSize(1366, 768);
        f.setVisible(true);
    }
}
