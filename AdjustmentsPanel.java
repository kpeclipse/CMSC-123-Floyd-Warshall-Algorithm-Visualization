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
    private JLabel update;
    private JLabel first;
    private JLabel second;

    public AdjustmentsPanel(Window w) {
        setLayout(null);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

        setDisplay();
        setRadioButtons();
        setSimulationSpeed();
    }

    public void updateDisplay(int tool) {
        switch (tool) {
            case 1:
                update.setText("Tool: Insert Graph");
                emptyVertices();
                break;
            case 2:
                update.setText("Tool: Insert Vertex");
                emptyVertices();
                break;
            case 3:
                update.setText("Tool: Insert Edge");
                resetVertices();
                break;
            case 4:
                update.setText("Tool: Remove Vertex");
                emptyVertices();
                break;
            case 5:
                update.setText("Tool: Remove Edge");
                resetVertices();
                break;
            case 6:
                update.setText("Tool: Start");
                emptyVertices();
                break;
            case 7:
                update.setText("Tool: Reset");
                emptyVertices();
                break;
        }
    }

    public void resetVertices() {
        first.setText("Source Vertex:");
        second.setText("Destination Vertex:");
    }

    public void emptyVertices() { // In case source and destination vertices are not needed
        first.setText("");
        second.setText("");
    }

    public void setSourceVertex(String key) {
        if (key != null)
            first.setText("Source Vertex: " + key);
    }

    public void setDestinationVertex(String key) {
        second.setText("Destination Vertex: " + key);
    }

    public void setDisplay() {
        update = new JLabel("Tool:");
        first = new JLabel();
        second = new JLabel();

        update.setFont(new Font("Arial", Font.PLAIN, 13));
        first.setFont(new Font("Arial", Font.PLAIN, 13));
        second.setFont(new Font("Arial", Font.PLAIN, 13));

        update.setBounds(10, 5, 200, 18);
        first.setBounds(10, 23, 200, 18);
        second.setBounds(10, 41, 200, 18);
        add(update);
        add(first);
        add(second);
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
        dGraph.setBounds(10, 70, 180, 30);

        uGraph = new JRadioButton("UNDIRECTED GRAPH");
        uGraph.setFont(new Font("Arial", Font.PLAIN, 13));
        uGraph.setContentAreaFilled(false);
        uGraph.setFocusPainted(false);
        uGraph.setBounds(10, 90, 180, 30);
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
        simulationSpeed.setBounds(30, 125, 150, 30);

        JLabel fast = new JLabel("Fast");
        fast.setFont(new Font("Arial", Font.PLAIN, 13));
        fast.setBounds(80, 160, 150, 30);

        JLabel slow = new JLabel("Slow");
        slow.setFont(new Font("Arial", Font.PLAIN, 13));
        slow.setBounds(80, 330, 150, 30);

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