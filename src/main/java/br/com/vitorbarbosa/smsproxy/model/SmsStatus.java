package br.com.vitorbarbosa.smsproxy.model;

import org.springframework.http.HttpStatus;

public class SmsStatus {
	
	public static final SmsStatus INVALID = new SmsStatus(HttpStatus.UNPROCESSABLE_ENTITY);
	
	public static final SmsStatus TOO_LARGE = new SmsStatus(HttpStatus.PAYLOAD_TOO_LARGE);

	public static final SmsStatus UNAVAILABLE = new SmsStatus(HttpStatus.SERVICE_UNAVAILABLE);
	
	private final HttpStatus status;

	public SmsStatus(HttpStatus statusCode) {
		status = statusCode;
	}
	
	public HttpStatus get() {
		return status;
	}

}
