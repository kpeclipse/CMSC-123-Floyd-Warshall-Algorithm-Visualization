import java.util.ArrayList;

public class Vertex {
    protected String key;
    protected ArrayList<Vertex> adjacentVertices = new ArrayList<Vertex>();
    protected ArrayList<Vertex> parents = new ArrayList<Vertex>();

    public Vertex(String name) {
        key = name;
    }

    public void addAdj(Vertex insert) {
        adjacentVertices.add(insert);
    }

    public void removeAdj(Vertex remove) {
        adjacentVertices.remove(remove);
    }

    public void addParent(Vertex parent) {
        parents.add(parent);
    }

    public void removeParent(Vertex parent) {
        parents.remove(parent);
    }
}