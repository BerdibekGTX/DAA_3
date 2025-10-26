# 🛣️ Assignment 3: Optimization of a City Transportation Network(Minimum Spanning Tree) 




# Objective
The purpose of this assignment is to apply Prim’s and Kruskal’s algorithms to
optimize a city’s transportation network by determining the minimum set of roads that
connect all city districts with the lowest possible total construction cost. Students will
also analyze the efficiency of both algorithms and compare their performance.

## 📁 Project Structure
```
DAA_3/
├── pom.xml
├── README.md
├── data/
│ ├── assign3_small_input.json 
│ ├── assign3_medium_input.json 
│ ├── assign3_large_input.json 
│ ├── assign3_extralarge_input.json 
│ ├── output.json
│ └── results.csv
├── src/
│ ├── main/
│ │ ├── algorithms/
│ │ │ ├── Prim.java
│ │ │ ├── Kruskal.java
│ │ │ ├── UnionFind.java
│ │ │ └── Metrics.java
│ │ ├── model/
│ │ │ ├── Graph.java
│ │ │ ├── Edge.java
│ │ │ ├── MSTResult.java
│ │ │ └── GraphLoader.java
│ │ ├── utils/
│ │ │ ├── GraphGenerator.java
│ │ │ ├── CsvExporter.java
│ │ │ └── JsonWriter.java
│ │ ├── app/
│ │ │ ├── MSTRunner.java 
│ │ │ └── ReportBuilder.java
│ │ └── Main.java
│ └── test/
│ ├── MSTCorrectnessTest.java
│ ├── DisconnectedGraphTest.java
│ ├── PerformanceTest.java
│ └── UnionFindTest.java
└── .gitignore
```
## 🎯 Implemented Algorithms

### 🧩 Prim's Algorithm

* **Approach:** Greedy vertex expansion using a priority queue
* **Complexity:** O(E log V)
* **Best for:** Dense graphs
* **Optimizations:** Heap-based priority queue

### 🧮 Kruskal's Algorithm

* **Approach:** Greedy edge selection with cycle detection
* **Complexity:** O(E log E)
* **Best for:** Sparse graphs
* **Optimizations:** Union-Find (Disjoint Set Union)

---
## 📈 Performance Analysis

### 🎯 Key Observations

#### ✅ MST Cost

* Both algorithms always produce the **same MST cost**, confirming correctness.

#### ⚙️ Execution Time

* **Kruskal’s algorithm** is faster for most graph sizes.
* **Prim’s algorithm** shows competitive results on small graphs.
* Kruskal is typically **20–80% faster** across tests.

#### 🔢 Operation Count

* **Prim** uses fewer operations on dense graphs.
* **Kruskal** executes more operations but with higher efficiency.

---

## 🏆 Comparative Analysis

### 📉 Performance by Graph Size

| Graph Size     | Vertices  | Dominant Algorithm | Time Advantage                              |
| -------------- | --------- | ------------------ | ------------------------------------------- |
| 🔵 Small       | 30        | **Prim**           | Faster (0.26–0.33 ms vs 0.26–0.49 ms)       |
| 🟢 Medium      | 300       | **Kruskal**        | Faster (3–4 ms vs 3–5 ms)                   |
| 🟡 Large       | 1000      | **Kruskal**        | Much faster (12–13 ms vs 17–21 ms)          |
| 🔴 Extra Large | 1300–2000 | **Kruskal**        | Significantly faster (15–38 ms vs 20–67 ms) |

---

## 🛠 Technical Details

### 🧱 Graph Structure

```java
public class Graph {
    private List<String> vertices;
    private List<Edge> edges;
    private Map<String, List<Edge>> adjacencyList;
}
```
## 🧭 4. Conclusion and Recommendations

### 🏁 Key Findings

1. **Kruskal’s empirical superiority:** Outperforms Prim for graphs > 100 vertices.
2. **Complexity alignment:** Both follow expected O(E log V) / O(E log E) growth.
3. **Constant factor impact:** Implementation details dominate real performance.
4. **Scalability:** Kruskal scales better.
5. **Efficiency:** Despite more operations, Kruskal executes them faster.

---

### 🧩 Algorithm Selection Guidelines

| Scenario                | Recommended Algorithm | Reason                    |
| ----------------------- | --------------------- | ------------------------- |
| Graphs < 50 vertices    | **Prim**              | Slightly faster           |
| Graphs 50–2000 vertices | **Kruskal**           | 20–40% faster             |
| Time-critical apps      | **Kruskal**           | Predictable speed         |
| Educational use         | Both                  | Pedagogical contrast      |
| Production systems      | **Kruskal**           | Reliability & performance |

**Decision Framework:**

```text
if (vertices < 50) → Prim's Algorithm  
else → Kruskal's Algorithm
```

---

### ⚡ Final Recommendations

1. Adopt **Kruskal’s algorithm** as the **default** MST approach in production.
2. Maintain both for educational and verification purposes.
3. Complement theoretical predictions with **empirical validation**.
4. Note that **constant factors** and hardware optimizations often outweigh asymptotic differences.

---
