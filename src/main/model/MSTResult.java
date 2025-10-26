package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

public class MSTResult {
    public String algorithm;
    public boolean mstExists;
    public List<Edge> mstEdges;
    public double totalCost;
    public double executionTimeMs;
    public int operationsCount;

    public MSTResult(String algorithm, boolean mstExists, List<Edge> mstEdges,
                     double totalCost, double executionTimeMs, int operationsCount) {
        this.algorithm = algorithm;
        this.mstExists = mstExists;
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.executionTimeMs = executionTimeMs;
        this.operationsCount = operationsCount;
    }

    // 🔹 Добавляем метод для вывода рёбер MST в JSON
    @SuppressWarnings("unchecked")
    public JSONArray getEdgesAsJson() {
        JSONArray arr = new JSONArray();
        for (Edge e : mstEdges) {
            JSONObject edgeObj = new JSONObject();
            edgeObj.put("from", e.from);
            edgeObj.put("to", e.to);
            edgeObj.put("weight", e.weight);
            arr.add(edgeObj);
        }
        return arr;
    }
}
