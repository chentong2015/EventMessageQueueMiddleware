package org.example.main;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @JmsListener(destination = Destinations.REPORTING_TOPIC, containerFactory = "topicContainerFactory", concurrency = "5")
    public void receiveMessage(ReportResponse reportResponse) {
        System.out.println("Received <" + reportResponse + ">");
    }
}