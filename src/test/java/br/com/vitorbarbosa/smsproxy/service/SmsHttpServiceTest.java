package br.com.vitorbarbosa.smsproxy.service;

import static br.com.vitorbarbosa.smsproxy.service.TestUtil.mockService;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import br.com.vitorbarbosa.smsproxy.SmsProxyApplication;
import br.com.vitorbarbosa.smsproxy.model.SmsRequest;
import br.com.vitorbarbosa.smsproxy.model.SmsStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmsProxyApplication.class)
public class SmsHttpServiceTest {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SmsHttpService service;

	@Test
	public void chamada_ok() {
		mockService(restTemplate, withSuccess());
		SmsStatus status = service.send(new SmsRequest(1L, "123", "456", "Hi"), LocalDateTime.of(2017, 5, 2, 10, 0));
		assertEquals(HttpStatus.OK, status.get());
	}

	@Test
	public void erro_no_servico_500_transformado_em_503() {
		mockService(restTemplate, withServerError());
		SmsStatus status = service.send(new SmsRequest(1L, "123", "456", "Hi"), LocalDateTime.of(2017, 5, 2, 10, 0));
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, status.get());
	}

}
