package GUI.Text;

import java.awt.*;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.text.AttributedCharacterIterator; 
import java.text.AttributedString;
import javax.swing.*;

public class TextPanel extends JComponent {

    private String _text;

    public TextPanel(){
    }

    public void draw(String text) {
        _text = text;
        paint(getGraphics());
    }

    private void clearText(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;            
        g2.setPaint(new Color(240,240,240));
        g2.fillRect(0, 0, getSize().width, getSize().height);
    }        

    @Override
    public void paint(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        FontRenderContext frc = g2.getFontRenderContext();
        Font font = new Font("Helvetica",Font.BOLD, 18);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Watch the margins
        Insets insets = getInsets();
        int width = getSize().width - 
        insets.right - insets.left;
        // Set the starting position to draw
        int x = insets.left;
        int y = insets.top;                                 

        if (_text != null) {   
            clearText(g);

            // Get iterator for string
            AttributedString attributedString = 
                new AttributedString(_text);                        
            attributedString.addAttribute(TextAttribute.FONT, font);
            AttributedCharacterIterator characterIterator = 
                attributedString.getIterator();
           
            // Create measurer
            LineBreakMeasurer measurer = 
                new LineBreakMeasurer(characterIterator, frc);            

            g2.setColor(Color.BLACK);
            
            while (measurer.getPosition() < 
                characterIterator.getEndIndex()) {
                
                // Get line
                TextLayout textLayout = 
                    measurer.nextLayout(width);
                // Move down to baseline
                y += textLayout.getAscent();
                // Draw line
                textLayout.draw(g2, x, y);
                // Move down to top of next line
                y += textLayout.getDescent() + 
                    textLayout.getLeading();
            }
        }
    }
    public void setText(String text){
        _text=text;
    }
}
