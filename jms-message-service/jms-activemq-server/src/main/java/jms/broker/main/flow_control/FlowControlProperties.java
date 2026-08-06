package jms.broker.main.flow_control;

public class FlowControlProperties {

    private long memoryLimit = 32 * 1024 * 1024L;      // 32 MB per destination
    private long memoryUsageLimit = 256 * 1024 * 1024L; // 256 MB broker heap
    private long storeUsageLimit = 1024 * 1024 * 1024L; // 1 GB persistent store
    private long tempUsageLimit = 100 * 1024 * 1024L;   // 100 MB temp store

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
