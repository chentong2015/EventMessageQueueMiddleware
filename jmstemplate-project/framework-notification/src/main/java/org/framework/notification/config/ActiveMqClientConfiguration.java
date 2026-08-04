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

    @Bean
    @ConditionalOnClass(ActiveMQConnectionFactory.class)
    @ConditionalOnMissingBean(ConnectionFactory.class)
    public ConnectionFactory activeMqConnectionFactory(MessageQueueProperties properties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        if (properties.isFlowControlEnabled()) {
            return buildWithFlowControl(connectionFactory, properties);
        }

        // 默认情况下在客户端设置到broker-address地址
        connectionFactory.setBrokerURL(properties.getBrokerAddress());
        return connectionFactory;
    }

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
