package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;


public class CsvExporter {

    public static void export(JSONArray results, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write(
                    "Graph ID,Vertices,Edges,"
                            + "Prim Cost,Prim Time (ms),Prim Ops,"
                            + "Kruskal Cost,Kruskal Time (ms),Kruskal Ops,"
                            + "Δ Time (ms),Δ Operations (%),Faster Algorithm\n"
            );

            for (Object obj : results) {
                JSONObject result = (JSONObject) obj;
                JSONObject stats = (JSONObject) result.get("input_stats");

                long id = (long) result.getOrDefault("graph_id", 0L);
                long vertices = (long) stats.getOrDefault("vertices", 0L);
                long edges = (long) stats.getOrDefault("edges", 0L);

                JSONObject prim = (JSONObject) result.get("prim");
                JSONObject kruskal = (JSONObject) result.get("kruskal");


                double primCost = getDouble(prim, "total_cost");
                double primTime = getDouble(prim, "execution_time_ms", "time_ms");
                long primOps = getLong(prim, "operations_count", "operations");

                double kruskalCost = getDouble(kruskal, "total_cost");
                double kruskalTime = getDouble(kruskal, "execution_time_ms", "time_ms");
                long kruskalOps = getLong(kruskal, "operations_count", "operations");

                double deltaTime = primTime - kruskalTime;

                double deltaOps = (primOps == 0)
                        ? 0.0
                        : ((double) (kruskalOps - primOps) / primOps) * 100.0;

                String faster =
                        (primTime < kruskalTime) ? "Prim"
                                : (kruskalTime < primTime) ? "Kruskal" : "Equal";

                writer.write(String.format(
                        "%d,%d,%d,%.2f,%.3f,%d,%.2f,%.3f,%d,%.3f,%.2f,%s\n",
                        id, vertices, edges,
                        primCost, primTime, primOps,
                        kruskalCost, kruskalTime, kruskalOps,
                        deltaTime, deltaOps, faster
                ));
            }

            System.out.println("✅ CSV saved: " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Error writing CSV: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    private static double getDouble(JSONObject obj, String... keys) {
        if (obj == null) return 0.0;
        for (String key : keys) {
            Object val = obj.get(key);
            if (val instanceof Number) return ((Number) val).doubleValue();
        }
        return 0.0;
    }

    private static long getLong(JSONObject obj, String... keys) {
        if (obj == null) return 0L;
        for (String key : keys) {
            Object val = obj.get(key);
            if (val instanceof Number) return ((Number) val).longValue();
        }
        return 0L;
    }
}
