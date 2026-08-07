package persistence;

import jakarta.jms.*;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;

import java.io.File;
import java.io.IOException;

// broker.setPersistent(false); 不持久化消息数据, 不产生文件信息
public class ActiveMqPersistence {

    // 加载KahaDB并持久化到本地目录
    public static void addPersistentFolder(BrokerService broker, File folder) throws IOException {
        KahaDBPersistenceAdapter kaha = new KahaDBPersistenceAdapter();
        kaha.setDirectory(folder);
        broker.setPersistenceAdapter(kaha);
    }

    // 发送非持久化的消息: 大量消息分页到临时文件(my-broker/tmp_storage)，而非堆在MemoryUsage里
    public static void setClientPersistence(Session session, Destination queue) throws JMSException {
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
}
