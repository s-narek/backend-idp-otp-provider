package ru.tecforce.identity.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessageException;
import ru.psbank.message_gate.out_message_service.*;
import ru.tecforce.identity.exception.ServiceException;

@Slf4j
public class SmsClientImpl implements SmsClient {

	private final WebServiceTemplate webServiceTemplate;
	private final SmsClientProperties properties;

	public SmsClientImpl(WebServiceTemplate webServiceTemplate, SmsClientProperties properties) {
		this.webServiceTemplate = webServiceTemplate;
		this.properties = properties;
	}

	@Override
	public void sendSms(String otp, String phone) {
		ConsumeOutMessageRequest request = createConsumeOutMessageRequest(phone, otp);
		try {
			ConsumeOutMessageResponse response = (ConsumeOutMessageResponse)
					webServiceTemplate.marshalSendAndReceive(properties.getUrl(), request);
			processResponse(response);
		} catch (SoapMessageException e) {
			log.error("Sms not sent", e);
			throw new ServiceException(e.getMessage(), "UNAVAILABLE");
		}
	}

	private void processResponse(ConsumeOutMessageResponse response) {
		if (response.getResponseCode() == ConsumeOutMessageResponseCode.ERROR_SYSTEM_BLOCKED) {
			var errorMessage = "Sms not sent, error: system blocked";
			log.error(errorMessage);
			throw new ServiceException(errorMessage, "BLOCKED");
		} else if (response.getResponseCode() == ConsumeOutMessageResponseCode.ERROR_MESSAGE_ID_DUPLICATE) {
			var errorMessage = "Sms not sent, error: messageId duplicate";
			log.error(errorMessage);
			throw new ServiceException(errorMessage, "DUPLICATE");
		}
	}

	private ConsumeOutMessageRequest createConsumeOutMessageRequest(String phone, String otp) {
		ConsumeOutMessageArg consumeOutMessageArg = createConsumeOutMessageArg(phone, otp);
		var consumeOutMessageRequest = new ConsumeOutMessageRequest();
		consumeOutMessageRequest.setAuth(createAuth());
		consumeOutMessageRequest
				.getConsumeOutMessageArg()
				.add(consumeOutMessageArg);
		return consumeOutMessageRequest;
	}

	private Auth createAuth() {
		var auth = new Auth();
		auth.setLogin(properties.getLogin());
		auth.setPassword(properties.getPassword());
		return auth;
	}

	private OutMessageTemplate createOutMessageTemplate(String message) {
		var outMessageTemplate = new OutMessageTemplate();
		outMessageTemplate.setText(message);
		return outMessageTemplate;
	}

	private ConsumeOutMessageArg createConsumeOutMessageArg(String phone, String otp) {
		var outMessageTemplate = createOutMessageTemplate(String.format(properties.getMes(), otp));
		var consumeOutMessageArg = new ConsumeOutMessageArg();
		consumeOutMessageArg.setAddress(phone);
		consumeOutMessageArg.setOutMessageTemplate(outMessageTemplate);
		consumeOutMessageArg.setClientId(properties.getSender());
		return consumeOutMessageArg;
	}

}
