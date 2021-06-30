package Algorithm;

import UI.Window;

public class AlgorithmTracing implements Runnable {
    private Window window;
    private int speed;

    public AlgorithmTracing(Window w) {
        window = w;
    }

    @Override
    public void run() {
        window.getFloydWarshall().setRunning(true);
        int k = 0;
        while (window.getGraph() != null && k < window.getGraph().getVertices().size()) {
            try {
                // The Floyd-Warshall Algorithm
                for (k = 0; k < window.getGraph().getVertices().size(); k++)
                    for (int i = 0; i < window.getGraph().getVertices().size(); i++)
                        for (int j = 0; j < window.getGraph().getVertices().size(); j++) {
                            window.getFloydWarshall().compare(k, i, j);
                            window.getDisplay().highlightVertices(k, i, j);
                            window.getAlgorithm().getIndex(k, i, j);
                            window.getData().paintTablePanel(k, i, j);
                            speed = window.getAdjustments().getSpeed();
                            Thread.sleep(2000 / speed);

                            if (window.getFloydWarshall().satisfied()) {
                                window.getFloydWarshall().setDist(k, i, j);
                                Thread.sleep(2000);
                            }
                        }
                window.getFloydWarshall().setRunning(false);
                window.getFloydWarshall().stop();
            } catch (Exception e) {
            }
        }
    }
}