package org.example.main.notification;

import org.example.main.model.Destinations;
import org.example.main.model.ReportResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "spring.jms", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WebNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private static final String NOTIFICATION_TOPIC = "/topic/notification_workflow";

    public WebNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // TODO. 将接收到的消息发送到WebSocket并将其显示在Web页面
    @JmsListener(destination = Destinations.REPORTING_TOPIC, containerFactory = "topicContainerFactory", concurrency = "1")
    public void receiveNotification(@Payload ReportResponse reportResponse) {
        System.out.println("Web notification service received: " + reportResponse);
        messagingTemplate.convertAndSend(NOTIFICATION_TOPIC, reportResponse);
    }
}
