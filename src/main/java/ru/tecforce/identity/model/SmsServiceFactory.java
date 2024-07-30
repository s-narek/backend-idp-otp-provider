package ru.tecforce.identity.model;

import lombok.extern.slf4j.Slf4j;
import ru.tecforce.identity.SmsConstants;

import java.util.Map;

@Slf4j
public class SmsServiceFactory {

	public static SmsClient get(Map<String, String> config) {
		if (Boolean.parseBoolean(config.getOrDefault(SmsConstants.SIMULATION_MODE, "false"))) {
			return (message, phoneNumber) ->
				log.warn(String.format("***** SIMULATION MODE ***** Would send SMS to %s with text: %s", phoneNumber, message));
		} else {
			return new SmsClientImpl(config);
		}
	}

}
