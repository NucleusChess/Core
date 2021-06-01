package com.nucleuschess;

import com.nucleuschess.net.BasicEndpoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Core {

    public static void main(String[] args) {
        final Server server = new Server("localhost", 9000, "/", null, BasicEndpoint.class);

        try {
            Thread.currentThread().setContextClassLoader(Core.class.getClassLoader());
            server.start();

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
