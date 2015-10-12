package br.com.vitorbarbosa.smsproxy.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vitorbarbosa.smsproxy.model.SmsRequest;
import br.com.vitorbarbosa.smsproxy.model.SmsStatus;
import br.com.vitorbarbosa.smsproxy.repository.SmsRepository;

@Service
public class SmsService {

	private final SmsRepository repository;
	
	private final SmsHttpService httpService;

	@Autowired
	public SmsService(SmsRepository repository, SmsHttpService httpService) {
		this.repository = repository;
		this.httpService = httpService;
	}
	
	public SmsStatus send(SmsRequest request, LocalDateTime expirationDate){
		if (!request.lengthIsOK()) {
			return SmsStatus.TOO_LARGE;
		}
		repository.save(request);		
		return httpService.send(request, expirationDate);
	}
	
}
