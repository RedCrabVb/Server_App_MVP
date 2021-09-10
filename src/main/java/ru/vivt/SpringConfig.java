package ru.vivt;

import ru.vivt.api.GetNews;
import ru.vivt.dataBase.DataBase;
import ru.vivt.dataBase.MySqlDataBase;
import ru.vivt.server.HandlerAPI;
import ru.vivt.server.Server;
import ru.vivt.server.ServerControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.vivt.dataBase.DataBase;
import ru.vivt.server.HandlerAPI;
import ru.vivt.server.ServerControl;

@Configuration
@ComponentScan("com.vivt")
@PropertySource("classpath:config.properties")
public class SpringConfig {
    private DataBase dataBase;
    private Server server;

    @Value("${serverPort}") int serverPort;
    @Value("${logConfPath}") String logConfig;

    @Value("${typeBase}") String typeBase;
    @Value("${userParameterDB}") String userParameterDB;
    @Value("${passwordParameterDB}") String passwordParameterDB;
    @Value("${portParameterDB}") String portParameterDB;
    @Value("${serverNameDB}") String serverNameDB;
    @Value("${databaseNameParameterDB}") String databaseNameParameterDB;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataBase dataBase() throws Exception {
        if (dataBase == null) {
            if (typeBase.equals("mysql")) {
                return new MySqlDataBase();
            } else {
                throw new Exception("Config error, type base = " + typeBase);
            }
        } else {
            return dataBase;
        }
    }

    @Bean
    public Server server() throws Exception {
        if (server == null) {
            return new Server(serverPort, dataBase());
        } else {
            return server;
        }
    }

    @Bean
    public ServerControl serverControl() throws Exception {
        return new ServerControl(logConfig, server());
    }

    @Bean
    public HandlerAPI apiGetNews() throws Exception {
        return new HandlerAPI(new GetNews(), server());
    }
}