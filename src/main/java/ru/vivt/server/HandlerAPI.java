package ru.vivt.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.vivt.api.Command;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;

public class HandlerAPI implements HttpHandler {
    private Command command;
    private Server server;

    public HandlerAPI(Command command, Server server) {
        this.server = server;
        this.command = command;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ServerControl.LOGGER.log(Level.INFO, "get url: " + exchange.getRequestURI().getQuery());

        Map<String, String> params = server.queryToMap(exchange.getRequestURI().getQuery());

        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Headers", "x-prototype-version,x-requested-with");
        headers.add("Access-Control-Allow-Methods", "GET,POST");
        headers.add("Access-Control-Allow-Origin", "*");

        try {
            String response = command.execute(params).toString();
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();

            ServerControl.LOGGER.log(Level.INFO, "send json: " + response);
        } catch (Exception e) {
            exchange.sendResponseHeaders(401, e.toString().getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(e.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            ServerControl.LOGGER.log(Level.WARNING, "send error: " + e.toString());
        }
    }
}