package ru.vivt.server;

import com.sun.net.httpserver.HttpHandler;
import ru.vivt.dataBase.DataBase;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends Thread {
    private HttpServer serverHttp;
    private DataBase dataBase;

/*    @Autowired
    @Qualifier("apiGetNews")
    private HandlerAPI apiGetNews;
    @Autowired
    @Qualifier("apiRegistration")
    private HandlerAPI apiRegistration;
    @Autowired
    @Qualifier("apiGetQrCode")
    private HandlerAPI apiGetQrCode;
    @Autowired
    @Qualifier("apiSetPersonDate")
    private HandlerAPI apiSetPersonDate;
    @Autowired
    @Qualifier("apiGetStatusToken")
    private HandlerAPI apiGetStatusToken;
    @Autowired
    @Qualifier("resetPassword")
    private HandlerAPI resetPassword;
    @Autowired
    @Qualifier("imgPath")
    private String imgPath;*/


    public Server(int port,
                  DataBase dataBase) throws Exception {
//        this.dataBase = dataBase;
//        this.serverHttp = HttpServer.create(new InetSocketAddress(port), 0);
    }

    @Override
    public void run() {

/*        serverHttp.createContext("/api/news", apiGetNews);
        serverHttp.createContext("/api/registration", apiRegistration);
        serverHttp.createContext("/api/qrCode", apiGetQrCode);
        serverHttp.createContext("/api/setPersonDate", apiSetPersonDate);
        serverHttp.createContext("/api/getStatusToken", apiGetStatusToken);
        serverHttp.createContext("/api/resetPassword", resetPassword);


        try {
            Files.walk(Paths.get(imgPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    String imgFile = filePath.getFileName().toString();
                    serverHttp.createContext("/src/img/" + imgFile, new ImageHandler(filePath.toAbsolutePath().toString()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverHttp.setExecutor(null); // creates a default executor*/
//        serverHttp.start();
    }

    public static Map<String, String> queryToMap(String query) {
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