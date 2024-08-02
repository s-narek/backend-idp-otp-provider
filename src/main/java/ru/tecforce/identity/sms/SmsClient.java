package ru.tecforce.identity.sms;

public interface SmsClient {

	void sendSms(String message, String phoneNumber);

}
