import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    protected static int width = 1200, height = 725;

    private JPanel algorithm;
    private JPanel data;

    private ButtonPanel buttons;
    private AdjustmentsPanel adjustments;
    private GraphPanel display;

    protected boolean input = false;

    public Window() {
        super("Floyd-Warshall Algorithm Visualization");
        setFrame();

        setPanels();
        setVisible(true);
    }

    // JFrame Settings
    public void setFrame() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    public void setPanels() {
        buttons = new ButtonPanel(this);
        adjustments = new AdjustmentsPanel(this);
        display = new GraphPanel(this);
        algorithm = setPanel(0, 0, 400, 425, Color.BLACK, "algorithm");
        data = setPanel(0, 425, 400, 300, Color.BLUE, "data");

        buttons.setBounds(950, 0, 250, 300);
        adjustments.setBounds(950, 300, 250, 425);
        display.setBounds(400, 0, 550, height);

        add(buttons);
        add(adjustments);
        add(display);
        add(algorithm);
        add(data);
    }

    // JPanel Settings
    public JPanel setPanel(int x, int y, int w, int h, Color color, String name) {
        JPanel thePanel = new JPanel();
        thePanel.setBounds(x, y, w, h);
        thePanel.setLayout(null);
        thePanel.setBackground(color);
        return thePanel;
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