package org.example.main.model;

import jakarta.jms.Destination;
import org.apache.activemq.command.ActiveMQTopic;

public class Destinations {

    public static final String REPORTING_TOPIC = "REPORTING_TOPIC_NAME";
    public static final String ADDRESS_QUEUE = "ADDRESS_QUEUE_NAME";

    public static Destination getReportingTopic() {
        return new ActiveMQTopic(REPORTING_TOPIC);
    }

    public static Destination getAddressQueue() {
        return new ActiveMQTopic(ADDRESS_QUEUE);
    }
}
