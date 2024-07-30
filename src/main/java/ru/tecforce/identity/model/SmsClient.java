package ru.tecforce.identity.model;

public interface SmsClient {

	void sendSms(String message, String phoneNumber);

}
