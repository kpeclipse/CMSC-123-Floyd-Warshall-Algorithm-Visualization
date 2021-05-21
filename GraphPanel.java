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

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GraphPanel extends JPanel {
    private Window window;
    private JPanel canvasPanel;
    private JScrollPane canvas;
    private String first = null;
    private String second = null;

    private int startX = 50, startY = 50;
    private int endX = 520, endY = 695;

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
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                setBounds(0, 0, 550, 725);

                // if graph is not empty
                if (window.graph != null) {
                    // DISPLAY VERTICES
                    if (window.graph.vertices != null) {
                        for (int i = 0; i < window.graph.vertices.size(); i++) {

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

                            if (!window.graph.directed) { // If graph is undirected
                                g.drawLine(window.graph.edges.get(i).first.getX(),
                                        window.graph.edges.get(i).first.getY(), window.graph.edges.get(i).second.getX(),
                                        window.graph.edges.get(i).second.getY());
                            } else // If graph is directed
                                drawArrowLine(g, window.graph.edges.get(i).first.getX(),
                                        window.graph.edges.get(i).first.getY(), window.graph.edges.get(i).second.getX(),
                                        window.graph.edges.get(i).second.getY(), 5, 5);

                            g.drawString(Double.toString(window.graph.edges.get(i).value),
                                    (window.graph.edges.get(i).first.getX() + window.graph.edges.get(i).second.getX())
                                            / 2,
                                    (window.graph.edges.get(i).first.getY() + window.graph.edges.get(i).second.getY())
                                            / 2);
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
                if (window.graph != null) {
                    switch (window.getTool()) {
                        // ADDING A VERTEX
                        case 2:
                            String element = null;
                            boolean canAddVertex = true;

                            element = checkVertex(e.getX(), e.getY());

                            if (element == null) { // check if there is no overlapping vertex
                                do {
                                    element = JOptionPane.showInputDialog("Vertex Name (Max: 3)");
                                } while (element.length() > 3); // 3 characters only

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
                                        int x = e.getX(), y = e.getY();
                                        if (x < startX)
                                            x = 30;
                                        if (y < startY)
                                            y = 30;
                                        window.graph.addVertex(element, x, y);
                                    }
                                }
                            }

                            break;

                        // ADDING AN EDGE
                        case 3:
                            if (first == null) { // Choose first vertex
                                first = checkVertex(e.getX(), e.getY());
                            }

                            else { // First vertex chosen
                                boolean reset = false;
                                boolean canAddEdge = true;
                                second = checkVertex(e.getX(), e.getY()); // choose second vertex

                                if (second != null) { // Two vertices chosen
                                    reset = true;
                                    if (first.equals(second)) { // If user attempts to create a loop
                                        JOptionPane.showMessageDialog(null, "LOOP NOT ALLOWED");
                                        canAddEdge = false;
                                    } else {
                                        for (Edge edge : window.graph.edges) { // check if edge exists
                                            if (edge.first.key.equals(first) && edge.second.key.equals(second)) {
                                                JOptionPane.showMessageDialog(null, "EDGE EXISTS");
                                                canAddEdge = false;
                                                break;
                                            }
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

                        // Remove vertex
                        case 4:
                            String removeVertex = checkVertex(e.getX(), e.getY());
                            if (removeVertex != null) {
                                for (Vertex v : window.graph.vertices) {
                                    if (v.key.equals(removeVertex)) {
                                        window.graph.removeVertex(v);
                                        if (window.graph.vertices.size() == 0) {
                                            window.graph = null;
                                            window.floydWarshall = null;
                                        }
                                        break;
                                    }
                                }
                            }
                            break;

                        // Remove Edge
                        case 5:
                            if (window.graph.edges != null) {
                                if (first == null) { // Choose first vertex
                                    first = checkVertex(e.getX(), e.getY());
                                }

                                else { // First vertex chosen
                                    boolean reset = false;
                                    boolean canRemoveEdge = false;
                                    second = checkVertex(e.getX(), e.getY()); // choose second vertex

                                    if (second != null) { // Two vertices chosen
                                        reset = true;
                                        for (Edge edge : window.graph.edges) { // check if edge exists
                                            if (edge.first.key.equals(first) && edge.second.key.equals(second)) {
                                                canRemoveEdge = true;
                                                window.graph.removeEdge(edge.first, edge.second, edge.value);
                                                break;
                                            }

                                            if (!window.graph.directed) { // in case graph is undirected
                                                if (edge.first.key.equals(second) && edge.second.key.equals(first)) {
                                                    canRemoveEdge = true;
                                                    window.graph.removeEdge(edge.first, edge.second, edge.value);
                                                    break;
                                                }
                                            }
                                        }

                                        if (window.graph.edges.size() == 0)
                                            window.graph.edges = null;

                                        if (!canRemoveEdge) // in case edge does not exist
                                            JOptionPane.showMessageDialog(null, "EDGE DOES NOT EXIST");
                                    }

                                    else
                                        reset = true;

                                    if (reset) {
                                        first = null; // Allowed to look for first vertex again
                                        second = null;
                                    }
                                }
                            }
                            break;
                    }
                }

                if (window.graph != null) // If a graph exists
                    window.floydWarshall = new FloydWarshall(window.graph);
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

    // method to draw a directed arrowline
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = { x2, (int) xm, (int) xn };
        int[] ypoints = { y2, (int) ym, (int) yn };

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    // Check boundaries if user clicked on a vertex in canvas panel
    private String checkVertex(int x, int y) {
        Rectangle boundaries;

        if (window.graph != null) { // if graph exists
            if (window.graph.vertices != null) {
                for (int i = 0; i < window.graph.vertices.size(); i++) {
                    if (window.getTool() == 2) // to avoid overlapping when adding new vertices
                        boundaries = new Rectangle(window.graph.vertices.get(i).getX() - 40,
                                window.graph.vertices.get(i).getY() - 40, 80, 80);
                    else
                        boundaries = new Rectangle(window.graph.vertices.get(i).getX() - 15,
                                window.graph.vertices.get(i).getY() - 15, 30, 30);
                    if (boundaries.contains(new Point(x, y)))
                        return window.graph.vertices.get(i).key;
                }
            }
        }

        return null;
    }
}