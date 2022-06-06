package graph;


import graph.DoubleLinkedList.DoubleLinkedList;
import graph.DoubleLinkedList.Node;
import graph.DoubleLinkedList.NodeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DynamicGraph<E, T> implements Graph {

    // Graph content
    private DoubleLinkedList<Vertex<E, T>> vertexList;
    private DoubleLinkedList<Edge<E, T>> edgeList;
    List<Vertex<E, T>> vertexLastList = new ArrayList<>(10);
    // Graph options
    public static boolean directed;
    private boolean isConnected;
    private int connectedComponents;
    private int dfsnumber = 0;
    private int bfsnumber = 0;

    public int getDfsnumber() {
        return dfsnumber;
    }

    public int getBfsnumber() {
        return bfsnumber;
    }

    public ArrayList<ArrayList<Integer>> getAm() {
        return am;
    }

    ArrayList<ArrayList<Integer>> am = new ArrayList<>(100);
    /**
     * Constructor
     *
     * @param directed true if the graph is directed, false if undirected
     */
    public DynamicGraph(boolean directed) {
        for (int i = 0; i < 10; i++)
            am.add(new ArrayList<Integer>());
        vertexList = new DoubleLinkedList<Vertex<E, T>>();
        edgeList = new DoubleLinkedList<Edge<E, T>>();
        this.directed = directed;
    }

    /**
     * Add Edge between two vertices
     *
     * @param v1
     * @param v2
     * @param label
     * @param weight
     * @return Array of 2 edges if the graph is undirected, array of 1 edge if the graph is directed
     */
    public Edge<E, T>[] addEdge(Vertex<E, T> v1, Vertex<E, T> v2, T label, double weight) {
        am.get(v1.getID()).add(v2.getID());
        am.get(v2.getID()).add(v1.getID());
        Edge<E, T> edges[] = new Edge[directed ? 1 : 2];

        // Create the first edge from v1 to v2 and set its configuration
        edges[0] = new Edge<E, T>(v1, v2);
        edges[0].setLabel(label);
        edges[0].setWeight(weight);
        edges[0].setPosition(edgeList.add(edges[0]));
        // If graph is undirected, create an edge in the opposite direction
        if (!directed) {

            // Create the second edge from v2 to v1 and set its configuration
            edges[1] = new Edge<E, T>(v2, v1);
            edges[1].setLabel(label);
            edges[1].setWeight(weight);
            edges[1].setPosition(edgeList.add(edges[1]));
        }
        return edges;
    }

    /**
     * Filters the vertices with given filter(id or label)
     * @param key
     * @param filter
     * @return
     */
    public DynamicGraph<E, T> filterVertices(String key, String filter) {
        DynamicGraph<E, T> newGraph = new DynamicGraph<E, T>(true);
        if (filter=="label"){
            for (int i = 0; i < vertexLastList.size(); i++) {
                if (key == vertexLastList.get(i).getLabel()) {
                    newGraph.addVertex(vertexLastList.get(i));
                }
            }
        }
        else if (filter=="id"){
            for (int i = 0; i < vertexLastList.size(); i++) {
                if (Integer.valueOf(key) == vertexLastList.get(i).getID()) {
                    newGraph.addVertex(vertexLastList.get(i));
                }
            }
        }
        return newGraph;
    }

    /**
     * Remove vertex
     */
    public void removeVertex(Integer id) {
        Vertex<E, T> vertex = null;
        for (int i = 0; i < vertexLastList.size(); i++) {
            if (vertexLastList.get(i).getID() == id) {
                vertex = vertexLastList.get(i);
            }
        }

        // Remove outgoing edges & trigger
        NodeIterator<Edge<E, T>> iterOutEdges = vertex.getOutEdges();
        while (iterOutEdges.hasNext()) {
            Edge<E, T> currentE = iterOutEdges.next();
            Vertex<E, T> vTo = currentE.getV2();

            // Remove edge from inEdge of V2
            vTo.removeInEdge(currentE.getIncidentPositionV2());

            // Remove edge from the graph content
            edgeList.remove(currentE.getPosition());
        }

        // Remove ingoing edges & trigger
        NodeIterator<Edge<E, T>> iterInEdges = vertex.getInEdges();
        while (iterInEdges.hasNext()) {
            Edge<E, T> currentE = iterInEdges.next();
            Vertex<E, T> vFrom = currentE.getV1();

            // Remove edge from outEdge of V1
            vFrom.removeOutEdge(currentE.getIncidentPositionV1());

            // Remove edge from the graph content
            edgeList.remove(currentE.getPosition());
        }

        // Remove vertex
        vertexList.remove(vertex.getPosition());
    }

    /**
     * Generate a new vertex by given parameters.
     * @param label label of the vertex
     * @param weight weight of the vertex
     * @return the vertexs itself
     */
    public Vertex<E, T> newVertex(String label, double weight) {
        Vertex<E, T> vertex5 = (Vertex<E, T>) new Vertex<String, String>(label, vertexList.size(), weight, 0);
        vertex5.setLabel(label);
        return addVertex(vertex5);
    }

    /**
     * removes the given vertex
     * @param label vertex label to be removed
     */

    public void removeVertex(String label) {
        Vertex<E, T> vertex = null;
        for (int i = 0; i < vertexLastList.size(); i++) {
            if (vertexLastList.get(i).getLabel() == label) {
                vertex = vertexLastList.get(i);
            }
        }

        // Remove outgoing edges & trigger
        NodeIterator<Edge<E, T>> iterOutEdges = vertex.getOutEdges();
        while (iterOutEdges.hasNext()) {
            Edge<E, T> currentE = iterOutEdges.next();
            Vertex<E, T> vTo = currentE.getV2();

            // Remove edge from inEdge of V2
            vTo.removeInEdge(currentE.getIncidentPositionV2());

            // Remove edge from the graph content
            edgeList.remove(currentE.getPosition());
        }

        // Remove ingoing edges & trigger
        NodeIterator<Edge<E, T>> iterInEdges = vertex.getInEdges();
        while (iterInEdges.hasNext()) {
            Edge<E, T> currentE = iterInEdges.next();
            Vertex<E, T> vFrom = currentE.getV1();

            // Remove edge from outEdge of V1
            vFrom.removeOutEdge(currentE.getIncidentPositionV1());

            // Remove edge from the graph content
            edgeList.remove(currentE.getPosition());
        }

        // Remove vertex
        vertexList.remove(vertex.getPosition());
    }

    /**
     * Breadth-First-Search
     *
     * @return Array of vertices traversed by BFS
     */
    public Vertex<E, T>[] BFS() {
        Vertex<E, T>[] BFS = new Vertex[vertexList.size()];
        int index = 0;

        // Mark all vertices as unvisited
        NodeIterator<Vertex<E, T>> iterV = vertices();
        while (iterV.hasNext()) iterV.next().setStatus(Vertex.UNVISITED);

        // Mark all edges as undiscovered
        NodeIterator<Edge<E, T>> iterE = edges();
        while (iterE.hasNext()) iterE.next().setStatus(Edge.UNDISCOVERED);

        // Start BFS
        iterV = vertices();
        while (iterV.hasNext()) {

            Vertex<E, T> current = iterV.next();
            if (current.getStatus() == Vertex.UNVISITED) {

                // Add the starting vertex and mark it as visiting
                Queue<Vertex<E, T>> q = new LinkedList<Vertex<E, T>>();
                q.add(current);
                current.setStatus(Vertex.VISITING);
                while (!q.isEmpty()) {

                    // Remove a vertex from the queue and mark it as visited
                    Vertex<E, T> polled = q.poll();
                    BFS[index++] = polled;
                    polled.setStatus(Vertex.VISITED);

                    // Iterator on all neighbors of the removed vertex and add them to the queue
                    NodeIterator<Edge<E, T>> incidentEdges = polled.getOutEdges();
                    while (incidentEdges.hasNext()) {

                        Edge<E, T> edge = incidentEdges.next();
                        Vertex<E, T> oppositeVertex = edge.getV2();

                        // If neighbor is not already visited, put it in the queue
                        if (oppositeVertex.getStatus() == Vertex.UNVISITED) {
                            bfsnumber++;
                            // Mark edge between the removed vertex and the current neighbor as discovered
                            edge.setStatus(Edge.DISCOVERED);
                            oppositeVertex.setStatus(Vertex.VISITING);
                            q.offer(oppositeVertex);

                            // If neighbor has already been visited, don't put it in the queue
                        } else {

                            // Mark edge as cross if undiscovered
                            if (edge.getStatus() == Edge.UNDISCOVERED) edge.setStatus(Edge.CROSS);
                        }
                    }

                }
            }
        }
        return BFS;
    }

    /**
     * Depth-First-Search
     *
     * @return Array of vertices traversed by DFS
     */
    public Vertex<E, T>[] DFS() {
        Vertex<E, T>[] DFS = new Vertex[vertexList.size()];
        int index[] = {0};

        // Configure Graph options
        this.connectedComponents = 0;

        // Mark all vertices as unvisited and uncolored
        NodeIterator<Vertex<E, T>> iterV = vertices();
        while (iterV.hasNext()) {
            Vertex<E, T> currentV = iterV.next();
            currentV.setStatus(Vertex.UNVISITED);
            currentV.setColor(Vertex.UNCOLORED);
        }

        // Mark all edges as undiscovered
        NodeIterator<Edge<E, T>> iterE = edges();
        while (iterE.hasNext()) iterE.next().setStatus(Edge.UNDISCOVERED);

        // Start DFS
        iterV = vertices();
        while (iterV.hasNext()) {
            Vertex<E, T> current = iterV.next();
            if (current.getStatus() == Vertex.UNVISITED) {

                // +1 disconnected graph, trigger connection detection
                this.connectedComponents++;
                this.isConnected = this.connectedComponents == 1;
                DFS(current, DFS, index);
            }
        }
        return DFS;
    }

    /**
     * Recursive DFS that generates the content of DFS[]
     *
     * @param v vertex to be iterated over
     * @param DFS helper function
     * @param index index of the vertex array
     */
    private void DFS(Vertex<E, T> v, Vertex<E, T>[] DFS, int[] index) {
        // Color all vertices with the same color for each vertex start ((v0-> v1) <- v2) [for DiGraph]
        v.setColor(connectedComponents);
        v.setStatus(Vertex.VISITING);
        DFS[index[0]++] = v;

        // Iterate on all neighbors of the current vertex
        NodeIterator<Edge<E, T>> incidentEdges = v.getOutEdges();
        while (incidentEdges.hasNext()) {

            Edge<E, T> edge = incidentEdges.next();
            Vertex<E, T> oppositeVertex = edge.getV2();
            // Recur on neighbor if not visited
            if (oppositeVertex.getStatus() == Vertex.UNVISITED) {
                dfsnumber++;
                edge.setStatus(Edge.DISCOVERED);
                oppositeVertex.setStatus(Vertex.VISITING);
                DFS(oppositeVertex, DFS, index);
            } else {

                // Checks if the undirected/directed graph is cyclic
                if ((!directed && oppositeVertex.getStatus() == Vertex.VISITED) || (directed && oppositeVertex.getStatus() == Vertex.VISITING && v.getColor() == oppositeVertex.getColor()) // Third condition is for DiGraph (Check earlier this method...)
                ) {
                }

                /// Mark edge as cross if the undiscovered
                if (edge.getStatus() == Edge.UNDISCOVERED) edge.setStatus(Edge.CROSS);
            }
        }

        // Mark vertex as visited if more neighbors needs to be visited
        v.setStatus(Vertex.VISITED);
    }


    /**
     * Get an iterator for the list of vertices
     *
     * @return NodeIterator of vertices
     */
    public NodeIterator<Vertex<E, T>> vertices() {
        return vertexList.iterator();
    }

    /**
     * Get an iterator for the list of edges
     *
     * @return NodeIterator of edges
     */
    public NodeIterator<Edge<E, T>> edges() {
        return edgeList.iterator();
    }

    /**
     * Get an array of the list of vertices
     *
     * @return Array of vertices
     */
    public Vertex<E, T>[] vertices_array() {
        Vertex<E, T>[] tmp = new Vertex[vertexList.size()];
        NodeIterator<Vertex<E, T>> iter = vertices();
        int index = 0;
        while (iter.hasNext()) tmp[index++] = iter.next();
        return tmp;
    }

    /**
     * Get an array of the list of vertices
     *
     * @return Array of vertices
     */
    public Edge<E, T>[] edges_array() {
        Edge<E, T>[] tmp = new Edge[edgeList.size()];
        NodeIterator<Edge<E, T>> iter = edges();
        int index = 0;
        while (iter.hasNext()) tmp[index++] = iter.next();
        return tmp;
    }

    @Override
    public int getNumV() {
        return vertexList.size();
    }

    /**
     * Checks if the graph is directed or not
     *
     * @return boolean
     */
    public boolean isDirected() {
        return directed;
    }

    @Override
    public void insert(Edge e) {

    }

    @Override
    public boolean isEdge(int source, int dest) {
        return false;
    }

    @Override
    public Edge getEdge(int source, int dest) {
        return null;
    }

    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return null;
    }

    /**
     * Checks if the graph is connected
     *
     * @return boolean
     */
    public boolean isConnected() {
        if (directed) BFS_helper();
        else DFS();
        return isConnected;
    }

    /**
     * Gives the number of connected components
     *
     * @return connected components
     */
    public int connectedComponents() {
        if (directed) BFS_helper();
        else DFS();
        return connectedComponents;
    }

    /**
     * Create the shortest path from a vertex to all other vertices
     *
     * @param v Starting vertex
     */
    public void dijkstra(Vertex<E, T> v) {
        // Mark all vertices as unvisited and reset Dijkstra options
        NodeIterator<Vertex<E, T>> iterV = vertices();
        while (iterV.hasNext()) {
            Vertex<E, T> currentV = iterV.next();
            currentV.setStatus(Vertex.UNVISITED);
            currentV.setDijkstra_value(Double.MAX_VALUE);
            currentV.setDijkstra_parent(null);
        }

        // Mark all edges as undiscovered
        NodeIterator<Edge<E, T>> iterE = edges();
        while (iterE.hasNext()) iterE.next().setStatus(Edge.UNDISCOVERED);

        // Mark the starting vertex
        v.setDijkstra_value(0);

        // Create the Priority Queue (Using a heap)
        PriorityQueue<Vertex<E, T>> pq = new PriorityQueue<>();

        // Start from the starting vertex by putting it in the Priority queue
        pq.offer(v);
        v.setStatus(Vertex.VISITING);
        v.setDijkstra_parent(v);
        while (!pq.isEmpty()) {
            // Remove the vertex with minimum Dijkstra value
            Vertex<E, T> polled = pq.poll();
            v.setStatus(Vertex.VISITED);
            NodeIterator<Edge<E, T>> incidentEdges = polled.getOutEdges();

            // Put all the neighbors of the removed vertex in the Priority queue and adjust their Dijkstra value and parent
            while (incidentEdges.hasNext()) {
                Edge<E, T> edge = incidentEdges.next();
                Vertex<E, T> oppositeVertex = edge.getV2();
                double pathCost = edge.getWeight() + polled.getDijkstra_value();
                System.out.println("Current vertex is:" + oppositeVertex.getLabel());
                System.out.println("Current Path Cost is: " + pathCost);
                System.out.println("Boosting value of " + oppositeVertex.getLabel() + "is " + oppositeVertex.getBoosting());
                System.out.println("Apply the boosting");
                pathCost -= oppositeVertex.getBoosting();
                System.out.println("After boosting, current path cost is: ");
                System.out.println(pathCost);
                // If the neighbor has not been visited, mark it visiting and adjust its configuration
                if (oppositeVertex.getStatus() == Vertex.UNVISITED) {
                    oppositeVertex.setDijkstra_value(pathCost);
                    oppositeVertex.setDijkstra_edge(edge);
                    edge.setStatus(Edge.DISCOVERED);
                    oppositeVertex.setStatus(Vertex.VISITING);
                    oppositeVertex.setDijkstra_parent(polled);
                    pq.offer(oppositeVertex);

                    // If the neighbor is still in the priority queue, check for minimum path cost, adjust if the cost can be reduced
                } else if (oppositeVertex.getStatus() == Vertex.VISITING) {

                    if (oppositeVertex.getDijkstra_value() > pathCost) {
                        oppositeVertex.setDijkstra_value(pathCost);
                        edge.setStatus(Edge.DISCOVERED);
                        oppositeVertex.setDijkstra_parent(polled);
                        oppositeVertex.getDijkstra_edge().setStatus(Edge.FORWARD); // Mark previous edge as FORWARD
                        oppositeVertex.setDijkstra_edge(edge); // Update edge that makes it shortest path
                    }
                }
            }
        }
    }

    /**
     * Gives all the vertices and edges that form this graph
     *
     * @return String
     */
    ArrayList<String> prevList = new ArrayList<>(0);

    /**
     * toString method
     * @return
     */
    public String toString() {
        String output = "Vertices:\n";
        for (Vertex<E, T> v : vertices_array())
            output += String.format("%s ", v.toString());

        output += "\n\nEdges:\n";
        for (Edge<E, T> e : edges_array()) {
            if (!prevList.contains(e.toString()))
                output += String.format("%s\n", e.toString());
            prevList.add(e.toString());
        }
        return output;
    }

/* Helper functions */
    /**
     * BFS for detecting connected components and is connected in DiGraphs
     * Idea is to consider the DiGraph as UnDiGraph by concatenating the in and out edges
     */
    private Vertex<E, T>[] BFS_helper() {
        Vertex<E, T>[] BFS = new Vertex[vertexList.size()];
        int index = 0;

        // Configure DiGraph options
        this.connectedComponents = 0;

        // Mark all vertices as unvisited
        NodeIterator<Vertex<E, T>> iterV = vertices();
        while (iterV.hasNext()) iterV.next().setStatus(Vertex.UNVISITED);

        // Mark all edges as undiscovered
        NodeIterator<Edge<E, T>> iterE = edges();
        while (iterE.hasNext()) iterE.next().setStatus(Edge.UNDISCOVERED);

        // Start BFS
        iterV = vertices();
        while (iterV.hasNext()) {
            Vertex<E, T> current = iterV.next();
            if (current.getStatus() == Vertex.UNVISITED) {

                // +1 disconnected graph, trigger connection detection
                this.connectedComponents++;
                this.isConnected = this.connectedComponents == 1;

                Queue<Vertex<E, T>> q = new LinkedList<Vertex<E, T>>();
                q.add(current);
                current.setStatus(Vertex.VISITING);
                while (!q.isEmpty()) {
                    Vertex<E, T> polled = q.poll();
                    BFS[index++] = polled;
                    polled.setStatus(Vertex.VISITED);

                    NodeIterator<Edge<E, T>> inOutEdges = polled.getOutEdges().concatenate(polled.getInEdges());
                    while (inOutEdges.hasNext()) {
                        Edge<E, T> edge = inOutEdges.next();
                        Vertex<E, T> oppositeVertex = edge.getOpposite(polled);
                        if (oppositeVertex.getStatus() == Vertex.UNVISITED) {
                            edge.setStatus(Edge.DISCOVERED);
                            oppositeVertex.setStatus(Vertex.VISITING);
                            q.offer(oppositeVertex);
                        } else {
                            if (edge.getStatus() == Edge.UNDISCOVERED) edge.setStatus(Edge.CROSS);
                        }
                    }
                }
            }
        }
        return BFS;
    }

    /**
     * Add vertex to the graph with custom ID
     * Private to avoid possible conflict if used manually
     *
     * @return Vertex
     */
    public Vertex<E, T> addVertex(Vertex<E, T> vertex) {
        vertexLastList.add(vertex);
        Node<Vertex<E, T>> node = vertexList.add(vertex);
        vertex.setPosition(node);
        return vertex;
    }

    /**
     * Add vertex to the graph with custom ID
     * Private to avoid possible conflict if used manually
     *
     * @param data
     * @return Vertex
     */
    private Vertex<E, T> addVertex(E data, int id) {
        Vertex<E, T> vertex = new Vertex<E, T>(data, id);
        Node<Vertex<E, T>> node = vertexList.add(vertex);
        vertex.setPosition(node);
        return vertex;
    }

    /**
     * prints the adjacent matrix
     * @param adj adjacent matrix
     */
    public void printGraph(ArrayList<ArrayList<Integer>> adj) {
        for (int i = 0; i < vertexList.size(); i++) {
            System.out.println("\nAdjacency list of vertex" + i);
            System.out.print("head");
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(" -> " + adj.get(i).get(j));
            }
            System.out.println();
        }
    }

    /**
     * calculates the number differences between BFS and DFS
     */
    public void calculateDFSandBFSNumbers(DynamicGraph<E, T>this) {
        System.out.println("DFS number of path: " + dfsnumber);
        System.out.println("BFS number of path: " + bfsnumber);
        System.out.println("Difference between BFS and DFS distance is: " + Math.abs(dfsnumber - bfsnumber));
    }

    int vertex = 5;
    int matrix[][] = new int[10][10];

    /**
     * adds new edge into graph
     * @param source
     * @param destination
     */
    public void addEdge(int source, int destination) {
        //add edge
        matrix[source][destination] = 1;

        //add bak edge for undirected graph
        matrix[destination][source] = 1;
    }

    /**
     *
     * @return the matrix
     */
    public int[][] exportMatrix() {
        return matrix;
    }

    /**
     * prints the graph as matrix
     * @param matrix1 matrix to be printed
     */
    public void printGraph(int[][] matrix1) {
        System.out.println("Graph: (Adjacency Matrix)");
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                System.out.print(matrix1[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < vertex; i++) {
            System.out.print("Vertex " + i + " is connected to:");
            for (int j = 0; j < vertex; j++) {
                if (matrix1[i][j] == 1) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
}
