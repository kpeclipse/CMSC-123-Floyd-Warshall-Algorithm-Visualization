import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AlgorithmPanel extends JPanel {
    private Window window;
    private int k = 0, i = 0, j = 0;

    public AlgorithmPanel(Window w) {
        window = w;
    }

    public void getIndex(int k, int i, int j) {
        this.k = k;
        this.i = i;
        this.j = j;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("FLOYD-WARSHALL ALGORITHM:", 20, 20);

        if (window.floydWarshall != null) {
            if (window.floydWarshall.isRunning()) {
                Graphics2D circle = (Graphics2D) g;
                Stroke prevStroke = circle.getStroke();
                circle.setStroke(new BasicStroke(3));
                circle.setColor(Color.BLUE);
                circle.drawOval(60, 210, 40, 40); // i node
                circle.setColor(Color.RED);
                circle.drawOval(170, 160, 40, 40); // k node
                circle.setColor(Color.GREEN);
                circle.drawOval(280, 210, 40, 40); // j node
                circle.setStroke(prevStroke);

                g.setColor(Color.BLACK);
                drawArrowLine(g, 90, 210, 170, 180, 5, 5); // from i to k
                drawArrowLine(g, 210, 180, 300, 210, 5, 5); // from k to j
                drawArrowLine(g, 100, 230, 280, 230, 5, 5); // from i to j

                g.setFont(new Font("Arial", Font.PLAIN, 15));
                g.drawString("for k --> " + k, 20, 50);
                g.drawString("for i --> " + i, 40, 70);
                g.drawString("for j --> " + j, 60, 90);
                g.drawString("dist[" + i + "][" + j + "] > dist[" + i + "][" + k + "] + dist[" + k + "][" + j + "]", 80,
                        120);
                if (window.floydWarshall.satisfied())
                    g.setColor(new Color(0, 100, 0));
                else
                    g.setColor(Color.RED);

                g.drawString(window.floydWarshall.getDist(i, j) + " > " + window.floydWarshall.getDist(i, k) + " + "
                        + window.floydWarshall.getDist(k, j), 80, 140);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.drawString(window.graph.vertices.get(i).key, 75, 230); // i node
                g.drawString(window.graph.vertices.get(k).key, 185, 180); // k node
                g.drawString(window.graph.vertices.get(j).key, 295, 230); // j node

                g.drawString(Double.toString(window.floydWarshall.getDist(i, k)), 100, 180);
                g.drawString(Double.toString(window.floydWarshall.getDist(k, j)), 250, 180);
                g.drawString(Double.toString(window.floydWarshall.getDist(i, j)), 180, 225);
            } else
                showAlgorithm(g);
        } else {
            showAlgorithm(g);
        }
    }

    private void showAlgorithm(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("for k --> 0 to (number of vertices - 1)", 20, 50);
        g.drawString("for i --> 0 to (number of vertices - 1)", 40, 70);
        g.drawString("for j --> 0 to (number of vertices - 1)", 60, 90);
        g.drawString("if dist [ i ] [ j ]  >  dist [ i ] [ k ] + dist [ k ] [ j ]", 80, 120);
        g.drawString("then dist [ i ] [ j ]  =  dist [ i ] [ k ] + dist [ k ] [ j ]", 80, 140);
    }

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
}