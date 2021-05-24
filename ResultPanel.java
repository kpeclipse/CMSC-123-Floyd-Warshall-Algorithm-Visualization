import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ResultPanel extends JPanel {
    private Window window;
    private JPanel tablePanel;
    private JScrollPane scrollPane;

    private int x = -1, y = -1, k = -1;

    public ResultPanel(Window w) {
        window = w;
        setLayout(null);
        add(setTablePanel());
    }

    public JPanel setTablePanel() {
        JPanel panel = new JPanel();
        panel.setSize(400, 425);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));

        tablePanel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                int endX = 300, endY = 315;
                int tableStartX = 50, tableStartY = 30;
                int labelStartX = tableStartX + 20, labelStartY = tableStartY + 30;
                int valueStartX = 60, valueStartY = 55;

                if (window.graph != null && window.graph.vertices != null) {
                    g.setColor(Color.BLACK);

                    for (int i = 0; i < window.graph.vertices.size(); i++) {
                        tableStartX = 50;
                        valueStartY = 55;
                        // Draw first box in a row
                        g.drawRect(tableStartX, tableStartY, 50, 50);

                        Font font = new Font("Arial", Font.PLAIN, 13);
                        g.setFont(font);

                        // Write the vertices
                        g.drawString(window.graph.vertices.get(i).key, labelStartX, 20);
                        g.drawString(window.graph.vertices.get(i).key, 20, labelStartY);

                        // Write the edge weight
                        if (window.floydWarshall != null)
                            g.drawString(Double.toString(window.floydWarshall.getDist(0, i)), valueStartX, valueStartY); // during
                                                                                                                         // and
                                                                                                                         // after
                                                                                                                         // tracing
                        else
                            g.drawString(Double.toString(window.graph.weights[0][i]), valueStartX, valueStartY); // before
                                                                                                                 // tracing

                        for (int j = 1; j < window.graph.vertices.size(); j++) {
                            valueStartY += 50;
                            // Draw boxes
                            g.drawRect(tableStartX + 50, tableStartY, 50, 50);

                            // Write edge weights
                            if (window.floydWarshall != null)
                                g.drawString(Double.toString(window.floydWarshall.getDist(j, i)), valueStartX,
                                        valueStartY);
                            else
                                g.drawString(Double.toString(window.graph.weights[j][i]), valueStartX, valueStartY);
                            tableStartX += 50;

                            if (tableStartX > endX)
                                endX = tableStartX + 50;
                        }

                        tableStartY += 50;
                        labelStartX += 50;
                        labelStartY += 50;
                        valueStartX += 50;

                        if (tableStartY > endY) {
                            endY = tableStartY + 50;
                        }
                    }

                    if (window.floydWarshall != null && window.floydWarshall.isRunning()) {
                        Graphics2D redBox = (Graphics2D) g;
                        Stroke prevRedStroke = redBox.getStroke();
                        redBox.setStroke(new BasicStroke(2));
                        redBox.setColor(Color.RED);
                        redBox.drawRect(50 + 50 * k, 30 + 50 * x, 50, 50);
                        redBox.drawRect(50 + 50 * y, 30 + 50 * k, 50, 50);
                        if (window.floydWarshall.satisfied()) {
                            redBox.setColor(Color.GREEN);
                        }
                        redBox.drawRect(50 + 50 * y, 30 + 50 * x, 50, 50);
                        redBox.setStroke(prevRedStroke);
                        // g.setColor(Color.RED);
                        // g.drawRect(50 + 50 * x, 30 + 50 * y, 50, 50);
                        // g.drawRect(50 + 50 * x, 30 + 50 * k, 50, 50);
                        // g.drawRect(50 + 50 * k, 30 + 50 * y, 50, 50);
                    }
                }

                // if (window.graph.vertices != null)
                // if (floydWarshall.dist != null) {
                // for (int i = 0; i < window.graph.vertices.size(); i++)
                // for (int j = 0; j < window.graph.vertices.size(); i++) {
                // g.setColor(Color.BLACK);
                // g.drawRect(tableStartX, tableStartY, 10, 10);
                // tableStartX += 10;
                // tableStartY += 10;
                // }
                // }
                setPreferredSize(new Dimension(endX + 60, endY + 30));
                revalidate();
                repaint();
            }
        };

        scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(390, 415));

        panel.add(scrollPane);
        return panel;
    }

    public void paintTablePanel(int k, int i, int j) {
        x = i;
        y = j;
        this.k = k;
    }
}