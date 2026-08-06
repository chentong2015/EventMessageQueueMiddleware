package jms.broker.main.ssl;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslBrokerService;
import org.apache.activemq.broker.SslContext;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;

public class ActiveMqBrokerSSL {

    // 使用带SSL安全的ConnectionFactory
    public ConnectionFactory activeMqConnectionFactory() throws Exception {
        ActiveMQSslConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory();
        connectionFactory.setBrokerURL("vm://localhost");
        return connectionFactory;
    }

    // 使用带SSL安全的BrokerService对象
    public BrokerService brokerService(MessageQueueProperties properties) throws Exception {
        String url = properties.getBindAddress();

        SslBrokerService broker = new SslBrokerService();
        broker.setUseJmx(properties.isUseJMX());
        broker.addSslConnector(url, readKeystore(properties), readTruststore(properties), new SecureRandom());

        SslContext sslContext = new SslContext();
        sslContext.setTrustManagers(Arrays.asList(readTruststore(properties)));
        sslContext.setKeyManagers(Arrays.asList(readKeystore(properties)));
        broker.setSslContext(sslContext);

        return broker;
    }

    private KeyManager[] readKeystore(MessageQueueProperties properties) throws GeneralSecurityException, IOException {
        KeyManagerFactory theKeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore theKeyStore = KeyStore.getInstance(properties.getKeystoreType());
        InputStream theKeystoreResource = new FileInputStream(properties.getKeystoreFile());
        theKeyStore.load(theKeystoreResource, properties.getKeystorePass().toCharArray());
        theKeyManagerFactory.init(theKeyStore, properties.getKeystorePass().toCharArray());
        return theKeyManagerFactory.getKeyManagers();
    }

    private TrustManager[] readTruststore(MessageQueueProperties properties) throws GeneralSecurityException, IOException {
        KeyStore theTruststore = KeyStore.getInstance(properties.getTruststoreType());
        InputStream theTruststoreResource = new FileInputStream(properties.getTruststoreFile());
        theTruststore.load(theTruststoreResource, properties.getTruststorePass().toCharArray());
        TrustManagerFactory theTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        theTrustManagerFactory.init(theTruststore);
        return theTrustManagerFactory.getTrustManagers();
    }
}
