
public class FloydWarshall {
    Window window;
    Graph g;
    double[][] weight;
    double[][] dist;
    int V;
    boolean run = false;

    // public static void main(String[] args) {
    // GraphReader read = new GraphReader(new File(System.getProperty("user.dir") +
    // "/graph.txt"));
    // Graph g = read.g;
    // new FloydWarshall(g);
    // }

    public FloydWarshall(Window w) {
        window = w;
        g = window.graph;
        V = g.vertices.size();
        weight = new double[V][V];
        dist = new double[V][V];

        initialize(g.weights);

        Thread trace = new Thread(new AlgorithmTracing(window));
        trace.start();
        // Algorithm
        // for (int k = 0; k < V; k++) {
        // // Source Vertex
        // for (int i = 0; i < V; i++)
        // // Destination Vertex
        // for (int j = 0; j < V; j++) {
        // if (i != j) {
        // if (dist[i][j] > dist[i][k] + dist[k][j])
        // dist[i][j] = dist[i][k] + dist[k][j];
        // }

        // else
        // dist[i][j] = 0;
        // }
        // }
        // }

        // showMatrix();
    }

    public void step(int k, int i, int j) {
        if (i != j) {
            if (dist[i][j] > dist[i][k] + dist[k][j])
                dist[i][j] = dist[i][k] + dist[k][j];
        } else
            dist[i][j] = 0;
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