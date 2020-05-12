package example.controller;

import http.HttpServer;
import http.HttpServerApp;

public class App {
    public static void main(String[] args) {
        try {
            new HttpServerApp().run(App.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
