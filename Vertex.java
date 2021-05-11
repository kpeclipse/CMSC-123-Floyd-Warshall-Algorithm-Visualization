import java.util.ArrayList;

public class Vertex {
    protected String key;
    protected ArrayList<Vertex> adjacentVertices = new ArrayList<Vertex>();
    protected ArrayList<Vertex> parents = new ArrayList<Vertex>();
    protected int x, y;

    public Vertex(String name, int x, int y) {
        key = name;
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
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