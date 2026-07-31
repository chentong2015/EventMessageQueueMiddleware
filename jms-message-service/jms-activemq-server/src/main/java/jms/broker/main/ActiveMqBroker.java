package jms.broker.main;

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
        broker.start();

        System.out.println("ActiveMQ Broker started...");
        System.out.println("brokerURL=" + BIND_ADDRESS);

        // broker.setPersistent(false); 不持久化消息数据, 不产生文件信息

        Path path = FileSystems.getDefault().getPath("jms-message-service",  "storage");
        File file = new File(path.toAbsolutePath().toString());
        broker.setDataDirectoryFile(file);
        broker.setTmpDataDirectory(file);

        broker.waitUntilStopped();
    }

    // 加载KahaDB并持久化到本地目录
    private static void addPersistentFolder(BrokerService broker) throws IOException {
        KahaDBPersistenceAdapter kaha = new KahaDBPersistenceAdapter();
        // kaha.setDirectory(file);
        broker.setPersistenceAdapter(kaha);
    }
}
