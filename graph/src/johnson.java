import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;



class johnson
{
    private boolean settled[];
    private boolean unsettled[];
    private int distances[];
    private int adjacencymatrix[][];
    private int numberofvertices;

    public static final int MAX_VALUE = 999;

    public johnson(int numberofvertices)
    {
        this.numberofvertices = numberofvertices;
    }

    public void johnson(int source, int[][] adjacencymatrix)
    {
        this.settled = new boolean[numberofvertices + 1];
        this.unsettled = new boolean[numberofvertices + 1];
        this.distances = new int[numberofvertices + 1];
        this.adjacencymatrix = new int[numberofvertices + 1][numberofvertices + 1];

        int evaluationnode;
        for (int vertex = 1; vertex <= numberofvertices; vertex++)
        {
            distances[vertex] = MAX_VALUE;
        }

        for (int sourcevertex = 0; sourcevertex <= numberofvertices-1; sourcevertex++)
        {
            for (int destinationvertex = 0; destinationvertex <= numberofvertices-1; destinationvertex++)
            {
                this.adjacencymatrix[sourcevertex][destinationvertex]
                        = adjacencymatrix[sourcevertex][destinationvertex];
            }
        }

        unsettled[source] = true;
        distances[source] = 0;
        while (getUnsettledCount(unsettled) != 0)
        {
            evaluationnode = getNodeWithMinimumDistanceFromUnsettled(unsettled);
            unsettled[evaluationnode] = false;
            settled[evaluationnode] = true;
            evaluateNeighbours(evaluationnode);
        }
    }

    public int getUnsettledCount(boolean unsettled[])
    {
        int count = 0;
        for (int vertex = 0; vertex <= numberofvertices-1; vertex++)
        {
            if (unsettled[vertex] == true)
            {
                count++;
            }
        }
        return count;
    }

    public int getNodeWithMinimumDistanceFromUnsettled(boolean unsettled[])
    {
        int min = MAX_VALUE;
        int node = 0;
        for (int vertex = 0; vertex <= numberofvertices-1; vertex++)
        {
            if (unsettled[vertex] == true && distances[vertex] < min)
            {
                node = vertex;
                min = distances[vertex];
            }
        }
        return node;
    }

    public void evaluateNeighbours(int evaluationNode)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int destinationNode = 0; destinationNode <= numberofvertices-1; destinationNode++)
        {
            if (settled[destinationNode] == false)
            {
                if (adjacencymatrix[evaluationNode][destinationNode] != MAX_VALUE)
                {
                    edgeDistance = adjacencymatrix[evaluationNode][destinationNode];
                    newDistance = distances[evaluationNode] + edgeDistance;
                    if (newDistance < distances[destinationNode])
                    {
                        distances[destinationNode] = newDistance;
                    }
                    unsettled[destinationNode] = true;
                }
            }
        }
    }

    public int[] getDistances()
    {
        return distances;
    }
}