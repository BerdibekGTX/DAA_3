package model;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.*;


public class GraphLoader {

    public static List<Graph> loadFromJson(String path) throws Exception {
        List<Graph> graphs = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(new FileReader(path));
        JSONArray arr = (JSONArray) root.get("graphs");

        for (Object o : arr) {
            JSONObject gObj = (JSONObject) o;
            int id = ((Long) gObj.get("id")).intValue();

            // вершины
            JSONArray nodes = (JSONArray) gObj.get("nodes");
            List<String> vertices = new ArrayList<>();
            for (Object n : nodes) vertices.add((String) n);

            Graph g = new Graph(id, vertices);

            // рёбра
            JSONArray edges = (JSONArray) gObj.get("edges");
            for (Object eo : edges) {
                JSONObject e = (JSONObject) eo;
                String from = (String) e.get("from");
                String to = (String) e.get("to");
                double w = ((Number) e.get("weight")).doubleValue();
                g.addEdge(from, to, w);
            }

            graphs.add(g);
        }

        return graphs;
    }


    public static Graph loadSingle(String path) throws Exception {
        return loadFromJson(path).get(0);
    }
}
