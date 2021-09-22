package ru.vivt.server;

import ru.vivt.Main;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
 * Responsible for entering data for the administrator.
 * Responsible for logging.
 * The single-responsibility principle may be violated
 * */
public class ServerControl extends Thread {
    private final String cls = "/cls",
            info = "/info", sendMessage = "/msg", logShow = "/logShow", close = "/close",
            help = "/help";


    public ServerControl() {
        start();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        String _help = " /cls - clear terminal /info - server status information \n /close \n /help - it is reference\n";
        System.out.println(_help);

        while (true) {
            try {
                switch (scanner.next()) {
                    case cls:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        break;
                    case info:
                        System.out.println("Active streams: " + Thread.getAllStackTraces().keySet().size());
                        System.out.println("Memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
                        break;
                    case help:
                        System.out.println(_help);
                        break;
                    case close:
                        System.exit(0);
                        return;
                    case sendMessage:
                    default:
                        System.out.println("What does this command mean?");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error enter data");
            }
        }
    }
}