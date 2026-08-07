package jms.broker.main;

public class FlowControlProperties {

    private long memoryLimit = 32 * 1024 * 1024L;      // 32 MB per destination
    private long memoryUsageLimit = 64 * 1024 * 1024L; // 64 MB broker heap
    private long storeUsageLimit = 128 * 1024 * 1024L; // 128 MB persistent store
    private long tempUsageLimit = 100 * 1024 * 1024L;  // 100 MB temp store

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public long getMemoryUsageLimit() {
        return memoryUsageLimit;
    }

    public long getStoreUsageLimit() {
        return storeUsageLimit;
    }

    public long getTempUsageLimit() {
        return tempUsageLimit;
    }
}
