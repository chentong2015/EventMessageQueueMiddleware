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
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = new ActiveMQTopic("topic_name_new");

        MessageConsumer consumer = session.createDurableSubscriber(topic, "sub1");
        consumer.close();

        // Producer 不停发消息 -> Broker 会缓存所有消 -> memoryUsage会上涨
    }
}