package algorithms;

import model.Graph;
import model.Edge;
import model.MSTResult;
import java.util.*;

public class Kruskal {
    private int operationsCount;

    public MSTResult run(Graph graph) {
        operationsCount = 0;

        int n = graph.vertices.size();
        int m = graph.edges.size();

        System.out.printf("\n=== 🧩 KRUSKAL START | V=%d, E=%d ===%n", n, m);

        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        List<Edge> edges = graph.edges;

        long sortStart = System.nanoTime();
        edges.sort(Comparator.comparingDouble(e -> e.weight));
        long sortEnd = System.nanoTime();
        double sortTimeMs = (sortEnd - sortStart) / 1_000_000.0;

        long ufStart = System.nanoTime();
        UnionFind uf = new UnionFind(graph.vertices);
        int unionsPerformed = 0;

        for (Edge edge : edges) {
            if (mstEdges.size() == n - 1) break;

            String root1 = uf.find(edge.from);
            String root2 = uf.find(edge.to);
            operationsCount += 2; // two find operations

            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                uf.union(edge.from, edge.to);
                operationsCount++; // one union operation
                unionsPerformed++;
            }
        }

        long ufEnd = System.nanoTime();
        double ufTimeMs = (ufEnd - ufStart) / 1_000_000.0;

        long totalEnd = System.nanoTime();
        double totalTimeMs = (totalEnd - startTime) / 1_000_000.0;

        double totalCost = mstEdges.stream().mapToDouble(e -> e.weight).sum();

        boolean mstExists = mstEdges.size() == n - 1;
        if (!mstExists) {
            System.out.println("⚠️  Warning: Graph is disconnected — MST not possible!");
        }

        System.out.printf("""
                🔹 KRUSKAL SUMMARY
                   Vertices: %d | Edges: %d
                   MST edges: %d | Unions: %d
                   Sort time: %.2f ms | Union-Find time: %.2f ms
                   Total time: %.2f ms | Operations: %d
                ===============================================
                """, n, m, mstEdges.size(), unionsPerformed,
                sortTimeMs, ufTimeMs, totalTimeMs, operationsCount);

        return new MSTResult(
                "Kruskal",
                mstExists,
                mstEdges,
                totalCost,
                totalTimeMs,
                operationsCount
        );
    }
}
