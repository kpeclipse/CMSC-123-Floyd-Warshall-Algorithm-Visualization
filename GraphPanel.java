import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GraphPanel extends JPanel {
    private Window window;
    private JPanel canvasPanel;
    private JScrollPane canvas;
    private String first = null;
    private String second = null;

    public GraphPanel(Window w) {
        window = w;
        setLayout(null);
        add(setCanvasPanel());
    }

    public JPanel setCanvasPanel() {
        JPanel panel = new JPanel();
        panel.setSize(550, 725);

        // graphics component
        canvasPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                setBounds(0, 0, 550, 725);

                int endX = 520, endY = 695;

                // if graph is not empty
                if (window.graph != null) {
                    // DISPLAY VERTICES
                    if (window.graph.vertices != null) {
                        for (int i = 0; i < window.graph.vertices.size(); i++) {
                            if (window.graph.vertices.get(i).getX() > endX)
                                window.graph.vertices.get(i).setX(endX);
                            if (window.graph.vertices.get(i).getY() > endY)
                                window.graph.vertices.get(i).setY(endY);

                            g.setColor(Color.PINK);
                            g.fillOval(window.graph.vertices.get(i).getX() - 15,
                                    window.graph.vertices.get(i).getY() - 15, 30, 30);
                            g.setColor(Color.BLACK);
                            g.drawString(window.graph.vertices.get(i).key, window.graph.vertices.get(i).getX() - 10,
                                    window.graph.vertices.get(i).getY() - 15);
                        }
                    }

                    // DISPLAY EDGES
                    if (window.graph.edges != null) {
                        for (int i = 0; i < window.graph.edges.size(); i++) {
                            g.setColor(Color.BLACK);

                            if (window.graph.edges.get(i).first.equals(window.graph.edges.get(i).second))
                                g.drawArc(window.graph.edges.get(i).first.getX() - 10,
                                        window.graph.edges.get(i).first.getX() - 25, 20, 30, 0, 180);
                            else
                                g.drawLine(window.graph.edges.get(i).first.getX() + 15,
                                        window.graph.edges.get(i).first.getY(),
                                        window.graph.edges.get(i).second.getX() - 15,
                                        window.graph.edges.get(i).second.getY());

                            g.drawString(Double.toString(window.graph.edges.get(i).value),
                                    window.graph.edges.get(i).first.getX() + 20,
                                    window.graph.edges.get(i).first.getY());
                        }
                    }

                    canvasPanel.setPreferredSize(new Dimension(endX + 30, endY + 30));
                    updateUI();
                }
            }
        };

        canvasPanel.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                switch (window.tool) {
                    case 2:
                        String element = null;
                        boolean canAddVertex = true;

                        element = JOptionPane.showInputDialog("Vertex Insert");

                        if (element != null) {
                            if (window.graph.vertices != null) {
                                for (Vertex v : window.graph.vertices) { // check if vertex exists
                                    if (v.key.equals(element)) {
                                        JOptionPane.showMessageDialog(null, "VERTEX EXISTS");
                                        canAddVertex = false;
                                    }
                                }
                            }
                            if (canAddVertex) {// if vertex is unique
                                window.graph.addVertex(element, e.getX(), e.getY());
                                window.data.floydWarshall = new FloydWarshall(window.graph);
                            }
                        }

                        break;
                    case 3:
                        if (first == null) { // Choose first vertex
                            first = checkVertex(e.getX(), e.getY());
                        }

                        else { // First vertex chosen
                            boolean reset = false;
                            boolean canAddEdge = true;
                            second = checkVertex(e.getX(), e.getY()); // choose second vertex

                            if (second != null) { // Two vertices chosen
                                for (Edge edge : window.graph.edges) { // check if edge exists
                                    if (edge.first.key.equals(first) && edge.second.key.equals(second)) {
                                        JOptionPane.showMessageDialog(null, "EDGE EXISTS");
                                        canAddEdge = false;
                                        break;
                                    }
                                }

                                if (canAddEdge) { // if edge is unique
                                    String weight;

                                    // Ask for weight
                                    try {
                                        weight = JOptionPane.showInputDialog("Edge Weight");
                                        if (weight != null) // In case user clicks "Cancel"
                                            window.graph.addEdge(first, second, Double.parseDouble(weight));

                                    } catch (NumberFormatException error) { // In case weight is not double
                                        JOptionPane.showMessageDialog(null, "INVALID WEIGHT");
                                    }

                                    reset = true;
                                }
                            }

                            else
                                reset = true;

                            if (reset) {
                                first = null; // Allowed to look for first vertex again
                                second = null;
                            }
                        }
                        break;
                }

                revalidate();
                repaint();
            }
        });

        canvas = new JScrollPane();
        canvas.setBorder(null);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                canvas.setBounds(0, 0, (int) e.getComponent().getWidth(), (int) e.getComponent().getHeight() - 98);
            }
        });

        canvasPanel.add(canvas);
        panel.add(canvasPanel);
        return panel;
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

    // Check boundaries if user clicked on a vertex in canvas panel
    private String checkVertex(int x, int y) {
        for (int i = 0; i < window.graph.vertices.size(); i++) {
            Rectangle boundaries = new Rectangle(window.graph.vertices.get(i).getX() - 15,
                    window.graph.vertices.get(i).getY() - 15, 30, 30);
            if (boundaries.contains(new Point(x, y)))
                return window.graph.vertices.get(i).key;
        }

        return null;
    }
}