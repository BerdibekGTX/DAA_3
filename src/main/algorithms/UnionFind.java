package algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Disjoint Set (Union-Find) structure with path compression and union by rank.
 * Used by Kruskal’s algorithm for cycle detection.
 */
public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    public UnionFind(List<String> vertices) {
        for (String v : vertices) {
            parent.put(v, v);
            rank.put(v, 0);
        }
    }

    public String find(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x))); // path compression
        }
        return parent.get(x);
    }

    public void union(String x, String y) {
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY)) return;

        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);

        if (rankX < rankY) {
            parent.put(rootX, rootY);
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }
    }
}
