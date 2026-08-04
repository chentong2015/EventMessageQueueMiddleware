package org.messaging.broker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message-queue")
public class MessageQueueProperties {

    private String bindAddress;
    private String dbDirectory;
    private String tmpDirectory;

    private boolean flowControlEnabled;
    private long maxInactivityDuration;
    private int  producerWindowSize;

    // --- Flow control ---
    private long memoryLimit;
    private long memoryUsageLimit;
    private long storeUsageLimit;
    private long tempUsageLimit;

    public String getBindAddress() {
        return bindAddress;
    }

    public String getDbDirectory() {
        return dbDirectory;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }

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

    public boolean isFlowControlEnabled() {
        return flowControlEnabled;
    }

    public int getProducerWindowSize() {
        return producerWindowSize;
    }

    public long getMaxInactivityDuration() {
        return maxInactivityDuration;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public void setDbDirectory(String dbDirectory) {
        this.dbDirectory = dbDirectory;
    }

    public void setTmpDirectory(String tmpDirectory) {
        this.tmpDirectory = tmpDirectory;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public void setMemoryUsageLimit(long memoryUsageLimit) {
        this.memoryUsageLimit = memoryUsageLimit;
    }

    public void setStoreUsageLimit(long storeUsageLimit) {
        this.storeUsageLimit = storeUsageLimit;
    }

    public void setTempUsageLimit(long tempUsageLimit) {
        this.tempUsageLimit = tempUsageLimit;
    }

    public void setFlowControlEnabled(boolean flowControlEnabled) {
        this.flowControlEnabled = flowControlEnabled;
    }

    public void setMaxInactivityDuration(long maxInactivityDuration) {
        this.maxInactivityDuration = maxInactivityDuration;
    }

    public void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
    }
}
