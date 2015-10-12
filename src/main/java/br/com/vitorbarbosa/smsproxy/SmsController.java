package br.com.vitorbarbosa.smsproxy;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorbarbosa.smsproxy.model.SmsRequest;
import br.com.vitorbarbosa.smsproxy.service.SmsService;

@RestController("/sms-proxy")
public class SmsController {

	@Autowired private SmsService service;
	
    @RequestMapping(value = "/{from}/to/{to}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> get(@PathVariable Long id, 
    								@PathVariable String from,
    								@PathVariable String to,
    								@RequestParam(required=false, value="expirationDate") LocalDateTime expirationDate,
    								@RequestBody String body){
    	
    	SmsRequest request = new SmsRequest(id, from, to, body);    
    	return ResponseEntity.status(service.send(request, expirationDate).get()).build();
    }
}
	