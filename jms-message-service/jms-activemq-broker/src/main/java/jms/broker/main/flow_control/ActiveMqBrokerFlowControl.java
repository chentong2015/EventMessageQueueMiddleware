package jms.broker.main.flow_control;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;

public class ActiveMqBrokerFlowControl {

    // TODO. BrokerService能够管理的消息大小有限制
    // 激活Producer Flow Control: 自定义设置Broker存储内存大小
    public static void customFlowControl(BrokerService broker) {
        FlowControlProperties flowControlProperties = new FlowControlProperties();
        // --- Per-destination flow control policy ---
        PolicyEntry policy = new PolicyEntry();
        policy.setProducerFlowControl(true);
        policy.setMemoryLimit(flowControlProperties.getMemoryLimit());

        PolicyMap policyMap = new PolicyMap();
        policyMap.setDefaultEntry(policy);
        broker.setDestinationPolicy(policyMap);

        SystemUsage systemUsage = getSystemUsage(flowControlProperties);
        broker.setSystemUsage(systemUsage);
    }

    private static SystemUsage getSystemUsage(FlowControlProperties flowControlProperties) {
        SystemUsage systemUsage = new SystemUsage();
        MemoryUsage memoryUsage = new MemoryUsage();
        memoryUsage.setLimit(flowControlProperties.getMemoryUsageLimit());
        systemUsage.setMemoryUsage(memoryUsage);

        StoreUsage storeUsage = new StoreUsage();
        storeUsage.setLimit(flowControlProperties.getStoreUsageLimit());
        systemUsage.setStoreUsage(storeUsage);

        TempUsage tempUsage = new TempUsage();
        tempUsage.setLimit(flowControlProperties.getTempUsageLimit());
        systemUsage.setTempUsage(tempUsage);
        return systemUsage;
    }
}
