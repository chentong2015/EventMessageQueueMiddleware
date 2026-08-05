package org.example.main.producer;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProducerController {

    private final JmsTemplate jmsTemplate;

    public MessageProducerController(@Qualifier("jmsTemplateTopic") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/jms/test")
    public String testJmsProducer() throws InterruptedException {
        sendMessageTopic();
        sendMessageQueue();
        return "Test JMS OK";
    }

    @Async
    public void sendMessageTopic() throws InterruptedException {
        for (int index = 0; index < 20; index++) {
            Thread.sleep(2000);
            ReportResponse reportResponse = new ReportResponse(index, "name", "response");
            jmsTemplate.convertAndSend(Destinations.getReportingTopic(), reportResponse);
        }
    }

    @Async
    public void sendMessageQueue() throws InterruptedException {
        for (int index = 0; index < 20; index++) {
            Thread.sleep(2000);
            ReportResponse reportResponse = new ReportResponse(index, "name", "response");
            jmsTemplate.convertAndSend(Destinations.getReportingTopic(), reportResponse);
        }
    }
}
