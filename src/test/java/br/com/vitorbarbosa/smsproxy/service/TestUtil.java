package br.com.vitorbarbosa.smsproxy.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

public class TestUtil {

	public static void mockService(RestTemplate template, DefaultResponseCreator response) {
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(template);		 		 
		mockServer.expect(requestTo("http://localhost:8888/sms/"))
		 					 .andExpect(method(HttpMethod.PUT))
		 					 .andRespond(response);
	}
}
