import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.lang.Math;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

// Graph Visualisation
public class GraphPanel extends JPanel {
    private Window window;
    private JPanel canvasPanel;
    private String first = null;
    private String second = null;

    private int startX = 50, startY = 50;
    private int endX = 530, endY = 665;
    private int k = -1, x = -1, y = -1;

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
                setBounds(0, 0, 550, 725);
                // if graph is not empty
                if (window.graph != null) {
                    // DISPLAY VERTICES
                    if (window.graph.vertices != null) {
                        for (int i = 0; i < window.graph.vertices.size(); i++) {
                            Graphics2D circle = (Graphics2D) g;
                            Stroke prevStroke = circle.getStroke();
                            circle.setStroke(new BasicStroke(3));
                            circle.setColor(Color.BLACK);
                            circle.drawOval(window.graph.vertices.get(i).getX() - 15,
                                    window.graph.vertices.get(i).getY() - 15, 30, 30);
                            circle.setStroke(prevStroke);

                            // For Algorithm Tracing
                            if (window.floydWarshall != null && window.floydWarshall.isRunning()) {
                                if (i == x)
                                    g.setColor(Color.BLUE);
                                else if (i == k)
                                    g.setColor(Color.RED);
                                else if (i == y)
                                    g.setColor(Color.GREEN);
                                else
                                    g.setColor(Color.PINK);
                            } else
                                g.setColor(Color.PINK);

                            g.fillOval(window.graph.vertices.get(i).getX() - 15,
                                    window.graph.vertices.get(i).getY() - 15, 30, 30);
                            g.setColor(Color.BLACK);
                            g.drawString(window.graph.vertices.get(i).key, window.graph.vertices.get(i).getX() - 10,
                                    window.graph.vertices.get(i).getY() - 20);
                        }
                    }

                    // DISPLAY EDGES
                    if (window.graph.edges != null) {
                        for (int i = 0; i < window.graph.edges.size(); i++) {
                            g.setColor(Color.BLACK);

                            double from = angleBetween(window.graph.edges.get(i).first.getX(),
                                    window.graph.edges.get(i).first.getY(), window.graph.edges.get(i).second.getX(),
                                    window.graph.edges.get(i).second.getY());
                            double to = angleBetween(window.graph.edges.get(i).second.getX(),
                                    window.graph.edges.get(i).second.getY(), window.graph.edges.get(i).first.getX(),
                                    window.graph.edges.get(i).first.getY());

                            Vertex first = window.graph.edges.get(i).first;
                            Vertex second = window.graph.edges.get(i).second;
                            double weight = window.graph.edges.get(i).value;

                            if (!window.graph.isDirected()) { // If graph is undirected
                                g.drawLine(getNewX(first.getX(), from, 15), getNewY(first.getY(), from, 15),
                                        getNewX(second.getX(), to, 15), getNewY(second.getY(), to, 15));
                            } else // If graph is directed
                                drawArrowLine(g, getNewX(first.getX(), from, 15), getNewY(first.getY(), from, 15),
                                        getNewX(second.getX(), to, 15), getNewY(second.getY(), to, 15), 5, 5);

                            g.setColor(new Color(102, 0, 153)); // Purple
                            if ((first.getX() > second.getX() && first.getY() <= second.getY())
                                    || (first.getX() > second.getX() && first.getY() > second.getY()))
                                g.drawString(Double.toString(weight), ((first.getX() + second.getX()) / 2) - 25,
                                        (first.getY() + second.getY()) / 2);
                            else if ((first.getX() <= second.getX() && first.getY() <= second.getY())
                                    || (first.getX() <= second.getX() && first.getY() > second.getY()))
                                g.drawString(Double.toString(weight), ((first.getX() + second.getX()) / 2) + 15,
                                        (first.getY() + second.getY()) / 2);
                        }
                    }
                    updateUI();
                }
                setPreferredSize(new Dimension(550, 700));
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

                            String check = null;
                            check = checkVertex(e.getX(), e.getY());

                            if (check == null) {
                                if (window.graph.vertices == null
                                        || (window.graph.vertices != null && window.graph.vertices.size() < 50)) { // max
                                    do {
                                        element = JOptionPane.showInputDialog("Vertex Name");
                                    } while (element != null && element.replaceAll("\\s", "").equals(""));
                                }
                            } else
                                canAddVertex = false;

                            if (element != null) {
                                if (window.graph.vertices != null)
                                    for (Vertex v : window.graph.vertices) { // check if vertex exists
                                        if (v.key.equals(element)) {
                                            JOptionPane.showMessageDialog(null, "VERTEX EXISTS");
                                            canAddVertex = false;
                                        }
                                    }
                            } else
                                canAddVertex = false;

                            if (canAddVertex) {// if vertex is unique
                                int x = e.getX(), y = e.getY();
                                if (x < startX)
                                    x = 30;
                                if (y < startY)
                                    y = 30;
                                if (x > endX)
                                    x = endX;
                                if (y > endY)
                                    y = endY;
                                window.graph.addVertex(element, x, y);
                            }

                            break;

                        // ADDING AN EDGE
                        case 3:
                            if (first == null) { // Choose first vertex
                                first = checkVertex(e.getX(), e.getY());
                                window.adjustments.setSourceVertex(first);
                            }

                            else { // First vertex chosen
                                boolean reset = false;
                                boolean canAddEdge = true;
                                second = checkVertex(e.getX(), e.getY()); // choose second vertex

                                if (second != null) { // Two vertices chosen
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
                                        window.adjustments.setDestinationVertex(second);
                                        // Ask for weight
                                        try {
                                            weight = JOptionPane.showInputDialog("Edge Weight");
                                            if (weight != null) // In case user clicks "Cancel"
                                                window.graph.addEdge(first, second, Double.parseDouble(weight));

                                        } catch (NumberFormatException error) { // In case weight is not double
                                            JOptionPane.showMessageDialog(null, "INVALID WEIGHT");
                                        }
                                    }

                                    reset = true;
                                }

                                else
                                    reset = true;

                                if (reset) {
                                    window.adjustments.resetVertices();
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
                                        if (window.graph.vertices.size() == 0)
                                            window.buttons.nullGraph();
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
                                    window.adjustments.setSourceVertex(first);
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

                                            if (!window.graph.isDirected()) { // in case graph is undirected
                                                if (edge.first.key.equals(second) && edge.second.key.equals(first)) {
                                                    canRemoveEdge = true;
                                                    window.graph.removeEdge(edge.first, edge.second, edge.value);
                                                    break;
                                                }
                                            }
                                        }

                                        if (window.graph.edges.size() == 0)
                                            window.graph.edges = null;

                                        if (!canRemoveEdge) { // in case edge does not exist
                                            window.adjustments.setDestinationVertex(second);
                                            JOptionPane.showMessageDialog(null, "EDGE DOES NOT EXIST");
                                        }
                                    }

                                    else
                                        reset = true;

                                    if (reset) {
                                        window.adjustments.resetVertices();
                                        first = null; // Allowed to look for first vertex again
                                        second = null;
                                    }
                                }
                            }
                            break;
                    }
                }

                revalidate();
                repaint();
            }
        });
        panel.add(canvasPanel);
        return panel;

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

    public double angleBetween(int x1, int y1, int x2, int y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        double rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toRadians(Math.toDegrees(rotation) + 180);

        return rotation;
    }

    public int getNewX(int centerX, double angle, double radius) {
        angle = angle - Math.toRadians(90.0);
        return (int) Math.round((float) (centerX + Math.cos(angle) * radius));
    }

    public int getNewY(int centerY, double angle, double radius) {
        angle = angle - Math.toRadians(90.0);
        return (int) Math.round((float) (centerY + Math.sin(angle) * radius));
    }

    public void highlightVertices(int k, int i, int j) {
        x = i;
        y = j;
        this.k = k;
    }
}