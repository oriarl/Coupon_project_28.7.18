package com.example.demo.entry;

public class Connection {

    private int port;
    private String url;

    @Override
    public String toString() {
        return "DBConnection{" +
                "port=" + port +
                ", url='" + url + '\'' +
                '}';
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection(int port, String url) {
        this.port = port;
        this.url = url;
    }
}