package jms.broker.main;

import org.apache.activemq.broker.BrokerService;

public class ActiveMqBroker {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("my-broker");
        broker.addConnector(BIND_ADDRESS);

        broker.setPersistent(false);
        // ActiveMqPersistence.setDataFolder(broker);
        // ActiveMqBrokerFlowControl.customFlowControl(broker);

        broker.start();
        broker.waitUntilStopped();
        System.out.println("ActiveMQ Broker started...");
        System.out.println("brokerURL=" + BIND_ADDRESS);
    }
}
