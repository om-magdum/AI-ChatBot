package com.example.chatbot;

public class message {

    public static String SENT_BY_ME ="me";
    public static String SENT_BY_bot ="bot";


    String qry;
    String response;

    public String getQry() {
        return qry;
    }

    public void setQry(String qry) {
        this.qry = qry;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public message(String qry, String response) {
        this.qry = qry;
        this.response = response;
    }
}
