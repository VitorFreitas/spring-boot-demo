package br.com.vitorbarbosa.smsproxy;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@SpringBootApplication
public class SmsProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsProxyApplication.class, args);
    }
    
    @Autowired private Environment env;
    
    @Bean
    public MongoClient mongo(){
    	return new Fongo("in-memory-mongo").getMongo();
    }
    
    @Bean(name="messages")
    public MongoCollection<Document> collection() {
		return mongo().getDatabase("sms").getCollection("messages");
	}
    
    @Bean(name="attempts")
    public MongoCollection<Document> attempts() {
		return mongo().getDatabase("sms").getCollection("attempts");
	}
    
    @Bean
    public RestTemplate restTemplace() {
    	return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));  
	}
        
    public HttpClient httpClient() {
    	return HttpClientBuilder.create()
    				.setRetryHandler(new StandardHttpRequestRetryHandler(config("sms.service.retries"), false))
    				.setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
    				.setConnectionManager(new PoolingHttpClientConnectionManager())
    				.setDefaultRequestConfig(RequestConfig.custom()
    													  .setConnectionRequestTimeout(config("sms.service.connection.timeout"))
    													  .setSocketTimeout(config("sms.service.socket.timeout")).build()
    											).build();
	}
    
    
    private int config(String key) {
		return Integer.valueOf(env.getProperty(key));
	}

}
