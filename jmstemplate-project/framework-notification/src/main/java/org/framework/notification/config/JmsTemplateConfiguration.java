package org.framework.notification.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jms.autoconfigure.AcknowledgeMode;
import org.springframework.boot.jms.autoconfigure.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

// Spring JmsTemplate Bean 客户端自定义注入/属性控制
@Configuration
@EnableJms
@ConditionalOnProperty(prefix = "spring.jms", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JmsTemplateConfiguration {

    @Bean
    @Qualifier("queueContainerFactory")
    @ConditionalOnMissingBean(name = "queueContainerFactory")
    public JmsListenerContainerFactory<?> queueContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(false);
        factory.setSessionAcknowledgeMode(AcknowledgeMode.AUTO.getMode());
        return factory;
    }

    @Bean
    @Qualifier("topicContainerFactory")
    @ConditionalOnMissingBean(name = "topicContainerFactory")
    public JmsListenerContainerFactory<?> topicContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setSessionAcknowledgeMode(AcknowledgeMode.AUTO.getMode());
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    @Qualifier("jmsMessageConverter")
    @ConditionalOnMissingBean(name = "jmsMessageConverter")
    public MessageConverter jacksonJmsMessageConverter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    @Qualifier("jmsTemplateQueue")
    @ConditionalOnMissingBean(name = "jmsTemplateQueue")
    public JmsTemplate jmsTemplateQueue(ConnectionFactory connectionFactory, @Qualifier("jmsMessageConverter") MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setPubSubDomain(false);
        return template;
    }

    @Bean
    @Qualifier("jmsTemplateTopic")
    @ConditionalOnMissingBean(name = "jmsTemplateTopic")
    public JmsTemplate jmsTemplateTopic(ConnectionFactory connectionFactory, @Qualifier("jmsMessageConverter") MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setPubSubDomain(true);
        return template;
    }
}
