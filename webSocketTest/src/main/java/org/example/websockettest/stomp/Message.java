package org.example.websockettest.stomp;

import lombok.Data;

@Data
public class Message {
    private String type;
    private String sender;
    private String channelId;
    private String data;

    public void newConnect() {this.type = "new";}
    public void closeConnect() {this.type = "close";}

}
