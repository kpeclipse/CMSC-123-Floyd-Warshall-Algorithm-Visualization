public class AlgorithmTracing implements Runnable {
    private Window window;

    public AlgorithmTracing(Window w) {
        window = w;
    }

    @Override
    public void run() {
        int speed = window.adjustments.getSpeed();
        while (window.floydWarshall.run) {
            try {
                for (int k = 0; k < window.graph.vertices.size(); k++)
                    for (int i = 0; i < window.graph.vertices.size(); i++)
                        for (int j = 0; j < window.graph.vertices.size(); j++) {
                            window.floydWarshall.step(k, i, j);
                            window.data.paintTablePanel(k, i, j);
                            Thread.sleep(2000 / speed);
                        }
            } catch (Exception e) {
            }
        }
    }
}