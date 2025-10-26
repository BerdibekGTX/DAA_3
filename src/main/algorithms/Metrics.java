package algorithms;


public class Metrics {
    private long start;
    public int operations;
    public long timeMs;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        timeMs = (System.nanoTime() - start) / 1_000_000;
    }

    public void inc() {
        operations++;
    }
}
