package br.com.vitorbarbosa.smsproxy.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

import br.com.vitorbarbosa.smsproxy.model.SmsRequest;

@Repository
public class SmsRepository {

	private final MongoCollection<Document> messages;
	
	@Autowired	
	public SmsRepository(@Qualifier("messages") MongoCollection<Document> messages) {
		this.messages = messages;
	}

	public void save(SmsRequest request){		
		messages.replaceOne(new Document("_id", request.getId()), request.toBson());
	}	
}
