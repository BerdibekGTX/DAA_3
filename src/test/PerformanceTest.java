import algorithms.Kruskal;
import algorithms.Prim;
import model.Graph;
import model.MSTResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {

    @Test
    @DisplayName("Performance test on large random graph")
    public void testLargeGraphPerformance() {
        int V = 100;
        Graph g = new Graph(99, new ArrayList<>());

        // добавляем вершины
        for (int i = 0; i < V; i++)
            g.vertices.add("V" + i);

        // добавляем случайные рёбра
        Random rand = new Random(42);
        for (int i = 0; i < V * 5; i++) {
            String u = g.vertices.get(rand.nextInt(V));
            String v = g.vertices.get(rand.nextInt(V));
            if (u.equals(v)) continue;
            g.addEdge(u, v, 1 + rand.nextInt(100));
        }

        // создаём экземпляры алгоритмов
        Prim primAlgo = new Prim();
        Kruskal kruskalAlgo = new Kruskal();

        // запускаем оба алгоритма
        MSTResult prim = primAlgo.run(g);
        MSTResult kruskal = kruskalAlgo.run(g);

        // проверки корректности и производительности
        assertTrue(prim.executionTimeMs < 5000, "Prim should run under 5 seconds");
        assertTrue(kruskal.executionTimeMs < 5000, "Kruskal should run under 5 seconds");
        assertEquals(prim.totalCost, kruskal.totalCost, 1e-6, "MST total cost must match");
    }
}
