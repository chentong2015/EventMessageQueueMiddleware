package org.example.main.consumer;

import org.example.main.model.Destinations;
import org.example.main.model.ItemAddress;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueConsumer {

    // TODO. 最多支持创建三个MessageConsumer并发消费信息
    // 多线程并发处理, 且保证每个消息ID只被消费一次, 挂掉恢复后继续剩余的
    // org.springframework.jms.JmsListenerEndpointContainer#0-2 Queue Received <ItemAddress{id=2, name='address name'}>
    // org.springframework.jms.JmsListenerEndpointContainer#0-1 Queue Received <ItemAddress{id=0, name='address name'}>
    // org.springframework.jms.JmsListenerEndpointContainer#0-3 Queue Received <ItemAddress{id=5, name='address name'}>

    @JmsListener(destination = Destinations.ADDRESS_QUEUE, containerFactory = "queueContainerFactory", concurrency = "3")
    public void receiveMessage(ItemAddress itemAddress) {
        System.out.println(Thread.currentThread().getName() + " Queue Received <" + itemAddress + ">");
        try {
            Thread.sleep(3000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}