package org.example.main.producer;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicProducerController {

    private final JmsTemplate jmsTemplate;

    public TopicProducerController(@Qualifier("jmsTemplateTopic") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/jms/topic")
    public String testJmsProducer() {
        for (int index = 0; index < 100; index++) {
            ReportResponse reportResponse = new ReportResponse(index, "report name", "response");
            jmsTemplate.convertAndSend(Destinations.getReportingTopic(), reportResponse);
        }
        return "Test JMS Topic OK";
    }
}
