import java.util.Scanner;

public class FloydWarshall {
    private double[][] paths;
    private GraphADT graph = new GraphADT();
    private Scanner scan = new Scanner(System.in);

    FloydWarshall(GraphADT graph)
    {
        this.graph = graph;
    }
    
    public double[][] findPath()
    {
        paths = new double[graph.adjM.length][graph.adjM.length];
        for (int m = 0; m < graph.adjM.length; m++)
        {
            for (int n = 0; n < graph.adjM.length; n++)
            {
                if (graph.adjM[m][n] != Double.POSITIVE_INFINITY)
                    paths[m][n] = graph.adjM[m][n];
                else 
                    paths[m][n] = Double.POSITIVE_INFINITY;
            }
        }
        //CENTRAL LOOP FOR FINDING SHORTEST PATH IN FLOYD WARSHALL
        //COMPARES PATH LENGTHS BETWEEN PAIRS, USING PREVIOUSLY KNOWN 
        //PATH LENGTH DATA TO MAKE CHANGES TO THE DISTANCE MATRIX 
        //ACCORDINGLY
        for (int k = 0; k < paths.length; k++)
        {
            for (int i = 0; i < paths.length; i++)
            {
                for (int j = 0; j < paths.length; j++)
                {
                    if (paths[i][j] > paths[i][k] + paths[k][j])
                    {
                        paths[i][j] = paths[i][k] + paths[k][j];
                    }
                }
            }
        }
        return paths;
    }

    public void displayMatrix()
    {
        if (paths == null || paths.length == 0)
        {
            System.out.println("\n\nEmpty graph");
        }

        else
        {
        System.out.print("\n  ");

        for (int i = 0 ; i < paths.length ; i++)
        {
            System.out.print((i) + "\t");
        }

        System.out.print("\n  ");

        for (int i = 0; i < paths.length*6; i++)
        {
            System.out.print("-");
        }

        System.out.print("\n");

        for (int i = 0; i < paths.length ; i++)
        {
            System.out.print((i) + "|");
            for (int x = 0; x < paths.length ; x++)
            {
                if (paths[i][x] != Double.POSITIVE_INFINITY)
                {
                    if (paths[i][x] == 0)
                        System.out.print("0\t");
                    else
                        System.out.print(paths[i][x]+"\t");
                }                    
                else if (paths[i][x] == Double.POSITIVE_INFINITY)
                    System.out.print("   \t");
            }
            System.out.print("\n");
        }

        System.out.print("\n");
        }
        scan.nextLine();
    }
}