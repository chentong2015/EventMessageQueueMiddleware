package org.framework.notification;

import org.framework.notification.config.ActiveMqClientConfiguration;
import org.framework.notification.config.JmsTemplateConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ActiveMqClientConfiguration.class, JmsTemplateConfiguration.class})
public @interface EnableMessageQueue {
}