import java.util.Scanner;

public class FloydWTester
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Boolean bool = true;
        int choice;
        
        GraphADT graph = new GraphADT();
        //0-1,1-3,1-2,0-2,2-3,0-4,4-7,7-8,8-6,6-4,4-2,2-5
        graph.addVertex("0");//0
        graph.addVertex("1");//1
        graph.addVertex("2");//2
        graph.addVertex("3");//3
        graph.addDirWeightedEdge(0,2,-2);
        graph.addDirWeightedEdge(2,3,2);
        graph.addDirWeightedEdge(3,1,-1);
        graph.addDirWeightedEdge(1,0,4);
        graph.addDirWeightedEdge(1,2,3);


        while(bool = true)
        {
            System.out.println("-------------------------------------------------------------------------");
            graph.printWeightedMatrix();
            // System.out.println ("bool = " + bool);
            System.out.println("Directed, unweighted graph has " + graph.getVertices() + " vertices and " + graph.getEdges() + " unique edges.");
            System.out.println("Choose (1-5 only): ");
            System.out.print("MODIFICATIONS:\n1. Add Vertex\n2. Remove Vertex\n3. Add directed edge \n4. Remove edge \n\nQUERIES: \n5. Find shortest path via Floyd Warshall\n\nInput Selection: "); 
            choice = scan.nextInt();
            System.out.println("-------------------------------------------------------------------------");
            switch (choice)
            {
                case 1:
                    System.out.println("ADDING A VERTEX");
                    System.out.print("Vertex Name:");
                    String inName = scan.next();
                    
                    graph.addVertex(inName);
                    break;
                case 2:
                    System.out.println("REMOVING A VERTEX");
                    System.out.print("Vertex Name:");
                    graph.printVertexList();
                    int reVertex = scan.nextInt();
                
                    graph.removeVertex(reVertex);
                    break;
                case 3: 
                    System.out.println("ADDING AN EDGE (From 0 to " + (graph.vert.size()-1) + " only.)");
                    graph.printVertexList();
                    System.out.print("Vertex 1: ");
                    int fa = scan.nextInt();
                    System.out.print("Vertex 2: ");
                    int ta = scan.nextInt();
                    // System.out.print("Weight: ");
                    // String wgt = scan.next();
                    
                    graph.addDirEdge(fa, ta);
                    break;
                case 4:
                    System.out.println("REMOVING AN EDGE");
                    System.out.print("Vertex 1: ");
                    int fr = scan.nextInt();
                    System.out.print("Vertex 2: ");
                    int tr = scan.nextInt();
                    
                    graph.removeEdge(fr, tr);
                    break;
                case 5:
                    System.out.println("FLOYD WARSHALL ALGO");
                    FloydWarshall fw = new FloydWarshall(graph);
                    fw.findPath();
                    fw.displayMatrix();

                    break;
            }

        }
    }
}