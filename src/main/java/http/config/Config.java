package http.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private Properties properties;

    private final String DEFAULT_PORT = "8080";

    int port;

    private void loadConfig() {
        port = Integer.parseInt(properties.getProperty("port", DEFAULT_PORT));
    }

    public Config(String fileName) {
        properties = new Properties();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            properties.load(bufferedReader);
        } catch (IOException e) {
            log.error("fail to load config file");
            log.error(e.getMessage());
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
