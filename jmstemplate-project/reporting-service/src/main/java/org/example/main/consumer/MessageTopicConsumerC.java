package org.example.main.consumer;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

// TODO. 消息的消费者可位于不同模块(项目), 通过同一个Broker进行分发
@Component
public class MessageTopicConsumerC {

    @JmsListener(destination = Destinations.REPORTING_TOPIC, containerFactory = "topicContainerFactory", concurrency = "3")
    public void receiveMessage(ReportResponse reportResponse) {
        System.out.println(Thread.currentThread().getName() + " C Received <" + reportResponse + ">");
        try {
            Thread.sleep(10000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}