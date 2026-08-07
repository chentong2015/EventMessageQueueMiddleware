package persistence;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// TODO. 消息持久化保证Consumer挂掉重启后能继续
public class ActiveMqPersistence {

    public static void setDataFolder(BrokerService broker) {
        Path path = FileSystems.getDefault().getPath("jms-message-service",  "drive");
        File file = path.toAbsolutePath().toFile();
        broker.setDataDirectoryFile(file);
    }

    // 加载KahaDB并持久化到本地目录
    private static void addPersistentFolder(BrokerService broker, File folder) throws IOException {
        KahaDBPersistenceAdapter kaha = new KahaDBPersistenceAdapter();
        kaha.setDirectory(folder);
        broker.setPersistenceAdapter(kaha);
    }
}
