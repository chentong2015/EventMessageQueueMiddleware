package ssl;

public class MessageQueueProperties {

    private String bindAddress;
    private String dbDirectory;
    private String tmpDirectory;
    private boolean useJMX;
    private boolean networkEnabled;
    private String networkConnectorURI;

    private String brokerAddress;
    private String keystoreFile;
    private String keystorePass;
    private String keystoreType;
    private String truststoreFile;
    private String truststorePass;
    private String truststoreType;
    private long maxInactivityDuration;
    private int producerWindowSize;

    public String getBindAddress() {
        return bindAddress;
    }

    public String getDbDirectory() {
        return dbDirectory;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }

    public boolean isUseJMX() {
        return useJMX;
    }

    public boolean isNetworkEnabled() {
        return networkEnabled;
    }

    public String getNetworkConnectorURI() {
        return networkConnectorURI;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public String getKeystorePass() {
        return keystorePass;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public String getTruststoreFile() {
        return truststoreFile;
    }

    public String getTruststorePass() {
        return truststorePass;
    }

    public String getTruststoreType() {
        return truststoreType;
    }

    public long getMaxInactivityDuration() {
        return maxInactivityDuration;
    }

    public int getProducerWindowSize() {
        return producerWindowSize;
    }
}
