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
import br.com.vitorbarbosa.smsproxy.model.SmsStatus;
import br.com.vitorbarbosa.smsproxy.service.SmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmsProxyApplication.class)
public class SmsRequestAttemptRepositoryTest {

	@Autowired SmsRequestAttemptRepository repository;
	
	@Autowired @Qualifier("attempts") MongoCollection<Document> attempts;
	
	@Test
	public void insere() {
		attempts.deleteMany(new Document());
		assertEquals(0,attempts.count());
		
		repository.save(new SmsRequest(1L, "123", "456", "Hi"), SmsStatus.UNAVAILABLE);
		
		assertEquals(1,attempts.count());
		assertEquals(1l,attempts.find().first().get("ref"));
		assertEquals(SmsStatus.UNAVAILABLE.get().value() ,attempts.find().first().get("status"));
	}


}
