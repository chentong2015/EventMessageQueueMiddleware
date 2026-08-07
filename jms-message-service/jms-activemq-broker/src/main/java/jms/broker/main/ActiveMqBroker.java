package jms.broker.main;

import org.apache.activemq.broker.BrokerService;

import java.io.File;
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

        // ActiveMqBrokerFlowControl.customFlowControl(broker);

        broker.start();
        broker.waitUntilStopped();
        System.out.println("ActiveMQ Broker started...");
        System.out.println("brokerURL=" + BIND_ADDRESS);
    }
}
