import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
    private File file;
    Graph g;

    public GraphReader(File f) {
        file = f;
        readGraph();
    }

    public void readGraph() {
        boolean readVertex = false;
        boolean readEdge = false;
        String line;
        int numberOfVertices = 0;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            g = new Graph(true);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.charAt(0) == '/')
                    continue;

                if (line.contains("VERTICES:")) {
                    readVertex = true;
                    continue;
                }

                if (line.contains("EDGES:")) {
                    readEdge = true;
                    readVertex = false;
                    continue;
                }

                if (readVertex) {
                    g.addVertex(line, 10, 10);
                    numberOfVertices = g.vertices.size();
                }

                if (readEdge) {
                    String[] details = line.split(" ", 3);
                    g.addEdge(details[0], details[1], Double.parseDouble(details[2]));
                }
            }

            bufferedReader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}