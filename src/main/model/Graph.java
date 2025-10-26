package model;

import java.util.*;


public class Graph {
    public int id;
    public List<String> vertices;
    public List<Edge> edges;
    public Map<String, List<Edge>> adj;

    public Graph(int id, List<String> vertices) {
        this.id = id;
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adj = new HashMap<>();

        for (String v : vertices) {
            adj.put(v, new ArrayList<>());
        }
    }

    public void addEdge(String from, String to, double weight) {
        Edge e = new Edge(from, to, weight);
        edges.add(e);
        // неориентированный граф → добавляем оба направления
        adj.get(from).add(e);
        adj.get(to).add(new Edge(to, from, weight));
    }

    public int vertexCount() {
        return vertices.size();
    }

    public int edgeCount() {
        return edges.size();
    }
}
