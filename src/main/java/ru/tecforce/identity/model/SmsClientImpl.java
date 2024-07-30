package ru.tecforce.identity.model;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;

import java.util.HashMap;
import java.util.Map;

public class SmsClientImpl implements SmsClient {

	private static final SnsClient sns = SnsClient.create();

	private final String senderId;

	SmsClientImpl(Map<String, String> config) {
		senderId = config.get("senderId");
	}

	@Override
	public void sendSms(String message, String phoneNumber) {
		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("AWS.SNS.SMS.SenderID",
			MessageAttributeValue.builder().stringValue(senderId).dataType("String").build());
		messageAttributes.put("AWS.SNS.SMS.SMSType",
			MessageAttributeValue.builder().stringValue("Transactional").dataType("String").build());

		sns.publish(builder -> builder
			.message(message)
			.phoneNumber(phoneNumber)
			.messageAttributes(messageAttributes));
	}

}
