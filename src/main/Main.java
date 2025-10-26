import app.MSTRunner;
import app.ReportBuilder;
import utils.GraphGenerator;


public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "--generate" -> {
                System.out.println("🧩 Generating random graphs...");
                GraphGenerator.generateAll();
            }
            case "--analyze" -> {
                System.out.println("🚀 Running MST analysis (Prim & Kruskal)...");
                MSTRunner.main(null);
            }
            case "--report" -> {
                System.out.println("📊 Building summary report...");
                ReportBuilder.main(null);
            }
            default -> printHelp();
        }

        System.out.println("\n✅ Done.");
    }

    private static void printHelp() {
        System.out.println("""
                Usage:
                  java -cp target/classes Main [option]
                Options:
                  --generate   Generate random graph datasets (data/assign3_input.json)
                  --analyze    Run MST analysis (Prim + Kruskal)
                  --report     Build CSV report (data/results.csv)
                """);
    }
}
