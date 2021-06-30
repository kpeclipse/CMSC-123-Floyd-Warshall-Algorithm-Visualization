package DataStructure;

import java.util.*;

public class Graph {
    double[][] weights;
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    ArrayList<Vertex> zeroInDegreeVertices;
    private boolean directed;

    public Graph(boolean d) {
        // determine whether graph is directed or undirected
        directed = d;
    }

    public boolean isDirected() {
        return directed;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double getWeight(int i, int j) {
        return weights[i][j];
    }

    public void setEdges(ArrayList<Edge> edgeSet) {
        edges = edgeSet;
    }

    public void addEdge(String first, String second, double weight) {
        boolean maxReached = true;
        int numberOfEdges = 0;
        Edge edge = null;
        Vertex firstV = null;
        Vertex secondV = null;

        /** If there are no vertices */
        if (vertices == null || vertices.size() == 0)
            System.out.println("NO VERTEX");

        /** If there is at least one vertex */
        else {
            /** If there is at least one edge */
            if (edges != null && edges.size() > 0)
                numberOfEdges = edges.size();

            else if (edges == null || edges.size() == 0)
                numberOfEdges = 0;

            /**
             * CHECK IF THE CURRENT NUMBER OF EDGES HAS REACHED THE MAXIMUM NUMBER OF EDGES
             * FOR UNDIRECTED GRAPH
             */
            if (!directed) {
                if ((numberOfEdges + 1) > ((vertices.size() * (vertices.size() - 1)) / 2))
                    System.out.println("MAXIMUM NUMBER OF EDGES REACHED!");
                else
                    maxReached = false;

            } else
                maxReached = false;

            /** IF WE CAN STILL ADD AN EDGE */
            if (!maxReached) {
                // NOTE: Simple Undirected Graph has no duplicate edges and no loops

                int found = 0;
                /** CHECK IF VERTICES EXIST IN GRAPH AND ARE NOT EQUAL */
                for (int i = 0; i < vertices.size(); i++) {
                    if (vertices.get(i).key.matches(first)) {
                        found += 1;
                        firstV = vertices.get(i);
                    }

                    if (vertices.get(i).key.matches(second)) {
                        found += 1;
                        secondV = vertices.get(i);
                    }

                    if (found == 2)
                        break;
                }

                if (vertices.contains(firstV) && vertices.contains(secondV)
                        && vertices.indexOf(firstV) != vertices.indexOf(secondV)) {

                    int i = vertices.indexOf(firstV);
                    int j = vertices.indexOf(secondV);

                    edge = new Edge(firstV, secondV, weight); // Create an Edge

                    weights[i][j] = weight; // ADD WEIGHT TO MATRIX
                    vertices.get(i).addAdj(vertices.get(j)); // ADD SECOND VERTEX TO ADJACENT LIST OF FIRST VERTEX
                    vertices.get(j).addParent(vertices.get(i)); // ADD FIRST VERTEX TO LIST OF PARENT NODES OF SECOND
                                                                // VERTEX
                    zeroInDegreeVertices.remove(vertices.get(j));

                    if (!directed) { // If graph is undirected
                        weights[j][i] = weight;
                        vertices.get(j).addAdj(vertices.get(i));
                        zeroInDegreeVertices.remove(vertices.get(i));
                    }

                    /** ADD EDGE TO THE LIST OF EDGES */
                    if (edges == null)
                        edges = new ArrayList<Edge>();
                    edges.add(edge);
                }
            }
        }
    }

    public void removeEdge(Vertex firstV, Vertex secondV, double weight) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).first == firstV && edges.get(i).second == secondV && edges.get(i).value == weight) {
                weights[vertices.indexOf(firstV)][vertices.indexOf(secondV)] = Double.POSITIVE_INFINITY;
                if (!directed)
                    weights[vertices.indexOf(secondV)][vertices.indexOf(firstV)] = Double.POSITIVE_INFINITY;
                edges.remove(i);
                break;
            }
        }
    }

    public void addVertex(String name, int x, int y) {
        // If there are no existing vertices
        if (vertices == null || vertices.size() == 0) {
            vertices = new ArrayList<Vertex>();
            zeroInDegreeVertices = new ArrayList<Vertex>();

            weights = new double[1][1];
            weights[0][0] = 0;
        }

        // If there is at least one vertex
        else {
            // Checks if name of vertex already exists
            boolean exists = false;
            for (int i = 0; i < vertices.size(); i++) {
                if (vertices.get(i).key.matches(name)) {
                    exists = true;
                    break;
                }
            }

            // If vertex is new
            if (!exists) {
                double[][] temp = weights;
                weights = new double[vertices.size() + 1][vertices.size() + 1];

                for (int i = 0; i < vertices.size() + 1; i++) {
                    if (i == vertices.size())
                        weights[i][vertices.size()] = 0;
                    else {
                        weights[i][vertices.size()] = Double.POSITIVE_INFINITY;
                        weights[vertices.size()][i] = Double.POSITIVE_INFINITY;
                    }
                }

                for (int i = 0; i < vertices.size(); i++)
                    for (int j = 0; j < vertices.size(); j++)
                        weights[i][j] = temp[i][j];
            }
        }

        Vertex newVertex = new Vertex(name, x, y);
        vertices.add(newVertex);
        zeroInDegreeVertices.add(newVertex);
    }

    public void removeVertex(Vertex v) {
        boolean vertexInList = false;
        int index = 0;

        // If there is at least one vertex
        if (vertices != null || vertices.size() != 0) {
            if (vertices.contains(v)) {
                vertexInList = true;
                index = vertices.indexOf(v);
            }

            if (vertexInList) {
                if (zeroInDegreeVertices.contains(v))
                    zeroInDegreeVertices.remove(v);

                for (int i = 0; i < vertices.size(); i++) {
                    if (i == index)
                        continue;

                    if (vertices.get(i).adjacentVertices.contains(v))
                        vertices.get(i).removeAdj(v);

                    if (vertices.get(i).parents.contains(v)) {
                        vertices.get(i).removeParent(v);
                        if (vertices.get(i).parents.size() == 0)
                            zeroInDegreeVertices.add(vertices.get(i));
                    }

                    // If there are existing edges
                    if (edges != null) {
                        for (int j = 0; j < edges.size(); j++) {
                            if (edges.get(j).first == vertices.get(index)
                                    || edges.get(j).second == vertices.get(index)) {
                                edges.remove(j);
                                j = -1;
                            }
                        }
                    }
                }

                vertices.remove(v);
                if (vertices.size() > 0)
                    defaultMatrix();
            }
        }
    }

    public void defaultMatrix() {
        weights = new double[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++)
            for (int j = 0; j < vertices.size(); j++) {
                if (i != j)
                    weights[i][j] = Double.POSITIVE_INFINITY;
                else
                    weights[i][j] = 0;
            }

        if (edges != null) {
            for (int i = 0; i < edges.size(); i++) {
                weights[vertices.indexOf(edges.get(i).first)][vertices.indexOf(edges.get(i).second)] = edges
                        .get(i).value;
                if (!directed)
                    weights[vertices.indexOf(edges.get(i).second)][vertices.indexOf(edges.get(i).first)] = edges
                            .get(i).value;
            }
        }
    }
}