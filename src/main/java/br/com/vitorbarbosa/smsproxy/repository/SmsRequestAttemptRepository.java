package br.com.vitorbarbosa.smsproxy.repository;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import br.com.vitorbarbosa.smsproxy.model.SmsRequest;
import br.com.vitorbarbosa.smsproxy.model.SmsStatus;

@Repository
public class SmsRequestAttemptRepository {

	private final MongoCollection<Document> attempts;
			
	@Autowired
	public SmsRequestAttemptRepository(@Qualifier("attempts") MongoCollection<Document> attempts) {
		this.attempts = attempts;	
	}

	public void save(SmsRequest request, SmsStatus status){
		Map<String, Object> values = new HashMap<>();
		values.put("ref", request.getId());
		values.put("status", status.get().value());
		attempts.insertOne( new Document(values));
	}
}
