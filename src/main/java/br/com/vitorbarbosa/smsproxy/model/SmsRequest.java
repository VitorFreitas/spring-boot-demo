package br.com.vitorbarbosa.smsproxy.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import br.com.vitorbarbosa.smsproxy.JsonUtil;

public class SmsRequest implements Serializable {

	private static final long serialVersionUID = 4925322579630417529L;

	private final Long id;
	
	private final String from;
	
	private final String to;
	
	private final String message;

	public SmsRequest(Long id, String from, String to, String message) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.message = message;
	}

	public String toJson() {
		Map<String, Object> values = new HashMap<>();
		values.put("id", id);
		values.put("from", from);
		values.put("to", to);
		values.put("body", message);
		return JsonUtil.toJson(values);
	}
	
	public Document toBson() {
		Map<String, Object> values = new HashMap<>();
		values.put("_id", id);
		values.put("from", from);
		values.put("to", to);
		values.put("message", message);
		return new Document(values);
	}
	
	public Boolean lengthIsOK() {
		return message.length() <= 160;
	}

	public Object getId() {
		return id;
	}
	
}
