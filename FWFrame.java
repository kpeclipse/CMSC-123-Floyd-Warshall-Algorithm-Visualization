import javax.swing.*;
import java.awt.*;

public class FWFrame extends JFrame{
    private JButton addVertex;
    private JButton addEdge;
    private JButton removeVertex;
    private JButton removeEdge;
    private JButton algo;
    private JButton reset;
    private JButton uGraph;
    private JButton dGraph;
    private JPanel visualization;
    private JPanel buttons;
    private JPanel matrix;
    private JPanel threeNodes;
    private JPanel description;
    private JTextArea desc;
    private Font font = new Font("Product Sans Regular", Font.BOLD, 10);

    public void setButtons(){
        addVertex = new JButton("Add Vertex");
        addVertex.setFocusPainted(false);
        addVertex.setBackground(null);
        addVertex.setFont(font);

        addEdge = new JButton("Add Edge");
        addEdge.setFocusPainted(false);
        addEdge.setBackground(null);
        addEdge.setFont(font);

        removeEdge = new JButton("Remove Edge");
        removeEdge.setFocusPainted(false);
        removeEdge.setBackground(null);
        removeEdge.setFont(font);

        removeVertex = new JButton("Remove Vertex");
        removeVertex.setFocusPainted(false);
        removeVertex.setBackground(null);
        removeVertex.setFont(font);
        
        algo = new JButton("Show Algorithm");
        algo.setFocusPainted(false);
        algo.setBackground(null);
        algo.setFont(font);

        reset = new JButton("Reset");
        reset.setFocusPainted(false);
        reset.setBackground(null);
        reset.setFont(font);
        reset.setEnabled(false);

        uGraph = new JButton("Undirected Graph");
        uGraph.setFocusPainted(false);
        uGraph.setBackground(null);
        uGraph.setFont(font);

        dGraph = new JButton("Directed Graph");
        dGraph.setFocusPainted(false);
        dGraph.setBackground(null);
        dGraph.setFont(font);

        buttons.add(addVertex);
        buttons.add(removeVertex);
        buttons.add(addEdge);
        buttons.add(removeEdge);
        buttons.add(algo);
        visualization.add(reset);
        visualization.add(uGraph);
        visualization.add(dGraph);
    }

    public void setPanels(){
        matrix = new JPanel();
        matrix.setBackground(Color.DARK_GRAY);
        matrix.setBounds(0, 100, 600, 450);

        buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.setBounds(0, 50, 600, 50);
        buttons.setLayout(new GridLayout(1,3));

        visualization = new JPanel();
        visualization.setBackground(Color.WHITE);
        visualization.setBounds(600,0, 600, 700);

        threeNodes = new JPanel();
        threeNodes.setBackground(Color.LIGHT_GRAY);
        threeNodes.setBounds(0, 550, 600, 115);
        
        description = new JPanel();
        description.setBackground(Color.LIGHT_GRAY);
        description.setBounds(0,0,600,50);
        description.setLayout(new BorderLayout());

        add(matrix);
        add(buttons);
        add(visualization);
        add(threeNodes);
        add(description);
    }

    public void setLabels(){
        desc = new JTextArea("Describe Floyd-Warshall keme");
        desc.setEditable(false);
        desc.setFont(font);
        desc.setOpaque(false);

        description.add(desc);
    }
    public FWFrame(){
        super("Floyd-Warshall Algorithm Visualization");
        setSize(1200, 700);
        setLayout(null);
        setLocation(50,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.BLACK);

        setPanels();
        setButtons();
        setLabels();

        setVisible(true);
    }

    public static void main(String[] args) {
        new FWFrame();
    }
}