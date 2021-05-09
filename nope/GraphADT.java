import java.util.Stack;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class GraphADT implements Cloneable
{
    public double[][] adjM;
    public ArrayList<Vertex> vert = new ArrayList<Vertex>();
    private Stack<Vertex> dfs = new Stack<Vertex>();
    private Queue<Vertex> bfs = new LinkedList<Vertex>();

    @Override
    protected Object clone() throws
                   CloneNotSupportedException 
    { 
        GraphADT cloned = (GraphADT)super.clone();
        return cloned; 
    } 

    public void addVertex(String name)
    {
        Vertex in = new Vertex();
        in.setName(name);
        vert.add(in);
        adjustMatrix(0, 0);
    }

    public Vertex removeVertex(int index)
    {
        Vertex removed = vert.remove(index);
        adjustMatrix(1, index);
        return removed;
    }

    public void adjustMatrix(int choice, int vertRemoved)
    {
        if (choice == 0)
        {
            // adding a new vertex
            if (adjM == null)
            {
                adjM = new double[vert.size()][vert.size()];
            }
            
            double[][] temp = new double[vert.size()][vert.size()];
            for (int i = 0 ; i < vert.size(); i++)
            {
                for (int x = 0; x < vert.size(); x++)
                {
                    if (x == i)
                        temp[i][x] = 0;

                    else if (x < adjM.length
                        &&  i < adjM.length
                            && adjM[i][x] != Double.POSITIVE_INFINITY)
                    {
                        temp[i][x] = adjM[i][x];
                    }

                    else
                    {
                        temp[i][x] = Double.POSITIVE_INFINITY;
                    }
                }
            }
            this.adjM = temp;
        }

        else if (choice == 1)
        {
            // removing a vertex
            double[][] temp = new double[adjM.length-1][adjM.length-1];

            for (int x = 0 ; x < adjM.length; x++)
            {
                for (int y = 0; y < adjM.length; y++)
                {
                    if (y < vertRemoved) {
                        //C
                        if (x > vertRemoved){
                            // System.out.println((x)+", "+y+" -> "+(x-1)+", "+y );
                            temp[x-1][y] = adjM[x][y];}
                        //D
                        else if (x < vertRemoved){
                            // System.out.println((x)+", "+y+" -> "+(x)+", "+y );
                            temp[x][y] = adjM[x][y];}
                    }

                    if (y > vertRemoved) {
                        //A
                        if (x < vertRemoved){
                            // System.out.println((x)+", "+(y-1)+" -> "+(x-1)+", "+y );
                            temp[x][y-1] = adjM[x][y];}
                        //B
                        else if (x > vertRemoved){
                            // System.out.println((x-1)+", "+(y-1)+" -> "+(x)+", "+y );
                            temp[x-1][y-1] = adjM[x][y];}
                    }                    
                }
            }
            this.adjM = temp;
        }
    }

    public void addEdge(int from, int to)
    {
        vert.get(from).addEdge();
        vert.get(from).addAdjV(vert.get(to));
        adjM[from][to] = 1;

        vert.get(to).addEdge();
        vert.get(to).addAdjV(vert.get(from));
        adjM[to][from] = 1;
    }

    public void addDirEdge(int from, int to)
    {
        vert.get(from).addEdge();
        vert.get(from).addAdjV(vert.get(to));
        vert.get(to).addInDegree();
        adjM[from][to] = 1;
    }

    public void addDirWeightedEdge(int from, int to, double weight)
    {
        vert.get(from).addEdge();
        vert.get(from).addAdjV(vert.get(to));
        vert.get(to).addInDegree();
        adjM[from][to] = weight;
    }

    public void removeEdge(int from, int to)
    {
        vert.get(from).removeEdge();
        vert.get(from).removeAdjV(vert.get(to).getName());
        vert.get(to).removeEdge();
        vert.get(to).removeAdjV(vert.get(from).getName());
        adjM[from][to] = 0;
        adjM[to][from] = 0;
    }

    public ArrayList<Integer> getNeighbors(Vertex x)
    {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        int position = 0;
        for (int c = 0; c < vert.size(); c++)
        {
            if (x.getName() == vert.get(c).getName())
            {
                position = c;
                break;
            }
        }

        for (int i = 0; i < adjM.length; i++)
        {
            if (adjM[position][i] >= 1)
                neighbors.add(i);
        }

        return neighbors;
    }

    public void resetVisits()
    {
        for (int c = 0; c < vert.size(); c++)
        {
            vert.get(c).unVisit();
        }
    }

    public int getPosition(Vertex x)
    {
        for (int i = 0; i < vert.size(); i++)
        {
            if (x.getName() == vert.get(i).getName())
                return i;
        }
        return -1;
    }

    public int getEdges()
    {
        int edges = 0;
        for (int i = 0 ; i < vert.size(); i++)
            {
                for (int x = 0; x < vert.size(); x++)
                {
                    if (adjM[i][x] >= 1 || adjM[x][i] >= 1)
                    {
                        edges += 1;
                    }
                }
            }
        return edges;
    }

    public int getVertices()
    {
        return vert.size();
    }

    public void printMatrix()
    {
        if (adjM == null || adjM.length == 0)
        {
            System.out.println("\n\nEmpty graph");
        }

        else
        {

        System.out.print("\n   ");

        for (int i = 0 ; i < adjM.length ; i++)
        {
            System.out.print((i) + " ");
        }

        System.out.print("\n  ");

        for (int i = 0; i < adjM.length*2; i++)
        {
            System.out.print("-");
        }

        System.out.print("\n");

        for (int i = 0; i < adjM.length ; i++)
        {
            System.out.print((i) + "| ");

            for (int x = 0; x < adjM.length ; x++)
            {
                if (i == x)
                    System.out.print("0 ");

                else if (i != x) {
                if (adjM[i][x] != Double.POSITIVE_INFINITY)
                    System.out.print("1 ");
                else if (adjM[i][x] == Double.POSITIVE_INFINITY)
                    System.out.print("  ");
                }
            }

            System.out.print("\n");
        }
        // printVertexList();
        printAdjList();

        System.out.print("\n");
        }
    }

    public void printWeightedMatrix()
    {
        if (adjM == null || adjM.length == 0)
        {
            System.out.println("\n\nEmpty graph");
        }

        else
        {
        System.out.print("\n   ");

        for (int i = 0 ; i < adjM.length ; i++)
        {
            System.out.print((i) + "\t");
        }

        System.out.print("\n  ");

        for (int i = 0; i < adjM.length*6; i++)
        {
            System.out.print("-");
        }

        System.out.print("\n");

        for (int i = 0; i < adjM.length ; i++)
        {
            System.out.print((i) + "|");

            for (int x = 0; x < adjM.length ; x++)
            {
                if (adjM[i][x] != Double.POSITIVE_INFINITY)
                    if (adjM[i][x] == 0)
                        System.out.print("0\t");
                    else
                        System.out.print(adjM[i][x]+"\t");
                else
                    System.out.print("   \t");
            }

            System.out.print("\n");
        }
        printAdjList();
        System.out.print("\n");
        }
    }

    public void printVertexList()
    {
        System.out.println("\nLegend: ");

        for (int i = 0; i < vert.size(); i++)
        {
            System.out.println(i+". " + vert.get(i).getName());
        }
    }

    public void printBinaryRelation()
    {
        for (int i = 0; i < adjM.length ; i++)
        {
            for (int x = 0; x < adjM.length ; x++)
            {
                if (adjM[i][x] >= 1){
                    System.out.print("(");
                    System.out.print(i+", "+x);
                    System.out.print(") ");}
            }
        }
        System.out.println();
    }

    public void printAdjList()
    {
        if (vert.size() == 0)
        {
            System.out.println("\n\nEmpty graph");
        }

        System.out.print("\n");
        for (int i = 0; i < vert.size() ; i++)
        {
            System.out.print(i +"| " +vert.get(i).getName());

            if (vert.get(i).hasEdge())
            {
                for (int x = 0; x < vert.get(i).adjList.size(); x++)
                {
                    System.out.print(" -> " + vert.get(i).adjList.get(x).getName());
                }
            }

            else
            {
                System.out.print(" has no outgoing edge.");
            }
            System.out.print("\n");
        }
    }

    public boolean checkConnected(int from, int to)
    {
        ArrayList<Integer> out = new ArrayList<Integer>();
        Vertex source = vert.get(from);
        dfs.push(source);

        while (!dfs.empty())
        {
            Vertex current = dfs.pop();
            if (!vert.get(getPosition(current)).hasVisited());
                out.add(getPosition(current));
                
            vert.get(getPosition(current)).setVisited();
            
            
            ArrayList<Integer> neigh = getNeighbors(current);

            for (int i = 0; i < neigh.size(); i++) 
            {
                if (vert.get(neigh.get(i)).hasVisited())
                {
                    continue;
                }
                else
                {
                    dfs.push(vert.get(neigh.get(i)));
                }
            }
        }
        boolean retout = vert.get(to).hasVisited();
        resetVisits();

        return retout;
        
    }

    public ArrayList<Integer> depthFirst()
    {
        ArrayList<Integer> out = new ArrayList<Integer>();
        Vertex source = vert.get(0);
        dfs.push(source);

        while (!dfs.empty())
        {
            Vertex current = dfs.pop();
            if (!vert.get(getPosition(current)).hasVisited()){
                out.add(getPosition(current));}
            vert.get(getPosition(current)).setVisited();
                
            
            ArrayList<Integer> neigh = getNeighbors(current);

            for (int i = 0; i < neigh.size(); i++) 
            {
                if (vert.get(neigh.get(i)).hasVisited())
                {
                    continue;
                }
                else
                {
                    dfs.push(vert.get(neigh.get(i)));
                }
            }
        }
        resetVisits();
        return out;
    }

    public ArrayList<Integer> breadthFirst()
    {
        ArrayList<Integer> out = new ArrayList<Integer>();
        Vertex source = vert.get(0);
        bfs.add(source);

        while (bfs.peek() != null)
        {
            Vertex current = bfs.remove();
            if (!vert.get(getPosition(current)).hasVisited()){
                out.add(getPosition(current));}

            vert.get(getPosition(current)).setVisited();
            
            ArrayList<Integer> neigh = getNeighbors(current);

            for (int i = 0; i < neigh.size(); i++) 
            {
                if (vert.get(neigh.get(i)).hasVisited())
                {
                    continue;
                }
                else
                {
                    bfs.add(vert.get(neigh.get(i)));
                }
            }
        }
        resetVisits();
        return out;
    }

    // public void depthTraverse()
    // {
    //     Vertex current = vert.get(0);
    //     dfs.push(current);
    //     int ctr = 0;

    //     System.out.print("DFS: [");
    //     while (!dfs.empty())
    //     {
    //         if (!current.hasVisited())
    //         {
    //             current.setVisited();
    //             System.out.print(" " +current.getName());
    //             // System.out.print(" " +dfs.pop().getName());
    //         }
            
    //         if (ctr < current.adjList.size() 
    //                     && current.hasEdge()
    //                             && !current.adjList.get(ctr).hasVisited())
    //         {
    //             current = current.adjList.get(ctr);
    //             dfs.push(current);
                
    //             // System.out.println("pushing "+current.getName());
    //             ctr=0;
    //         }

    //         else if (ctr < current.adjList.size() 
    //                 && current.adjList.get(ctr).hasVisited())
    //         {
    //             // System.out.println("All current nearby shits are visited, "+ctr+" of "+vert.size());
    //             ctr++;
    //         }

    //         else
    //         {
    //             dfs.pop();
    //         }
    //     }
    //     // System.out.print(" " +dfs.pop().getName()); 
    //     System.out.println(" ]");
    // }

    public void printWeights(int f, int t)
    {
    }
}