package br.com.vitorbarbosa.smsproxy.repository;

import static org.junit.Assert.*;


import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.MongoCollection;

import br.com.vitorbarbosa.smsproxy.SmsProxyApplication;
import br.com.vitorbarbosa.smsproxy.model.SmsRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmsProxyApplication.class)
public class SmsRepositoryTest {

	@Autowired SmsRepository repository;
	
	@Autowired @Qualifier("messages") MongoCollection<Document> messages;
	
	@Test
	public void insere() {
		messages.deleteMany(new Document());
		assertEquals(0,messages.count());
		
		repository.save(new SmsRequest(1L, "123", "456", "Hi"));
		
		assertEquals(1,messages.count());
		assertEquals(1L,messages.find().first().getLong("_id").longValue());
		assertEquals("123",messages.find().first().getString("from"));
		assertEquals("456",messages.find().first().getString("to"));
		assertEquals("Hi",messages.find().first().getString("message"));
	}

}
