package br.com.vitorbarbosa.smsproxy.model;


import static org.junit.Assert.*;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.boot.json.JsonParserFactory;

import com.fasterxml.jackson.core.JsonParser;

public class SmsRequestTest {

	@Test
	public void tamanho_da_mensagem_e_valido() {
		SmsRequest request = new SmsRequest(20L, "", "", " Hi");
		assertTrue(request.lengthIsOK());
	}
	
	@Test
	public void tamanho_da_mensagem_e_invalido() {		
		SmsRequest request = new SmsRequest(20L, "", "", RandomStringUtils.random(180));
		assertFalse(request.lengthIsOK());
	}

	@Test
	public void converte_para_doc_mongo() {		
		SmsRequest request = new SmsRequest(20L, "258", "369", "Hi");
		
		assertEquals(20l, request.toBson().getLong("_id").longValue());
		assertEquals("258", request.toBson().getString("from"));
		assertEquals("369", request.toBson().getString("to"));
		assertEquals("Hi", request.toBson().getString("message"));
	}
	
	@Test
	public void converte_para_doc_do_servico() {		
		SmsRequest request = new SmsRequest(20L, "258", "369", "Hi");
		
		Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap(request.toJson());
		
		assertEquals(20, map.get("id"));
		assertEquals("258", map.get("from"));
		assertEquals("369", map.get("to"));
		assertEquals("Hi", map.get("body"));
	}

}
