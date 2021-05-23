import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    private static int width = 1200, height = 725;

    private int tool;

    ButtonPanel buttons;
    AdjustmentsPanel adjustments;
    GraphPanel display;
    ResultPanel data;
    AlgorithmPanel algorithm;

    protected Graph graph;
    protected FloydWarshall floydWarshall;

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
        // setting the buttonspanel, simulation adjustments panel, and graph display
        // panel
        buttons = new ButtonPanel(this);
        adjustments = new AdjustmentsPanel(this);
        display = new GraphPanel(this);
        data = new ResultPanel(this);
        algorithm = new AlgorithmPanel(this);

        buttons.setBounds(950, 0, 250, 300);
        adjustments.setBounds(950, 300, 250, 425);
        display.setBounds(400, 0, 550, height);
        data.setBounds(0, 0, 400, 425);
        algorithm.setBounds(0, 425, 400, 300);

        add(buttons);
        add(adjustments);
        add(display);
        add(data);
        add(algorithm);
    }

    // JPanel Settings
    public JPanel setPanel(int x, int y, int w, int h, Color color, String name) {
        JPanel thePanel = new JPanel();
        thePanel.setBounds(x, y, w, h);
        thePanel.setLayout(null);
        thePanel.setBackground(color);
        return thePanel;
    }

    public boolean isDirected() {
        return adjustments.getSelected();
    }

    public void setTool(int t) {
        tool = t;
    }

    public int getTool() {
        return tool;
    }
}