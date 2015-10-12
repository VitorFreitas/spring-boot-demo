package br.com.vitorbarbosa.smsproxy;

import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.mongodb.MongoClient;

import br.com.vitorbarbosa.smsproxy.service.TestUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmsProxyApplication.class)
@WebAppConfiguration
public class SmsProxyApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Autowired RestTemplate template;
	
	@Autowired MongoClient client;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void fluxo_basico() throws Exception {
		TestUtil.mockService(template, withStatus(HttpStatus.CREATED));
		
		mvc.perform(
				 		put("/02122524585/to/03246877895/589675")
				 		.content("Hi")
				 	)
				.andExpect(status()
				.isCreated());
		
	}
	
	@Test
	public void fluxo_basico_com_falha() throws Exception {
		TestUtil.mockService(template, withServerError());
		
		mvc.perform(
				 		put("/02122524585/to/03246877895/122121")
				 		.content("Hi")
				 	)
				.andExpect(status()
				.isServiceUnavailable());
		
	}

}
