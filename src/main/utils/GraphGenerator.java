package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.*;

public class GraphGenerator {

    public static void generateAll() throws Exception {
        generateGraphs("data/assign3_small_input.json", 5, 4, 6, 5, 30);
        generateGraphs("data/assign3_medium_input.json", 10, 10, 15, 30, 300);
        generateGraphs("data/assign3_large_input.json", 10, 20, 30, 300, 1000);
        generateGraphs("data/assign3_extralarge_input.json", 5, 40, 80, 1000, 2000);
        System.out.println("✅ All input datasets generated in /data/");
    }

    @SuppressWarnings("unchecked")
    private static void generateGraphs(String path, int count, int minV, int maxV, int minE, int maxE) throws Exception {
        JSONObject root = new JSONObject();
        JSONArray graphs = new JSONArray();
        Random rand = new Random();

        for (int i = 1; i <= count; i++) {
            int vertices = minV + rand.nextInt(maxV - minV + 1);
            int edgesCount = minE + rand.nextInt(maxE - minE + 1);
            int maxEdgesPossible = vertices * (vertices - 1) / 2;
            edgesCount = Math.min(edgesCount, maxEdgesPossible);

            JSONObject graph = new JSONObject();
            graph.put("id", i);
            JSONArray nodes = new JSONArray();
            for (int v = 0; v < vertices; v++) nodes.add(String.valueOf((char) ('A' + v)));
            graph.put("nodes", nodes);

            JSONArray edges = new JSONArray();
            Set<String> used = new HashSet<>();
            while (edges.size() < edgesCount) {
                int u = rand.nextInt(vertices);
                int v = rand.nextInt(vertices);
                if (u == v) continue;
                String key = u + "-" + v;
                if (used.contains(key)) continue;
                used.add(key);

                JSONObject e = new JSONObject();
                e.put("from", String.valueOf((char) ('A' + u)));
                e.put("to", String.valueOf((char) ('A' + v)));
                e.put("weight", 1 + rand.nextInt(100));
                edges.add(e);
            }

            graph.put("edges", edges);
            graphs.add(graph);
        }

        root.put("graphs", graphs);

        try (FileWriter fw = new FileWriter(path)) {
            fw.write(root.toJSONString());
        }

        System.out.println("📄 " + path + " created (" + count + " graphs).");
    }
}
