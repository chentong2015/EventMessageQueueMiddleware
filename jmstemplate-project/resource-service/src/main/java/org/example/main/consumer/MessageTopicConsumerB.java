package org.example.main.consumer;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageTopicConsumerB {

    // TODO. 最多支持创建三个MessageConsumer线程, 同一个Topic ID被多次消费
    // org.springframework.jms.JmsListenerEndpointContainer#2-2 A Received <ReportResponse{id=1, name='report name', response='response'}>
    // org.springframework.jms.JmsListenerEndpointContainer#2-1 A Received <ReportResponse{id=0, name='report name', response='response'}>
    // org.springframework.jms.JmsListenerEndpointContainer#2-3 A Received <ReportResponse{id=2, name='report name', response='response'}>

    @JmsListener(destination = Destinations.REPORTING_TOPIC, containerFactory = "topicContainerFactory", concurrency = "3")
    public void receiveMessage(ReportResponse reportResponse) {
        System.out.println(Thread.currentThread().getName() + " B Received <" + reportResponse + ">");
        try {
            Thread.sleep(10000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}