public class TestStats {
    private final int total;
    private int passed;
    private int failed;

    public TestStats(int total) {
        this.total = total;
        this.passed = 0;
        this.failed = 0;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public void incrementPassed() {
        passed++;
    }

    public void incrementFailed() {
        failed++;
    }
}
