package org.messaging.broker;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

// 注入后端ActiveMQ Broker服务, 为MQ Client端提供连接
@Configuration
@ConditionalOnProperty(prefix = "spring.jms", name = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class ActiveMqBrokerConfiguration {

    @Bean
    public ConnectionFactory activeMqConnectionFactory() {
        ActiveMQSslConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost");
        return connectionFactory;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService brokerService(MessageQueueProperties properties) throws Exception {
        String url = properties.getBindAddress();
        if (properties.isFlowControlEnabled()) {
            String separator = "?";
            if (properties.getBindAddress().contains("?")) {
                separator = "&";
            }
            url = properties.getBindAddress() + separator + "wireFormat.maxInactivityDuration=" + properties.getMaxInactivityDuration();
        }

        BrokerService broker = new BrokerService();
        broker.addConnector(url);
        broker.setDataDirectoryFile(new File(properties.getDbDirectory()));
        broker.setTmpDataDirectory(new File(properties.getTmpDirectory()));

        if (properties.isFlowControlEnabled()) {
            setBrokerWithFlowControl(broker, properties);
        }
        return broker;
    }

    // Broker Service 服务端Flow Control控制
    private void setBrokerWithFlowControl(BrokerService broker, MessageQueueProperties properties) {
        // --- Per-destination flow control policy ---
        PolicyEntry policy = new PolicyEntry();
        policy.setProducerFlowControl(true);
        policy.setMemoryLimit(properties.getMemoryLimit());

        PolicyMap policyMap = new PolicyMap();
        policyMap.setDefaultEntry(policy);
        broker.setDestinationPolicy(policyMap);

        // --- Broker-wide memory/storage limits (thresholds that trigger flow control) ---
        SystemUsage systemUsage = new SystemUsage();

        MemoryUsage memoryUsage = new MemoryUsage();
        memoryUsage.setLimit(properties.getMemoryUsageLimit());
        systemUsage.setMemoryUsage(memoryUsage);

        StoreUsage storeUsage = new StoreUsage();
        storeUsage.setLimit(properties.getStoreUsageLimit());
        systemUsage.setStoreUsage(storeUsage);

        TempUsage tempUsage = new TempUsage();
        tempUsage.setLimit(properties.getTempUsageLimit());
        systemUsage.setTempUsage(tempUsage);

        broker.setSystemUsage(systemUsage);
    }
}
