package persistence;

import jakarta.jms.*;
import org.apache.activemq.broker.BrokerService;

// TODO. 不持久化消息数据将使用临时目录存储(my-broker/tmp_storage/)
// 默认临时目录大小 Temporary Store limit is 51200 mb (current store usage is 0 mb)
public class ActiveMqNonPersistence {

    public static void setNonPersistence(BrokerService broker) {
        broker.setPersistent(false);
    }

    // 发送非持久化的消息: 大量消息分页到临时文件，而非堆在MemoryUsage里
    // ActiveMQ会使用temporary file store来降低内存占用，因此queue的memoryLimit可能永远达不到
    private static void setClientPersistence(Session session, Destination queue) throws JMSException {
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
}