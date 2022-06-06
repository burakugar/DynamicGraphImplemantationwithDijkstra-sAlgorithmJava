
import graph.DynamicGraph;
import graph.Edge;
import graph.Vertex;

public class main {
    public static void main(String[] args) {
        DynamicGraph<String, String> dynamicGraph = new DynamicGraph<>(DynamicGraph.directed);
        System.out.println("Adding vertexes A,B,C,D,E");
        Vertex<String, String> vertex0 = new Vertex<String, String>("A", 0, 1.0, 2);
        System.out.println("A is added: id: 0, weight: 1, boosting: 2");
        Vertex<String, String> vertex1 = new Vertex<String, String>("B", 1, 1.0, 3);
        System.out.println("B is added: id: 1, weight: 1, boosting: 3");
        Vertex<String, String> vertex2 = new Vertex<String, String>("C", 2, 1.0, 0);
        System.out.println("C is added: id: 2, weight: 1, boosting: 0");
        Vertex<String, String> vertex3 = new Vertex<String, String>("D", 3, 1.0, 3);
        System.out.println("D is added: id: 3, weight: 1, boosting: 3");
        Vertex<String, String> vertex4 = new Vertex<String, String>("E", 4, 1.0, 1);
        System.out.println("E is added: id: 4, weight: 1, boosting: 1");
        Vertex<String, String> vertex5 = new Vertex<String, String>("F", 5, 3.0, 2);
        System.out.println("F is added: id: 5, weight: 3, boosting: 2");

        vertex0.setLabel("A");
        vertex1.setLabel("B");
        vertex2.setLabel("C");
        vertex3.setLabel("D");
        vertex4.setLabel("E");
        vertex5.setLabel("F");

        dynamicGraph.addVertex(vertex0); // id= 0
        dynamicGraph.addVertex(vertex1); // id= 1
        dynamicGraph.addVertex(vertex2); // id= 2
        dynamicGraph.addVertex(vertex3); // id= 3
        dynamicGraph.addVertex(vertex4); // id= 4
        dynamicGraph.addVertex(vertex5); // id= 5


        System.out.println("Trying newVertex function: ");
        System.out.println("Adding new vertex with label F");
        System.out.println(dynamicGraph.newVertex("F", 2).toString());

        System.out.println("Trying filter graph function with key value label: ");
        System.out.println("Return graph" + dynamicGraph.filterVertices("F", "label").toString());

        System.out.println("Trying filter graph function with key value id: ");
        System.out.println("Return graph" + dynamicGraph.filterVertices("2", "id").toString());


        String output = "Vertices:\n";
        for (Vertex<String, String> v : dynamicGraph.vertices_array())
            output += String.format("%s ", v.toString());
        System.out.println(output);

        System.out.println("Removing the vertex F with id");
        dynamicGraph.removeVertex(4);
        output = "Vertices:\n";
        for (Vertex<String, String> v : dynamicGraph.vertices_array())
            output += String.format("%s ", v.toString());
        System.out.println(output);
        System.out.println("Adding the vertex F again");
        dynamicGraph.addVertex(vertex5); // id= 5
        output = "Vertices:\n";
        for (Vertex<String, String> v : dynamicGraph.vertices_array())
            output += String.format("%s ", v.toString());
        System.out.println(output);
        System.out.println("Removing the vertex F with label");
        dynamicGraph.removeVertex("F");

        output = "Vertices:\n";
        for (Vertex<String, String> v : dynamicGraph.vertices_array())
            output += String.format("%s ", v.toString());
        System.out.println(output);

        System.out.println("Adding edges:");
        System.out.println("Adding first edge from A to B with weight 10");
        dynamicGraph.addEdge(vertex0, vertex1, "1'th edge", 10);
        System.out.println("Adding second edge from A to C with weight 3");
        dynamicGraph.addEdge(vertex0, vertex2, "2'th edge", 3);
        System.out.println("Adding third edge from B to D with weight 2");
        dynamicGraph.addEdge(vertex1, vertex3, "3'th edge", 2);
        System.out.println("Adding fourth edge from B to C with weight 1");
        dynamicGraph.addEdge(vertex1, vertex2, "4'th edge", 1);
        System.out.println("Adding fifth edge from C to B with weight 4");
        dynamicGraph.addEdge(vertex2, vertex1, "5'th edge", 4);
        System.out.println("Adding sixth edge from C to D with weight 8");
        dynamicGraph.addEdge(vertex2, vertex3, "6'th edge", 8);
        System.out.println("Adding seventh edge from C to E with weight 2");
        dynamicGraph.addEdge(vertex2, vertex4, "7'th edge", 2);
        System.out.println("Adding eighth edge from D to E with weight 7");
        dynamicGraph.addEdge(vertex3, vertex4, "8'th edge", 7);
        System.out.println("Adding nighth edge from E to D with weight 9");
        dynamicGraph.addEdge(vertex4, vertex3, "9'th edge", 9);
        System.out.println("Adding nighth edge from E to B with weight 4");
        dynamicGraph.addEdge(vertex4, vertex1, "10'th edge", 4);
        output = "Edges:\n";
        for (Edge<String, String> e : dynamicGraph.edges_array())
            output += String.format("%s ", e.toString());
        System.out.println(output);

        System.out.println(dynamicGraph);
        dynamicGraph.addVertex(vertex5); // id= 5

        System.out.println("Applying BFS to graph:");
        for (Vertex<String, String> v : dynamicGraph.BFS())
            System.out.print(v + " ");

        System.out.println("\n\nApplying DFS to graph:");
        for (Vertex<String, String> v : dynamicGraph.DFS())
            System.out.print(v + " ");

        System.out.println("\n\nIs graph connected?: " + dynamicGraph.isConnected());
        System.out.println("Is graph directed? : " + dynamicGraph.isDirected());
        System.out.println("Number of Connected Components: " + dynamicGraph.connectedComponents());

        System.out.println("Testing the export matrix function: ");
        dynamicGraph.addEdge(0, 1);
        dynamicGraph.addEdge(0, 2);
        dynamicGraph.addEdge(1, 3);
        dynamicGraph.addEdge(1, 2);
        dynamicGraph.addEdge(2, 1);
        dynamicGraph.addEdge(2, 3);
        dynamicGraph.addEdge(2, 4);
        dynamicGraph.addEdge(3, 4);
        dynamicGraph.addEdge(4, 3);
        dynamicGraph.addEdge(4, 1);
        dynamicGraph.printGraph(dynamicGraph.exportMatrix());
        System.out.println("Testing the Adjacency List printGraph fucntion");
        dynamicGraph.printGraph(dynamicGraph.getAm());

        System.out.println("Part 2 - Calculating the difference between DFS and BFS algorithm paths:");
        dynamicGraph.calculateDFSandBFSNumbers();
        System.out.println(dynamicGraph.getBfsnumber());
        System.out.println(dynamicGraph.getDfsnumber());


        System.out.println("Part 3 - Boosting algorithm: ");
        System.out.println("Applying boosting for vertex 0: ");
        dynamicGraph.dijkstra(vertex0);
        System.out.println();
        System.out.println("Applying boosting for vertex 1: ");
        dynamicGraph.dijkstra(vertex1);
        System.out.println();
        System.out.println("Applying boosting for vertex 2: ");
        dynamicGraph.dijkstra(vertex2);
        System.out.println();
        System.out.println("Applying boosting for vertex 3: ");
        dynamicGraph.dijkstra(vertex3);
        System.out.println();
        System.out.println("Applying boosting for vertex 4: ");
        dynamicGraph.dijkstra(vertex4);
        System.out.println();


    }
}
