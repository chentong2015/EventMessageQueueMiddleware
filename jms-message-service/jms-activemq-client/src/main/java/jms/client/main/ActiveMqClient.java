package jms.client.main;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;

import jakarta.jms.*;

// TODO. ActiveMQConnectionFactory是ConnectionFactory的实现
public class ActiveMqClient {

    // 需要消息转发的Server提供消息发送的服务
    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BIND_ADDRESS);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Destination: 提供消息发送的目的地(Queue或者Topic)
        Destination destinationQueue = new ActiveMQQueue("queue_name");
        Destination destinationTopic = new ActiveMQTopic("topic_name");

        // 创建消息的发送者和消费者，关联到同一个Destination
        Session session = connection.createSession();
        MessageProducer producer = session.createProducer(destinationQueue);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("Sent message: first text message");
        producer.send(textMessage);

        MessageConsumer consumer = session.createConsumer(destinationQueue);
        TextMessage messageReceived = (TextMessage) consumer.receive(5000);
        System.out.println("Receive message: " + messageReceived.getText());
        messageReceived.acknowledge();

        connection.stop();
        connection.close();
    }
}