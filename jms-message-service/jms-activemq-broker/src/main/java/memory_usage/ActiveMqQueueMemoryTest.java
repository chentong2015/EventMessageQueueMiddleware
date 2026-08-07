package memory_usage;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMqQueueMemoryTest {

    private static final String BIND_ADDRESS = "tcp://127.0.0.1:7777";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BIND_ADDRESS);
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue_name_test");

        MessageProducer producer = session.createProducer(queue);
        while (true) {
            byte[] body = new byte[1024 * 1024]; // 1MB
            BytesMessage msg = session.createBytesMessage();
            msg.writeBytes(body);
            producer.send(msg);
        }
    }
}