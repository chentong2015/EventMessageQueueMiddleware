package org.framework.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message-queue")
public class MessageQueueProperties {

    private String brokerAddress;

    private boolean flowControlEnabled;
    private long maxInactivityDuration;
    private int  producerWindowSize;

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public long getMaxInactivityDuration() {
        return maxInactivityDuration;
    }

    public int getProducerWindowSize() {
        return producerWindowSize;
    }

    public boolean isFlowControlEnabled() {
        return flowControlEnabled;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
    }

    public void setMaxInactivityDuration(long maxInactivityDuration) {
        this.maxInactivityDuration = maxInactivityDuration;
    }

    public void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
    }

    public void setFlowControlEnabled(boolean flowControlEnabled) {
        this.flowControlEnabled = flowControlEnabled;
    }
}
