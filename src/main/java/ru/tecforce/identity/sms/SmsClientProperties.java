package ru.tecforce.identity.sms;

import lombok.Data;

@Data
public class SmsClientProperties {
    private String url;
    private String login;
    private String password;
    private String sender;
    private String mes;
    private String provider;
}
