package algorithms;

import model.Graph;
import model.Edge;
import model.MSTResult;

import java.util.*;

/**
 * Optimized implementation of Prim's algorithm for Minimum Spanning Tree.
 * Uses priority queue (min-heap) and adjacency list built from edges.
 */
public class Prim {
    private int operationsCount;

    public MSTResult run(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        List<String> vertices = graph.vertices;
        int n = vertices.size();

        if (n == 0) {
            return new MSTResult("Prim", false, new ArrayList<>(), 0, 0.0, 0);
        }

        // --- Build adjacency list from edge list (if getAdjacencyList() doesn't exist) ---
        Map<String, List<Edge>> adjacencyList = new HashMap<>();
        for (Edge e : graph.edges) {
            adjacencyList.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e);
            adjacencyList.computeIfAbsent(e.to, k -> new ArrayList<>()).add(e);
        }

        Map<String, Integer> vertexIndex = new HashMap<>(n);
        for (int i = 0; i < n; i++) vertexIndex.put(vertices.get(i), i);

        boolean[] visited = new boolean[n];
        double[] minWeight = new double[n];
        Edge[] bestEdge = new Edge[n];
        Arrays.fill(minWeight, Double.POSITIVE_INFINITY);

        // --- Min-heap for edges (weight, vertex index) ---
        class Node implements Comparable<Node> {
            int vertex;
            double weight;
            Node(int vertex, double weight) { this.vertex = vertex; this.weight = weight; }
            public int compareTo(Node o) { return Double.compare(this.weight, o.weight); }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        minWeight[0] = 0;
        pq.offer(new Node(0, 0.0));

        List<Edge> mstEdges = new ArrayList<>(n - 1);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;
            operationsCount++;

            // Add edge to MST
            if (bestEdge[u] != null) {
                mstEdges.add(bestEdge[u]);
                operationsCount++;
            }

            List<Edge> neighbors = adjacencyList.get(vertices.get(u));
            if (neighbors == null) continue;

            for (Edge edge : neighbors) {
                operationsCount++;
                String neighbor = edge.from.equals(vertices.get(u)) ? edge.to : edge.from;
                Integer v = vertexIndex.get(neighbor);
                if (v == null || visited[v]) continue;

                if (edge.weight < minWeight[v]) {
                    minWeight[v] = edge.weight;
                    bestEdge[v] = edge;
                    pq.offer(new Node(v, edge.weight));
                    operationsCount += 3;
                }
            }
        }

        long endTime = System.nanoTime();
        double totalTimeMs = (endTime - startTime) / 1_000_000.0;

        double totalCost = mstEdges.stream().mapToDouble(e -> e.weight).sum();
        boolean mstExists = mstEdges.size() == n - 1;

        if (!mstExists) {
            System.out.println("⚠️  Warning: Graph is disconnected — MST not possible!");
        }

        System.out.printf("""
                🔹 PRIM SUMMARY
                   Vertices: %d | Edges: %d
                   MST edges: %d | Total cost: %.2f
                   Total time: %.2f ms | Operations: %d
                ===============================================
                """, n, graph.edges.size(), mstEdges.size(), totalCost, totalTimeMs, operationsCount);

        return new MSTResult(
                "Prim",
                mstExists,
                mstEdges,
                totalCost,
                totalTimeMs,
                operationsCount
        );
    }
}
