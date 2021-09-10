package ru.vivt.server;

import ru.vivt.dataBase.DataBase;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private HttpServer serverHttp;
    private DataBase dataBase;

    @Autowired
    @Qualifier("apiGetNews")
    private HandlerAPI apiGetNews;


    public Server(int port,
                  DataBase dataBase) throws Exception {
        this.dataBase = dataBase;
        this.serverHttp = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void run() {
        serverHttp.createContext("/api/news", apiGetNews);
        serverHttp.setExecutor(null); // creates a default executor
        serverHttp.start();
    }

    public Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}