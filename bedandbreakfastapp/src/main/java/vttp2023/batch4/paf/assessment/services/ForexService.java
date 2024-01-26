package vttp2023.batch4.paf.assessment.services;

import java.io.StringReader;
import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ForexService {


	@Value("${currency.api}")
    private String currency_url;

	RestTemplate template = new RestTemplate(); 

	// TODO: Task 5 
	public float convert(String from, String to, float amount) {
		 
		
		try {
			RequestEntity<Void> req = RequestEntity.get(currency_url).build();
		 ResponseEntity<String> resp = template.exchange(req, String.class);
       	 String payload = resp.getBody().toString();
		 System.out.println(payload);

		 //read the json response
		JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObject = jsonReader.readObject();
		
        JsonObject jsonObject1 = jsonObject.getJsonObject("rates");  //{"SGD":0.88232}
		System.out.println("jsonObject 1" + jsonObject1);
		JsonNumber rate = jsonObject1.getJsonNumber("SGD");
		float value = (float)rate.doubleValue();
		float finalAmount = value * amount;

		return finalAmount; 
			

		} catch (Exception e) {
			return -1000f;
		}
	
		
	}

	 }

