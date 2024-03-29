package Algorithm;

import DataStructure.Graph;
import UI.Window;

public class FloydWarshall {
    private double[][] dist;
    private int V;
    private boolean run = false;
    private boolean satisfied = false;
    private Thread trace;

    // public static void main(String[] args) {
    // GraphReader read = new GraphReader(new File(System.getProperty("user.dir") +
    // "/graph.txt"));
    // Graph g = read.g;
    // new FloydWarshall(g);
    // }

    public FloydWarshall(Graph g) {
        useGraph(g);

        /**
         * // Algorithm for (int k = 0; k < V; k++) { // Source Vertex for (int i = 0; i
         * < V; i++) // Destination Vertex for (int j = 0; j < V; j++) { if (i != j) {
         * if (dist[i][j] > dist[i][k] + dist[k][j]) dist[i][j] = dist[i][k] +
         * dist[k][j]; } else dist[i][j] = 0; } } }
         */

        // showMatrix();
    }

    public void useGraph(Graph g) {
        V = g.getVertices().size();
        dist = new double[V][V];

        initialize(g.getWeights());
    }

    public boolean isRunning() {
        return run;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public boolean satisfied() {
        return satisfied;
    }

    public void start(Window window) {
        trace = new Thread(new AlgorithmTracing(window));
        trace.start();
    }

    public void stop() {
        if (isRunning())
            trace.interrupt();
        trace = null;
    }

    public void compare(int k, int i, int j) {
        satisfied = false;
        if (i != j) {
            if (dist[i][j] > dist[i][k] + dist[k][j]) {
                satisfied = true;
                // dist[i][j] = dist[i][k] + dist[k][j];
            }
        } else
            dist[i][j] = 0;
    }

    public double getDist(int i, int j) {
        return dist[i][j];
    }

    public void setDist(int k, int i, int j) {
        dist[i][j] = dist[i][k] + dist[k][j];
    }

    public void initialize(double[][] wGraph) {
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++) {
                if (i == j)
                    dist[i][j] = 0;
                else
                    dist[i][j] = wGraph[i][j];
            }
    }

    public void showMatrix() {
        for (int i = 0; i < V; i++) {
            System.out.print("\t");
            for (int j = 0; j < V; j++) {
                if (dist[i][j] != Double.POSITIVE_INFINITY)
                    System.out.print(" [" + dist[i][j] + "] ");
                else
                    System.out.print(" [INF] ");
            }
            System.out.println();
        }
    }
}