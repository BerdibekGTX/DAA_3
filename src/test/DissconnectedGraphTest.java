import algorithms.Kruskal;
import algorithms.Prim;
import model.Graph;
import model.MSTResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DissconnectedGraphTest {

    @Test
    @DisplayName("Disconnected graph should have no MST")
    public void testDisconnectedGraph() {
        // создаём граф с двумя несвязанными компонентами
        Graph g = new Graph(2, Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 3);
        g.addEdge("C", "D", 2); // вторая компонента несвязна

        // создаём объекты алгоритмов
        Prim primAlgo = new Prim();
        Kruskal kruskalAlgo = new Kruskal();

        // запускаем алгоритмы
        MSTResult prim = primAlgo.run(g);
        MSTResult kruskal = kruskalAlgo.run(g);

        // проверяем, что MST не найден
        assertFalse(prim.mstExists, "Prim should detect disconnected graph");
        assertFalse(kruskal.mstExists, "Kruskal should detect disconnected graph");
    }
}
