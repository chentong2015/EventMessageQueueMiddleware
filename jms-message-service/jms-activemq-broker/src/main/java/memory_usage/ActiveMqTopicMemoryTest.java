package memory_usage;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class ActiveMqTopicMemoryTest {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BIND_ADDRESS);
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.setClientID("client-1");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = new ActiveMQTopic("topic_name_new");

        MessageConsumer consumer = session.createDurableSubscriber(topic, "sub1");
        consumer.close();

        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        while (true) {
            byte[] body = new byte[1024 * 1024]; // 1MB
            BytesMessage msg = session.createBytesMessage();
            msg.writeBytes(body);
            producer.send(msg);
        }
    }
}