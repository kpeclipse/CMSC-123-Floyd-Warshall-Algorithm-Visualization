import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ResultPanel extends JPanel {
    private Window window;
    private JPanel tablePanel;
    private JScrollPane scrollPane;

    public ResultPanel(Window w) {
        window = w;
        setLayout(null);
        add(setTablePanel());
    }

    public JPanel setTablePanel() {
        JPanel panel = new JPanel();
        panel.setSize(400, 425);

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
                        g.drawRect(tableStartX, tableStartY, 50, 50);
                        g.drawString(window.graph.vertices.get(i).key, labelStartX, 20);
                        g.drawString(window.graph.vertices.get(i).key, 20, labelStartY);
                        g.drawString(Double.toString(window.floydWarshall.dist[0][i]), valueStartX, valueStartY);

                        for (int j = 1; j < window.graph.vertices.size(); j++) {
                            valueStartY += 50;
                            g.drawRect(tableStartX + 50, tableStartY, 50, 50);
                            g.drawString(Double.toString(window.floydWarshall.dist[j][i]), valueStartX, valueStartY);
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
        scrollPane.setPreferredSize(new Dimension(395, 420));

        panel.add(scrollPane);
        return panel;
    }

}