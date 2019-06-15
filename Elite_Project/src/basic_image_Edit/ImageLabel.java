package basic_image_Edit;
import java.awt.*;
import javax.swing.*;
public class ImageLabel extends JLabel
{
    JTextArea m_resultArea = new JTextArea(6, 30);
    public static final long serialVersionUID = 1L;
    private Image _myimage;
    
    public ImageLabel(String text){
        super(text);
    }
    
    public void setIcon(Icon icon){
        super.setIcon(icon);
        if(icon instanceof ImageIcon){
            _myimage = ((ImageIcon)icon).getImage();
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(_myimage, 0, 0, this.getWidth(), this.getHeight(),null);
    }
}
