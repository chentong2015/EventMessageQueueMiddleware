package jms.client.main;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;

import jakarta.jms.*;

public class ActiveMqTopicDemo {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws JMSException, InterruptedException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BIND_ADDRESS);

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination topic = new ActiveMQTopic("topic_name");

        createTopicConsumer(session, topic, 1);
        createTopicConsumer(session, topic, 2);
        createTopicConsumer(session, topic, 3);

        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("Topic message: first text message !");
        producer.send(textMessage);
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            textMessage = session.createTextMessage("Topic message: " + i);
            producer.send(textMessage);
        }

        session.close();
        connection.close();
    }

    // 创建基于某个Topic的消费者
    private static void createTopicConsumer(Session session, Destination topic, int index) throws JMSException {
        MessageConsumer consumer = session.createConsumer(topic);
        // TextMessage messageReceived = (TextMessage) consumer.receive(5000);

        consumer.setMessageListener(message -> {
            TextMessage text = (TextMessage) message;
            try {
                System.out.println("Consumer " + index + " + Received: " + text.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}