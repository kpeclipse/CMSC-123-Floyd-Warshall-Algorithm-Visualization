import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;

public class AdjustmentsPanel extends JPanel {
    private JRadioButton dGraph;
    private JRadioButton uGraph;
    private JSlider slider;

    public AdjustmentsPanel(Window w) {
        setLayout(null);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

        setRadioButtons();
        setSimulationSpeed();
    }

    public void disableRadioButton(boolean directed) {
        if (directed)
            uGraph.setEnabled(false);
        else
            dGraph.setEnabled(false);
    }

    public void enableRadioButton(boolean directed) {
        if (directed)
            uGraph.setEnabled(true);
        else
            dGraph.setEnabled(true);
    }

    private void setRadioButtons() {
        dGraph = new JRadioButton("DIRECTED GRAPH");
        dGraph.setFont(new Font("Arial", Font.PLAIN, 13));
        dGraph.setContentAreaFilled(false);
        dGraph.setFocusPainted(false);
        dGraph.setBounds(10, 60, 180, 30);

        uGraph = new JRadioButton("UNDIRECTED GRAPH");
        uGraph.setFont(new Font("Arial", Font.PLAIN, 13));
        uGraph.setContentAreaFilled(false);
        uGraph.setFocusPainted(false);
        uGraph.setBounds(10, 80, 180, 30);
        dGraph.isSelected();

        ButtonGroup G = new ButtonGroup();
        G.add(dGraph);
        G.add(uGraph);

        add(dGraph);
        add(uGraph);
        dGraph.setSelected(true);
    }

    private void setSimulationSpeed() {
        JLabel simulationSpeed = new JLabel("SIMULATION SPEED");
        simulationSpeed.setFont(new Font("Arial", Font.PLAIN, 15));
        simulationSpeed.setBounds(30, 115, 150, 30);

        JLabel fast = new JLabel("Fast");
        fast.setFont(new Font("Arial", Font.PLAIN, 13));
        fast.setBounds(80, 155, 150, 30);

        JLabel slow = new JLabel("Slow");
        slow.setFont(new Font("Arial", Font.PLAIN, 13));
        slow.setBounds(80, 335, 150, 30);

        slider = new JSlider(JSlider.VERTICAL, 1, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(false);
        slider.setBounds(30, 160, 30, 200);

        add(fast);
        add(slow);
        add(simulationSpeed);
        add(slider);
    }

    public int getSpeed() {
        return slider.getValue();
    }

    public boolean getSelected() {
        if (dGraph.isSelected())
            return true;
        else if (uGraph.isSelected())
            return false;
        return false;
    }
}