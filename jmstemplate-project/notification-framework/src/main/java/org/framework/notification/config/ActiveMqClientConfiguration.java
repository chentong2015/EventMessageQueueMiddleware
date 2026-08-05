package org.framework.notification.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.framework.notification.MessageQueueProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

// JMS ActiveMQ Bean 客户端相关配置
@Configuration
@PropertySources(value = @PropertySource("classpath:/mq.properties"))
@EnableConfigurationProperties(MessageQueueProperties.class)
public class ActiveMqClientConfiguration {

    // 该注入仅应用于客户端设置broker-address地址
    @Bean
    @ConditionalOnClass(ActiveMQConnectionFactory.class)
    @ConditionalOnMissingBean(ConnectionFactory.class)
    public ConnectionFactory activeMqConnectionFactory(MessageQueueProperties properties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        if (properties.isFlowControlEnabled()) {
            return buildWithFlowControl(connectionFactory, properties);
        }
        connectionFactory.setBrokerURL(properties.getBrokerAddress());
        return connectionFactory;
    }

    // ActiveMQ Client 客户端的Flow Control
    private ConnectionFactory buildWithFlowControl(ActiveMQConnectionFactory connectionFactory, MessageQueueProperties properties) {
        String separator="?";
        if (properties.getBrokerAddress().contains("?")) {
            separator = "&";
        }
        String url = properties.getBrokerAddress() + separator + "wireFormat.maxInactivityDuration=" + properties.getMaxInactivityDuration();
        connectionFactory.setBrokerURL(url);
        connectionFactory.setProducerWindowSize(properties.getProducerWindowSize());
        connectionFactory.setUseAsyncSend(true);
        return connectionFactory;
    }
}
