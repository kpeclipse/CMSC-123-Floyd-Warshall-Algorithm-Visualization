public class AlgorithmTracing implements Runnable {
    private Window window;
    private int speed;

    public AlgorithmTracing(Window w) {
        window = w;
    }

    @Override
    public void run() {
        window.floydWarshall.setRunning(true);
        int k = 0;
        while (window.graph != null && k < window.graph.vertices.size()) {
            try {
                // The Floyd-Warshall Algorithm
                for (k = 0; k < window.graph.vertices.size(); k++)
                    for (int i = 0; i < window.graph.vertices.size(); i++)
                        for (int j = 0; j < window.graph.vertices.size(); j++) {
                            window.floydWarshall.compare(k, i, j);
                            window.display.highlightVertices(k, i, j);
                            window.algorithm.getIndex(k, i, j);
                            window.data.paintTablePanel(k, i, j);
                            speed = window.adjustments.getSpeed();
                            Thread.sleep(2000 / speed);

                            if (window.floydWarshall.satisfied()) {
                                window.floydWarshall.setDist(k, i, j);
                                Thread.sleep(2000);
                            }
                        }
                window.floydWarshall.setRunning(false);
                window.floydWarshall.stop();
            } catch (Exception e) {
            }
        }
    }
}