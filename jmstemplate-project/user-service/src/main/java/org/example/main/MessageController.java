package org.example.main;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final JmsTemplate jmsTemplate;

    public MessageController(@Qualifier("jmsTemplateTopic") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/jms/test")
    public String testJmsProducer() {
        for (int index = 0; index < 100; index++) {
            sendExportResponse(new ReportResponse(index, "name", "response"));
        }
        return "Test JMS OK";
    }

    private void sendExportResponse(ReportResponse reportResponse) {
        try{
            jmsTemplate.convertAndSend(Destinations.getReportingTopic(), reportResponse);
        } catch (UncategorizedJmsException e) {
            System.out.println("Send JMS Failed");
        }
    }
}
