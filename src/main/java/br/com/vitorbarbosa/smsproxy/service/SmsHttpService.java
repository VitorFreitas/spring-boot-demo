package br.com.vitorbarbosa.smsproxy.service;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.vitorbarbosa.smsproxy.model.SmsRequest;
import br.com.vitorbarbosa.smsproxy.model.SmsStatus;
import br.com.vitorbarbosa.smsproxy.repository.SmsRequestAttemptRepository;


@Service
public class SmsHttpService {

	private final RestTemplate restTemplate;
	
	private final Environment env;

	private final SmsRequestAttemptRepository attempts;	

	@Autowired
	public SmsHttpService(SmsRequestAttemptRepository attempts, RestTemplate restTemplate, Environment env) {
		this.attempts = attempts;
		this.restTemplate = restTemplate;
		this.env = env;
	}
	
	public SmsStatus send(SmsRequest request, LocalDateTime expirationDate){
		if (isExpired(expirationDate)) {
			return SmsStatus.INVALID;
		}
		
		SmsStatus status = request(request, expirationDate);
		attempts.save(request, status);
		return status;
	}

	private boolean isExpired(LocalDateTime expirationDate) {
		return expirationDate != null && expirationDate.isBefore(LocalDateTime.now());
	}
	
	private SmsStatus request(SmsRequest request, LocalDateTime expirationDate) {		
		try {			
			System.out.println(request.toJson());
			RequestEntity<String> requestEntity = new RequestEntity<String>(request.toJson(), HttpMethod.PUT, URI.create(env.getProperty("sms.service.url")));
			ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
			return new SmsStatus(exchange.getStatusCode());
		} catch (RestClientException e) {
			return SmsStatus.UNAVAILABLE;
		}
	}
}
