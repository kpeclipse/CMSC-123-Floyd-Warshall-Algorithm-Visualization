import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.filechooser.*;
import javax.swing.JFileChooser;

public class ButtonPanel extends JPanel {
    private Window window;
    private GraphReader readg;
    private AdjustmentsPanel adjustments;

    private JButton inputGraph;
    private JButton inputVertex;
    private JButton inputEdge;
    private JButton removeVertex;
    private JButton removeEdge;
    private JButton adjust;
    private JButton start;
    private JButton reset;

    public ButtonPanel(Window w) {
        window = w;

        setLayout(new GridLayout(8, 1));
        setBackground(Color.BLACK);

        setButtons();
        setActionAndMouseListeners();
    }

    // Initialize buttons
    public void setButtons() {
        adjust = setButton("ADJUST", useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));
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
        reset = setButton("RESET", useFont(System.getProperty("user.dir") + "/resources/BebasNeue-Regular.ttf", 20));

        add(adjust);
        add(inputGraph);
        add(inputVertex);
        add(inputEdge);
        add(removeVertex);
        add(removeEdge);
        add(start);
        add(reset);
    }

    // JButton Settings
    public JButton setButton(String name, Font font) {
        JButton theButton = new JButton(name);
        theButton.setFont(font);
        theButton.setForeground(Color.WHITE);
        theButton.setContentAreaFilled(false);
        theButton.setFocusPainted(false);
        return theButton;
    }

    public void setActionAndMouseListeners() {
        adjust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(0);
            }
        });

        inputGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // opens jfile chooser to parse graph
                window.setTool(1);
                JFileChooser j = new JFileChooser();

                // only allow files of .txt extension
                j.setAcceptAllFileFilterUsed(false);
                j.setDialogTitle("Select a .txt file");
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
                j.addChoosableFileFilter(restrict);
                int result = j.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // creates readgraph object and sets the window graph to the graph in that
                    // object
                    File graphFile = j.getSelectedFile();
                    readg = new GraphReader(graphFile);
                    window.graph = readg.g;
                    window.floydWarshall = new FloydWarshall(window.graph);
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Cancel was selected");
                }
                
                
                
            }
        });

        inputVertex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(2);
                // creates a directed graph
                if (window.graph == null) // if in case user clicks button again and there is an existing graph
                    // window.graph = new Graph(true);
                    if (window.isDirected()) {
                        window.graph = new Graph(true);
                    } else
                        window.graph = new Graph(false);
            }
        });

        inputEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(3);
                // creates a directed graph
                // if in case graph has only one or no vertices
                if (window.graph != null && window.graph.vertices != null && window.graph.vertices.size() > 1
                        && window.graph.edges == null)
                    window.graph.edges = new ArrayList<Edge>();
            }
        });

        removeVertex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(4);
            }
        });

        removeEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(5);
            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(6);
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(7);
                window.graph = null;
            }
        });

        adjust.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                adjust.setForeground(Color.BLACK);
                adjust.setContentAreaFilled(true);
                adjust.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                adjust.setForeground(Color.WHITE);
                adjust.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                adjust.setContentAreaFilled(false);
                adjust.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        inputGraph.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputGraph.setForeground(Color.BLACK);
                inputGraph.setContentAreaFilled(true);
                inputGraph.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                inputGraph.setForeground(Color.WHITE);
                inputGraph.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                inputGraph.setContentAreaFilled(false);
                inputGraph.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        inputVertex.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputVertex.setForeground(Color.BLACK);
                inputVertex.setContentAreaFilled(true);
                inputVertex.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                inputVertex.setForeground(Color.WHITE);
                inputVertex.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                inputVertex.setForeground(Color.WHITE);
                inputVertex.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        inputEdge.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputEdge.setForeground(Color.BLACK);
                inputEdge.setContentAreaFilled(true);
                inputEdge.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                inputEdge.setForeground(Color.WHITE);
                inputEdge.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                inputEdge.setForeground(Color.WHITE);
                inputEdge.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        removeVertex.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                removeVertex.setForeground(Color.BLACK);
                removeVertex.setContentAreaFilled(true);
                removeVertex.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                removeVertex.setForeground(Color.WHITE);
                removeVertex.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                removeVertex.setForeground(Color.WHITE);
                removeVertex.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        removeEdge.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                removeEdge.setForeground(Color.BLACK);
                removeEdge.setContentAreaFilled(true);
                removeEdge.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                removeEdge.setForeground(Color.WHITE);
                removeEdge.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                removeEdge.setContentAreaFilled(false);
                removeEdge.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        start.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                start.setForeground(Color.BLACK);
                start.setContentAreaFilled(true);
                start.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                start.setForeground(Color.WHITE);
                start.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                start.setContentAreaFilled(false);
                start.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        reset.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                reset.setForeground(Color.BLACK);
                reset.setContentAreaFilled(true);
                reset.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                reset.setForeground(Color.WHITE);
                reset.setContentAreaFilled(false);
            }

            public void mouseReleased(MouseEvent e) {
                reset.setContentAreaFilled(false);
                reset.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
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