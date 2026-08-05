package org.example.main.consumer;

import org.example.main.model.Destinations;
import org.example.main.model.ItemAddress;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueConsumer {

    @JmsListener(destination = Destinations.ADDRESS_QUEUE, containerFactory = "queueContainerFactory", concurrency = "5")
    public void receiveMessage(ItemAddress itemAddress) {
        System.out.println("Received <" + itemAddress + ">");
    }
}
