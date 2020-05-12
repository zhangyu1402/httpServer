package example.controller;

import http.annotation.Service;

@Service
public class TestService {
    public int add(int a, int b) {
        return a+b;
    }

    public int del(int a, int b) {
        return a-b;
    }
}
