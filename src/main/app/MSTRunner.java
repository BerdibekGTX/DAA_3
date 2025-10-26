package app;

import algorithms.Kruskal;
import algorithms.Prim;
import model.Graph;
import model.GraphLoader;
import model.MSTResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonWriter;

import java.util.List;

public class MSTRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("🚀 Starting MST analysis...");

        // Все входные файлы
        String[] inputFiles = {
                "data/assign3_small_input.json",
                "data/assign3_medium_input.json",
                "data/assign3_large_input.json",
                "data/assign3_extralarge_input.json"
        };

        JSONArray allResults = new JSONArray();

        for (String path : inputFiles) {
            System.out.println("\n📂 Processing dataset: " + path);

            List<Graph> graphs;
            try {
                graphs = GraphLoader.loadFromJson(path);
            } catch (Exception e) {
                System.out.println("⚠️  Skipping " + path + " (file is empty or invalid)");
                continue;
            }

            for (Graph g : graphs) {
                System.out.println("🔹 Analyzing graph ID: " + g.id + " (" + g.vertices.size() + " vertices)");

                JSONObject resultObj = new JSONObject();
                resultObj.put("graph_id", g.id);

                // 🔹 Добавляем input_stats для ReportBuilder
                JSONObject stats = new JSONObject();
                stats.put("vertices", g.vertices.size());
                stats.put("edges", g.edges.size());
                resultObj.put("input_stats", stats);

                // Prim
                Prim prim = new Prim();
                MSTResult primRes = prim.run(g);
                JSONObject primJson = new JSONObject();
                primJson.put("total_cost", primRes.totalCost);
                primJson.put("execution_time_ms", primRes.executionTimeMs);
                primJson.put("operations_count", primRes.operationsCount);
                primJson.put("mst_edges", primRes.getEdgesAsJson());
                resultObj.put("prim", primJson);

                // Kruskal
                Kruskal kruskal = new Kruskal();
                MSTResult kruskalRes = kruskal.run(g);
                JSONObject kruskalJson = new JSONObject();
                kruskalJson.put("total_cost", kruskalRes.totalCost);
                kruskalJson.put("execution_time_ms", kruskalRes.executionTimeMs);
                kruskalJson.put("operations_count", kruskalRes.operationsCount);
                kruskalJson.put("mst_edges", kruskalRes.getEdgesAsJson());
                resultObj.put("kruskal", kruskalJson);

                allResults.add(resultObj);
            }
        }

        JSONObject finalOutput = new JSONObject();
        finalOutput.put("results", allResults);
        JsonWriter.saveToFile(finalOutput, "data/output.json");

        System.out.println("\n✅ Analysis complete! Results saved to data/output.json");
    }
}
