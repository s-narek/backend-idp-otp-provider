package ru.tecforce.identity.model;

public interface OtpService {

	void send(String phoneNumber, String message);

}
