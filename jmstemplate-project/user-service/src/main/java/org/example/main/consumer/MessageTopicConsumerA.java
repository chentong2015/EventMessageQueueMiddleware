package org.example.main.consumer;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageTopicConsumerA {

    @JmsListener(destination = Destinations.REPORTING_TOPIC, containerFactory = "topicContainerFactory", concurrency = "5")
    public void receiveMessage(ReportResponse reportResponse) {
        System.out.println("A Received <" + reportResponse + ">");
    }
}