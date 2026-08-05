package jms.broker.main;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ActiveMqBroker {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    // broker.setPersistent(false); 不持久化消息数据, 不产生文件信息
    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("my-broker");
        broker.addConnector(BIND_ADDRESS);

        // TODO. 消息持久化保证Consumer挂掉重启后能继续接收剩下的
        Path path = FileSystems.getDefault().getPath("jms-message-service",  "storage");
        File file = path.toAbsolutePath().toFile();
        broker.setDataDirectoryFile(file);

        broker.start();
        broker.waitUntilStopped();
        System.out.println("ActiveMQ Broker started...");
        System.out.println("brokerURL=" + BIND_ADDRESS);
    }

    // 加载KahaDB并持久化到本地目录
    private static void addPersistentFolder(BrokerService broker) throws IOException {
        KahaDBPersistenceAdapter kaha = new KahaDBPersistenceAdapter();
        // kaha.setDirectory(file);
        broker.setPersistenceAdapter(kaha);
    }

    // TODO. BrokerService能够管理的消息大小有限制
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
