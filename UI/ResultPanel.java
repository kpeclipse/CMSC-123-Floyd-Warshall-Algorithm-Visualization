package UI;

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
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));

        tablePanel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                int endX = 300, endY = 315;
                int tableStartX = 50, tableStartY = 100;
                int labelStartX = tableStartX + 20, labelStartY = tableStartY + 30;
                int valueStartX = 60, valueStartY = 125;

                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("COST TABLE", 20, 20);

                if (window.graph != null && window.graph.getVertices() != null) {
                    g.setColor(Color.BLACK);

                    for (int i = 0; i < window.graph.getVertices().size(); i++) {
                        tableStartX = 50;
                        valueStartY = 125;
                        // Draw first box in a row
                        g.drawRect(tableStartX, tableStartY, 60, 60);

                        g.setFont(new Font("Arial", Font.BOLD, 13));

                        // Write the vertices
                        g.drawString(window.graph.getVertices().get(i).getKey(), labelStartX, 80);
                        g.drawString(window.graph.getVertices().get(i).getKey(), 20, labelStartY);

                        // Write the edge weight
                        if (window.floydWarshall != null) // During Tracing
                            g.drawString(Double.toString(window.floydWarshall.getDist(0, i)), valueStartX, valueStartY);
                        else // Before Tracing
                            g.drawString(Double.toString(window.graph.getWeight(0, i)), valueStartX, valueStartY);

                        for (int j = 1; j < window.graph.getVertices().size(); j++) {
                            valueStartY += 60;
                            // Draw boxes
                            g.drawRect(tableStartX + 60, tableStartY, 60, 60);

                            // Write edge weights
                            if (window.floydWarshall != null)
                                g.drawString(Double.toString(window.floydWarshall.getDist(j, i)), valueStartX,
                                        valueStartY);
                            else
                                g.drawString(Double.toString(window.graph.getWeight(j, i)), valueStartX, valueStartY);
                            tableStartX += 60;

                            if (tableStartX > endX)
                                endX = tableStartX + 60;
                        }

                        tableStartY += 60;
                        labelStartX += 60;
                        labelStartY += 60;
                        valueStartX += 60;

                        if (tableStartY > endY) {
                            endY = tableStartY + 60;
                        }
                    }

                    // Highlight Edges
                    if (window.floydWarshall != null && window.floydWarshall.isRunning()) {
                        Graphics2D redBox = (Graphics2D) g;
                        Stroke prevRedStroke = redBox.getStroke();
                        redBox.setStroke(new BasicStroke(2));
                        redBox.setColor(Color.RED);
                        redBox.drawRect(50 + 60 * k, 100 + 60 * x, 60, 60); // dist[i][k]
                        redBox.drawRect(50 + 60 * y, 100 + 60 * k, 60, 60); // dist[k][j]
                        if (window.floydWarshall.satisfied()) {
                            redBox.setColor(new Color(0, 100, 0));
                        }
                        redBox.drawRect(50 + 60 * y, 100 + 60 * x, 60, 60); // dist[i][j]
                        redBox.setStroke(prevRedStroke);
                    }
                }
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