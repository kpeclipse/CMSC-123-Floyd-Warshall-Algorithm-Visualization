import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;

public class AdjustmentsPanel extends JPanel {
    private Window window;
    public JRadioButton dGraph;
    public JRadioButton uGraph;

    public AdjustmentsPanel(Window w) {
        window = w;
        setLayout(null);
        setBackground(Color.BLACK);

        setRadioButtons();
        setSimulationSpeed();
    }

    private void setRadioButtons() {
        dGraph = new JRadioButton("Directed Graph");
        dGraph.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        dGraph.setForeground(Color.WHITE);
        dGraph.setContentAreaFilled(false);
        dGraph.setFocusPainted(false);
        dGraph.setBounds(10, 20, 150, 30);
        
        uGraph = new JRadioButton("Undirected Graph");
        uGraph.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
        uGraph.setForeground(Color.WHITE);
        uGraph.setContentAreaFilled(false);
        uGraph.setFocusPainted(false);
        uGraph.setBounds(10, 50, 150, 30);
        dGraph.isSelected();

        ButtonGroup G = new ButtonGroup();
        G.add(dGraph);
        G.add(uGraph);
        
        add(dGraph);
        add(uGraph);
        dGraph.setSelected(true);
    }

    private void setSimulationSpeed() {
        JLabel simulationSpeed = new JLabel("Simulation Speed");
        simulationSpeed.setFont(useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 25));
        simulationSpeed.setForeground(Color.WHITE);
        simulationSpeed.setBounds(30, 95, 150, 30);

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

        add(fast);
        add(slow);
        add(simulationSpeed);
        add(slider);
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