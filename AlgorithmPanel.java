import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AlgorithmPanel extends JPanel {
    private Window window;

    public AlgorithmPanel(Window w) {
        window = w;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

        if (window.floydWarshall != null) {
            if (window.floydWarshall.run) {
                g.setColor(Color.BLACK);
                g.drawOval(60, 120, 60, 60);
                g.drawOval(170, 60, 60, 60);
                g.drawOval(280, 120, 60, 60);

                drawArrowLine(g, 90, 120, 170, 90, 5, 5);
                drawArrowLine(g, 230, 90, 310, 120, 5, 5);
                drawArrowLine(g, 120, 150, 280, 150, 5, 5);
            }
        }
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