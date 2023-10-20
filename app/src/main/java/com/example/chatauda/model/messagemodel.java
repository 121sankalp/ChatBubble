package com.example.chatauda.model;

public class messagemodel {
    String uId, message,messageid ;  ;
    long timestamp ;


    public messagemodel(String uId, String message, long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public messagemodel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }
    public messagemodel ()
    { }

    public String getuId() {
        return uId;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
