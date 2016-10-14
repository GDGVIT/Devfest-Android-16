package com.gdgvitvellore.devfest.Entity.Events;


public class ChatBotApiResponse {

    public int responseCode ;
    public String responseAnswer ;

    public ChatBotApiResponse(int responseCode, String responseAnswer) {
        this.responseCode = responseCode;
        this.responseAnswer = responseAnswer;
    }

    public String getResponseAnswer() {
        return responseAnswer;
    }

    public void setResponseAnswer(String responseAnswer) {
        this.responseAnswer = responseAnswer;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
