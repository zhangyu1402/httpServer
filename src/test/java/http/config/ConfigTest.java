package http.config;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigTest {

    @Test
    public void configTest() {
        Properties properties = new Properties();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/zhangyu/nettyLearn/httpServer/src/main/resources/application.properties"));
            properties.load(bufferedReader);
            String port = properties.getProperty("port");
            System.out.println(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
