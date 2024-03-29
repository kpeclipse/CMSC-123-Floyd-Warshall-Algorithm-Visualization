package UI;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algorithm.FloydWarshall;
import DataStructure.Edge;
import DataStructure.Graph;
import DataStructure.GraphReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

public class ButtonPanel extends JPanel {
    private Window window;
    private GraphReader readg;

    private JLabel tools;
    private JButton inputGraph;
    private JButton inputVertex;
    private JButton inputEdge;
    private JButton removeVertex;
    private JButton removeEdge;
    // private JButton adjust;
    private JButton start;
    private JButton reset;

    public ButtonPanel(Window w) {
        window = w;

        setLayout(new GridLayout(8, 1));
        setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
        // setBackground(Color.BLACK);

        tools = new JLabel("TOOLS", SwingConstants.CENTER);
        tools.setFont(new Font("Arial", Font.BOLD, 13));
        add(tools);

        setButtons();
        setActionAndMouseListeners();
    }

    // Initialize buttons
    public void setButtons() {
        // adjust = setButton("ADJUST", new Font("Arial", Font.PLAIN, 13));
        inputGraph = setButton("INPUT GRAPH", new Font("Arial", Font.PLAIN, 13));
        inputVertex = setButton("INPUT VERTEX", new Font("Arial", Font.PLAIN, 13));
        inputEdge = setButton("INPUT EDGE", new Font("Arial", Font.PLAIN, 13));
        removeVertex = setButton("REMOVE VERTEX", new Font("Arial", Font.PLAIN, 13));
        removeEdge = setButton("REMOVE EDGE", new Font("Arial", Font.PLAIN, 13));
        start = setButton("START", new Font("Arial", Font.PLAIN, 13));
        reset = setButton("RESET", new Font("Arial", Font.PLAIN, 13));

        // add(adjust);
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
        theButton.setForeground(Color.BLACK);
        // theButton.setContentAreaFilled(false);
        theButton.setFocusPainted(false);
        return theButton;
    }

    public void setActionAndMouseListeners() {
        // adjust.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // window.setTool(0);
        // if (window.floydWarshall != null)
        // window.floydWarshall = null;
        // }
        // });

        inputGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // opens jfile chooser to parse graph
                window.setTool(1);
                window.adjustments.updateDisplay(1);
                createGraph();

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
                    readg = new GraphReader(window, graphFile);
                    window.graph = readg.getGraph();
                    window.display.repaint();
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    // System.out.println("Cancel was selected");
                }

            }
        });

        inputVertex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(2);
                window.adjustments.updateDisplay(2);
                // creates a graph
                createGraph();
                nullFloydWarshall();
            }
        });

        inputEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(3);
                window.adjustments.updateDisplay(3);
                // creates a directed graph
                // if in case graph has only one or no vertices
                if (window.graph != null && window.graph.getVertices() != null && window.graph.getVertices().size() > 1
                        && window.graph.getEdges() == null) {
                    window.graph.setEdges(new ArrayList<Edge>());
                }

                nullFloydWarshall();
            }
        });

        removeVertex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(4);
                window.adjustments.updateDisplay(4);
                nullFloydWarshall();
            }
        });

        removeEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(5);
                window.adjustments.updateDisplay(5);
                nullFloydWarshall();
            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.adjustments.updateDisplay(6);
                window.setTool(6);
                if (window.graph != null && window.graph.getVertices() != null) {
                    if (window.floydWarshall == null) // Perform Algorithm for the first time
                        window.setFloydWarshall(new FloydWarshall(window.getGraph()));
                    else {
                        if (!window.floydWarshall.isRunning()) // Re-run algorithm
                            window.floydWarshall.useGraph(window.graph);
                    }

                    if (!window.floydWarshall.isRunning()) // In case user clicks start button while running
                        window.floydWarshall.start(window);
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setTool(7);
                window.adjustments.updateDisplay(7);
                if (window.graph != null) {
                    nullFloydWarshall();
                    nullGraph();
                }
            }
        });

        // adjust.addMouseListener(new MouseListener() {
        // public void mouseEntered(MouseEvent e) {
        // adjust.setForeground(Color.WHITE);
        // adjust.setContentAreaFilled(true);
        // adjust.setBackground(Color.BLUE);
        // }

        // public void mouseExited(MouseEvent e) {
        // adjust.setForeground(Color.BLACK);
        // // adjust.setContentAreaFilled(false);
        // adjust.setBackground(null);
        // }

        // public void mouseReleased(MouseEvent e) {
        // // adjust.setContentAreaFilled(false);
        // adjust.setForeground(Color.WHITE);
        // }

        // public void mouseClicked(MouseEvent e) {
        // }

        // public void mousePressed(MouseEvent e) {
        // }
        // });

        inputGraph.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputGraph.setToolTipText("Choose an existing graph file");
                inputGraph.setForeground(Color.WHITE);
                inputGraph.setContentAreaFilled(true);
                inputGraph.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                inputGraph.setForeground(Color.BLACK);
                // inputGraph.setContentAreaFilled(false);
                inputGraph.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                // inputGraph.setContentAreaFilled(false);
                inputGraph.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        inputVertex.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputVertex.setToolTipText("Click anywhere on the area to place a new vertex");
                inputVertex.setForeground(Color.WHITE);
                inputVertex.setContentAreaFilled(true);
                inputVertex.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                inputVertex.setForeground(Color.BLACK);
                // inputVertex.setContentAreaFilled(false);
                inputVertex.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                inputVertex.setForeground(Color.WHITE);
                // inputVertex.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        inputEdge.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                inputEdge.setToolTipText(
                        "Choose a source vertex then a destination vertex to create an edge (NOTE: An edge from a vertex to self is not allowed)");
                inputEdge.setForeground(Color.WHITE);
                inputEdge.setContentAreaFilled(true);
                inputEdge.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                inputEdge.setForeground(Color.BLACK);
                // inputEdge.setContentAreaFilled(false);
                inputEdge.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                inputEdge.setForeground(Color.WHITE);
                // inputEdge.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        removeVertex.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                removeVertex.setToolTipText("Click any vertex on the area to be removed");
                removeVertex.setForeground(Color.WHITE);
                removeVertex.setContentAreaFilled(true);
                removeVertex.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                removeVertex.setForeground(Color.BLACK);
                // removeVertex.setContentAreaFilled(false);
                removeVertex.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                removeVertex.setForeground(Color.WHITE);
                // removeVertex.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        removeEdge.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                removeEdge.setToolTipText("Choose a source vertex then a destination vertex with an existing edge");
                removeEdge.setForeground(Color.WHITE);
                removeEdge.setContentAreaFilled(true);
                removeEdge.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                removeEdge.setForeground(Color.BLACK);
                // removeEdge.setContentAreaFilled(false);
                removeEdge.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                // removeEdge.setContentAreaFilled(false);
                removeEdge.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        start.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                start.setToolTipText("Run the algorithm");
                start.setForeground(Color.WHITE);
                start.setContentAreaFilled(true);
                start.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                start.setForeground(Color.BLACK);
                // start.setContentAreaFilled(false);
                start.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                // start.setContentAreaFilled(false);
                start.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });

        reset.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                reset.setToolTipText("Reset the graph");
                reset.setForeground(Color.WHITE);
                reset.setContentAreaFilled(true);
                reset.setBackground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                reset.setForeground(Color.BLACK);
                // reset.setContentAreaFilled(false);
                reset.setBackground(null);
            }

            public void mouseReleased(MouseEvent e) {
                // reset.setContentAreaFilled(false);
                reset.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }
        });
    }

    public void createGraph() {
        if (window.graph == null) { // if in case user clicks button again and there is an existing graph
            // window.graph = new Graph(true);
            window.graph = new Graph(window.isDirected());
            window.adjustments.disableRadioButton(window.adjustments.getSelected());
        }
    }

    public void nullGraph() {
        window.adjustments.enableRadioButton(window.graph.isDirected());
        window.graph = null;
    }

    public void nullFloydWarshall() {
        if (window.floydWarshall != null) {
            window.floydWarshall.stop();
            window.floydWarshall = null;
        }
    }
}