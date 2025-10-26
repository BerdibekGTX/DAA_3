package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.CsvExporter;

import java.io.FileReader;

/**
 * Собирает CSV-отчёт по результатам анализа MST.
 */
public class ReportBuilder {

    public static void main(String[] args) throws Exception {
        System.out.println("📊 Building MST report...");

        JSONParser parser = new JSONParser();
        JSONObject root;

        try (FileReader reader = new FileReader("data/output.json")) {
            root = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            System.out.println("❌ Error: Cannot read data/output.json — file missing or corrupted.");
            return;
        }

        JSONArray results = (JSONArray) root.get("results");
        if (results == null || results.isEmpty()) {
            System.out.println("⚠️  No results found in data/output.json");
            return;
        }

        // 🔹 Проверим структуру перед экспортом
        for (Object obj : results) {
            JSONObject result = (JSONObject) obj;

            // Проверим наличие поля input_stats
            JSONObject stats = (JSONObject) result.get("input_stats");
            if (stats == null) {
                // fallback для старых версий, где поля были на верхнем уровне
                Long vertices = (Long) result.getOrDefault("vertices", 0L);
                Long edges = (Long) result.getOrDefault("edges", 0L);

                stats = new JSONObject();
                stats.put("vertices", vertices);
                stats.put("edges", edges);
                result.put("input_stats", stats);

                System.out.println("⚠️  Reconstructed input_stats for graph ID: " + result.get("graph_id"));
            }
        }

        try {
            CsvExporter.export(results, "data/results.csv");
            System.out.println("✅ Report ready: data/results.csv");
        } catch (Exception e) {
            System.out.println("❌ Error writing CSV: " + e.getMessage());
        }
    }
}
