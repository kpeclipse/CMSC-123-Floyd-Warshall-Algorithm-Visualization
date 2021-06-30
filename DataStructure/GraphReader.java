package DataStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import UI.Window;

public class GraphReader {
    private Graph graph;
    private File file;
    private ArrayList<Integer> xBounds = new ArrayList<Integer>();
    private ArrayList<Integer> yBounds = new ArrayList<Integer>();

    public GraphReader(Window w, File f) {
        graph = w.getGraph();
        file = f;
        setCoordinates();
        readGraph();
    }

    public void setCoordinates() { // possible coordinates for random vertices
        int multiple = 50;
        for (int i = 0; i < 10; i++) {
            xBounds.add(multiple);
            yBounds.add(multiple);
            multiple += 50;
        }

        for (int i = 10; i < 13; i++) {
            yBounds.add(multiple);
            multiple += 50;
        }
    }

    public void readGraph() {
        boolean readVertex = false;
        boolean readEdge = false;
        String line;
        Random r = new Random();
        int setX = 0;
        int setY = 0;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

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
                    setX = xBounds.get(r.nextInt(10));
                    setY = yBounds.get(r.nextInt(13));

                    if (graph.vertices != null) {
                        boolean overlap = true;
                        while (overlap) {
                            overlap = checkVertex(setX, setY); // check if there is existing vertex on point

                            if (overlap) {
                                setX = xBounds.get(r.nextInt(10));
                                setY = yBounds.get(r.nextInt(13));
                            }
                        }
                    }

                    if (graph.vertices == null || (graph.vertices != null && graph.vertices.size() < 50))
                        graph.addVertex(line, setX, setY);
                }

                if (readEdge) {
                    String[] details = line.split(" ", 3);
                    graph.addEdge(details[0], details[1], Double.parseDouble(details[2]));
                }
            }

            bufferedReader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean checkVertex(int currentX, int currentY) {
        for (int i = 0; i < graph.vertices.size(); i++)
            if (graph.vertices.get(i).getX() == currentX && graph.vertices.get(i).getY() == currentY)
                return true;

        return false;
    }

    public Graph getGraph() {
        return graph;
    }
}