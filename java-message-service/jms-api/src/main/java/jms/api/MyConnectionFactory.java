package jms.api;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;

// 自定义对JMS的实现和封装
public class MyConnectionFactory implements ConnectionFactory {

    @Override
    public Connection createConnection() throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public JMSContext createContext() {
        return null;
    }

    @Override
    public JMSContext createContext(String s, String s1) {
        return null;
    }

    @Override
    public JMSContext createContext(String s, String s1, int i) {
        return null;
    }

    @Override
    public JMSContext createContext(int i) {
        return null;
    }
}
