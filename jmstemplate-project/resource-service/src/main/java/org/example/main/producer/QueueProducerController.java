package org.example.main.producer;

import org.example.main.model.Destinations;
import org.example.main.model.ItemAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueProducerController {

    private final JmsTemplate jmsTemplate;

    public QueueProducerController(@Qualifier("jmsTemplateQueue") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/jms/queue")
    public String testJmsProducer() {
        for (int index = 0; index < 100; index++) {
            ItemAddress itemAddress = new ItemAddress(index, "address name");
            jmsTemplate.convertAndSend(Destinations.getAddressQueue(), itemAddress);
        }
        return "Test JMS Queue OK";
    }
}
