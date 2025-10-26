import algorithms.Kruskal;
import algorithms.Prim;
import model.Graph;
import model.MSTResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MSTCorrectnessTest {

    @Test
    @DisplayName("Prim and Kruskal produce identical MST cost")
    public void testMSTCostEquality() {
        // создаём связный граф
        Graph g = new Graph(1, Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);
        g.addEdge("B", "D", 5);

        // создаём экземпляры алгоритмов
        Prim primAlgo = new Prim();
        Kruskal kruskalAlgo = new Kruskal();

        // запускаем
        MSTResult prim = primAlgo.run(g);
        MSTResult kruskal = kruskalAlgo.run(g);

        // проверки
        assertTrue(prim.mstExists, "Prim MST should exist");
        assertTrue(kruskal.mstExists, "Kruskal MST should exist");

        assertEquals(prim.totalCost, kruskal.totalCost, 1e-6, "MST costs must match");
        assertEquals(g.vertices.size() - 1, prim.mstEdges.size(), "MST must have |V|-1 edges");
    }
}
