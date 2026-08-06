package jms.broker.main;

import jms.broker.main.flow_control.ActiveMqBrokerFlowControl;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ActiveMqBroker {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("my-broker");
        broker.addConnector(BIND_ADDRESS);

        // TODO. 消息持久化保证Consumer挂掉重启后能继续接收剩下的
        Path path = FileSystems.getDefault().getPath("jms-message-service",  "drive");
        File file = path.toAbsolutePath().toFile();
        broker.setDataDirectoryFile(file);

        broker.start();
        broker.waitUntilStopped();
        System.out.println("ActiveMQ Broker started...");
        System.out.println("brokerURL=" + BIND_ADDRESS);
    }

    // 加载KahaDB并持久化到本地目录
    // broker.setPersistent(false); 不持久化消息数据, 不产生文件信息
    private static void addPersistentFolder(BrokerService broker) throws IOException {
        KahaDBPersistenceAdapter kaha = new KahaDBPersistenceAdapter();
        // kaha.setDirectory(file);
        broker.setPersistenceAdapter(kaha);
    }
}
