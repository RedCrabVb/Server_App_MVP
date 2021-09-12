package ru.vivt.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

public class ImageHandler implements HttpHandler {
    private String imgPath;

    public ImageHandler(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        if (http.getRequestMethod().equals("GET")) {
            System.out.println("img transfered..." + imgPath);
            ServerControl.LOGGER.log(Level.INFO, "img transfered ", imgPath);
            try {
                File file = new File(imgPath);
                http.sendResponseHeaders(200, file.length());

                OutputStream outputStream = http.getResponseBody();
                Files.copy(file.toPath(), outputStream);
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
