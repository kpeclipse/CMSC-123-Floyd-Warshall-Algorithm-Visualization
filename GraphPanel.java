import java.awt.Color;
import java.awt.Dimension;
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

    public GraphPanel(Window w) {
        window = w;
        setLayout(null);

        setCanvasPanel();
    }

    public JPanel setCanvasPanel() {
        JPanel panel = new JPanel();
        panel.setSize(550, 725);
        
        //graphics component 
        canvasPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                setSize(400,400);
                
                // int endX = 520, endY = 695;

                //if graph is not empty
                // if (window.graph.vertices != null) {
                //     for (int i = 0; i < window.graph.vertices.size(); i++) {
                //         if (window.graph.vertices.get(i).getX() > endX)
                //             endX = window.graph.vertices.get(i).getX();
                //         if (window.graph.vertices.get(i).getY() > endY)
                //             endY = window.graph.vertices.get(i).getY();

                //         g.setColor(Color.PINK);
                //         g.fillOval(window.graph.vertices.get(i).getX() - 15, window.graph.vertices.get(i).getY() - 15, 30, 30);
                //         g.setColor(Color.BLACK);
                //         g.drawString(window.graph.vertices.get(i).key, window.graph.vertices.get(i).getX() + 15,
                //                 window.graph.vertices.get(i).getY() - 5);
                //     }

                //     canvasPanel.setPreferredSize(new Dimension(endX + 30, endX + 30));
                //     updateUI();
                // }
            }
        };
        panel.setBackground(Color.RED);

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
                if (window.tool == 2) {
                    String element = null;

                    element = JOptionPane.showInputDialog("Vertex Insert");
                    System.out.println(element);

                    if (element != null) {
                        if (window.graph != null)
                            window.graph = new Graph(true);
                        window.graph.addVertex(element, e.getX(), e.getY());
                    }
                }

                revalidate();
                repaint();
            }
        });
        
        add(panel);
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
}