import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet.ColorAttribute;

public class GraphPanel extends JPanel {
    private Window window;

    public GraphPanel(Window w) {
        window = w;
        setLayout(null);
        setBackground(Color.WHITE);

        setMouseListener();
    }

    public void setMouseListener() {
        addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                if (!window.input) {
                    setBackground(Color.WHITE);
                }
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                if (window.input) {
                    /*
                     * Node node = new Node("name"); NodeDrawing draw = new NodeDrawing(node);
                     * 
                     * draw.setLocation(e.getX(), e.getY());
                     */
                    JLabel draw = new JLabel();
                    draw.setBackground(Color.PINK);
                    draw.setSize(30, 30);
                    draw.setLocation(e.getX(), e.getY());
                    draw.setVisible(true);

                    add(draw);
                    revalidate();
                    repaint();

                }
            }

            public void mousePressed(MouseEvent e) {
            }
        });
    }

    // What font to use
    public Font useFont(String path, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}