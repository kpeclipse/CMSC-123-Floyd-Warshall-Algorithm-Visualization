import java.util.ArrayList;

public class Vertex
{
    private String Name;
    private boolean hasEdge;
    private boolean visited = false;
    private int path = -1;
    private int inDegree = 0;
    private double criticalPath = 0;
    public ArrayList<Vertex> adjList = new ArrayList<Vertex>();

    public Vertex()
    {
    }

    public Vertex(String name)
    {
        this.Name = name;
    }

    public void setCPath(double path)
    {
        this.criticalPath = path;
    }

    public double getCPath()
    {
        return this.criticalPath;
    }

    public void addInDegree()
    {
        this.inDegree += 1;
    }

    public void removeInDegree()
    {
        this.inDegree -= 1;
    }

    public int getInDegree()
    {
        return this.inDegree;
    }

    public void setVisited()
    {
        this.visited = true;
    }

    public void unVisit()
    {
        this.visited = false;
    }

    public boolean hasVisited()
    {
        return this.visited;
    }

    public void addAdjV(Vertex add)
    {
        this.adjList.add(add);
    }

    public Vertex removeAdjV(String remove)
    {
        for (int i = 0; i < adjList.size(); i++)
        {
            if (adjList.get(i).getName() == remove)
            {
                Vertex removed = adjList.get(i);
                adjList.remove(i);
                return removed;
            }
        }
        return null;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public String getName()
    {
        return this.Name;
    }

    // public void setWeight(String in)
    // {
    //     this.weight = in;
    // }

    // public String getWeight()
    // {
    //     return this.weight;
    // }

    public void addPath()
    {
        this.path += 1;
    }

    public void setPath(int x)
    {
        this.path = x;
    }

    public int getPath()
    {
        return this.path;
    }

    public void resetPath()
    {
        this.path = 0;
    }

    public void addEdge()
    {
        this.hasEdge = true;
    }

    public boolean removeEdge()
    {
        this.hasEdge = false;
        return this.hasEdge;
    }

    public boolean hasEdge()
    {
        return this.hasEdge;
    }
}