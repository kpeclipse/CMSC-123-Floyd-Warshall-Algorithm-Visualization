class Edge {
    Vertex first;
    Vertex second;
    double value;

    // With Weight
    public Edge(Vertex f, Vertex s, double v) {
        first = f;
        second = s;
        value = v;
    }

    // Without Weight
    public Edge(Vertex f, Vertex s) {
        first = f;
        second = s;
    }
}