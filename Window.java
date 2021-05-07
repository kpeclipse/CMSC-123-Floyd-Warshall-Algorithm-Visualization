import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class Window extends JFrame {
    protected static int width = 1200, height = 725;

    private JPanel buttons;
    private JPanel adjustments;
    private JPanel display;
    private JPanel algorithm;
    private JPanel data;

    private JButton inputGraph;
    private JButton inputVertex;
    private JButton inputEdge;
    private JButton removeVertex;
    private JButton removeEdge;
    private JButton start;

    private JRadioButton dGraph;
    private JRadioButton uGraph;

    public Window() {
        super("Floyd-Warshall Algorithm Visualization");
        setFrame();
        setPanels();
        setButtons();
        setAdjustments();
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
        buttons = setPanel(950, 0, 250, 300, Color.BLACK, "buttons");
        adjustments = setPanel(950, 300, 250, 425, Color.BLACK, "adjustments");
        display = setPanel(400, 0, 550, height, Color.LIGHT_GRAY, "display");
        algorithm = setPanel(0, 0, 400, 425, Color.BLACK, "algorithm");
        data = setPanel(0, 425, 400, 300, Color.BLUE, "data");

        add(buttons);
        add(adjustments);
        add(display);
        add(algorithm);
        add(data);
    }

    // setting up a panel
    public JPanel setPanel(int x, int y, int w, int h, Color color, String name) {
        JPanel thePanel = new JPanel();
        thePanel.setBounds(x, y, w, h);
        if (name == "buttons")
            thePanel.setLayout(new GridLayout(6, 1));
        else
            thePanel.setLayout(null);
        thePanel.setBackground(color);
        return thePanel;
    }

    public void setButtons() {
        inputGraph = setButton("INPUT GRAPH",
                useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        inputVertex = setButton("INPUT VERTEX",
                useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        inputEdge = setButton("INPUT EDGE",
                useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        removeVertex = setButton("REMOVE VERTEX",
                useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        removeEdge = setButton("REMOVE EDGE",
                useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        start = setButton("START", useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));

        buttons.add(inputGraph);
        buttons.add(inputVertex);
        buttons.add(inputEdge);
        buttons.add(removeVertex);
        buttons.add(removeEdge);
        buttons.add(start);
    }

    public void setAdjustments() {
        dGraph = new JRadioButton("Directed Graph");
        uGraph = new JRadioButton("Undirected Graph");

        dGraph.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        uGraph.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));

        dGraph.setForeground(Color.WHITE);
        uGraph.setForeground(Color.WHITE);

        dGraph.setContentAreaFilled(false);
        uGraph.setContentAreaFilled(false);

        dGraph.setFocusPainted(false);
        uGraph.setFocusPainted(false);

        dGraph.setBounds(10, 20, 150, 30);
        uGraph.setBounds(10, 50, 150, 30);

        JLabel simulationSpeed = new JLabel("Simulation Speed");
        simulationSpeed.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        simulationSpeed.setForeground(Color.WHITE);
        simulationSpeed.setBounds(30, 90, 150, 30);

        JLabel fast = new JLabel("Fast");
        fast.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        fast.setForeground(Color.WHITE);
        fast.setBounds(120, 140, 150, 30);

        JLabel slow = new JLabel("Slow");
        slow.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        slow.setForeground(Color.WHITE);
        slow.setBounds(120, 330, 150, 30);

        JSlider slider = new JSlider(JSlider.VERTICAL, 0, 10, 5);
        slider.setBackground(Color.WHITE);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setBounds(60, 150, 30, 200);

        adjustments.add(fast);
        adjustments.add(slow);
        adjustments.add(simulationSpeed);
        adjustments.add(slider);
        adjustments.add(dGraph);
        adjustments.add(uGraph);
    }

    // setting up a button
    public JButton setButton(String name, Font font) {
        JButton theButton = new JButton(name);
        theButton.setFont(font);
        theButton.setForeground(Color.WHITE);
        theButton.setContentAreaFilled(false);
        theButton.setFocusPainted(false);
        return theButton;
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