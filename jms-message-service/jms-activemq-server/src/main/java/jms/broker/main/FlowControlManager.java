package jms.broker.main;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;

// TODO. BrokerService能够管理的消息大小有限制
public class FlowControlManager {

    // 激活Producer Flow Control: 自定义设置Broker存储内存大小
    private static void customFlowControl(BrokerService broker, boolean isEnabled) {
        FlowControlProperties flowControlProperties = new FlowControlProperties();
        if (isEnabled) {
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
