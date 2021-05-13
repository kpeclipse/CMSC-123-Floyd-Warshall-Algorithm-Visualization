import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;


public class ResultPanel extends JPanel{
    private Window window;
    private JPanel tablePanel;
    public FloydWarshall floydWarshall;
    private JScrollPane scrollPane;

    public ResultPanel(Window w) {
        window = w;
        setLayout(null);
        add(setTablePanel());
    }

    public JPanel setTablePanel(){
        JPanel panel = new JPanel();
        panel.setSize(400, 425);

        tablePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                setBounds(0, 0, 400, 425);

                int endX = 400, endY = 425;
                int tableStartX = 50, tableStartY = 30;
                int labelStartX = tableStartX + 20;

                if (window.graph != null && window.graph.vertices != null) {
                    g.setColor(Color.BLACK);
                    for (int i = 0; i < window.graph.vertices.size(); i++) {
                        tableStartX = 50;
                        g.drawRect(tableStartX, tableStartY, 50, 50);
                        g.drawString(window.graph.vertices.get(i).key, labelStartX, 20);
                        g.drawString(window.graph.vertices.get(i).key, 20, labelStartX);

                        for (int j = 1; j < window.graph.vertices.size(); j++) {
                            g.drawRect(tableStartX + 50, tableStartY, 50, 50);
                            tableStartX += 50;
                        }
                        tableStartY += 50;
                        labelStartX += 50;
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

                tablePanel.setPreferredSize(new Dimension(endX + 30, endY + 30));
                
                updateUI();
            }
        };
        scrollPane = new JScrollPane(tablePanel,   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(400, 425);
        
        panel.add(scrollPane);
        return panel;
    }


}