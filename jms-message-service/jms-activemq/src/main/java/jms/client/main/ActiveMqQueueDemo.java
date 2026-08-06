package jms.client.main;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMqQueueDemo {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BIND_ADDRESS);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue_name_new");

        createQueueConsumer(session, queue);

        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("Queue message: first text message"));
        for (int i = 0; i < 1000; i++) {
            TextMessage textMessage = session.createTextMessage("Queue message: " + i);
            producer.send(textMessage);
        }

        session.close();
        connection.close();
    }

    // 创建基于特定Queue的消费者, 设置监听器
    private static void createQueueConsumer(Session session, Destination queue) throws JMSException {
        MessageConsumer consumer = session.createConsumer(queue);
        // TextMessage msg = (TextMessage) consumer.receive(5000);

        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("Received: " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}