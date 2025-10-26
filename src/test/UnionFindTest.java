package algorithms;

import java.util.HashMap;
import java.util.Map;

public class UnionFindTest {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    // Default constructor (чтобы можно было вызывать makeSet вручную)
    public UnionFindTest() {}

    // Constructor for graph initialization
    public UnionFindTest(java.util.List<String> vertices) {
        for (String v : vertices) {
            makeSet(v);
        }
    }

    // 🧩 Добавляем makeSet
    public void makeSet(String v) {
        parent.put(v, v);
        rank.put(v, 0);
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
