package com.vin.test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class Test {
public static void main(String[] args) throws ParseException {
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	map.add("grant_type", "authorization_code");
	map.add("code", "4/5QBfyDQK6M1Ckutn26ufwg_SXEvtnwBRDNp7SDVaSC1rK9OTw0prp3Iz6vBaMWRGHRta_g2dKzEjv5e7QzsvrNA");
	map.add("client_id", "605554397035-014ok8tl2rlqs5u06qqouaq2aedj69ps.apps.googleusercontent.com");
	map.add("client_secret", "TgRym37t3x4eJg_M7WZpgmWe");
	map.add("redirect_uri", "http://localhost:8080/api/testapi");

	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	ResponseEntity<String> response = restTemplate.postForEntity("https://accounts.google.com/o/oauth2/token", request , String.class );
	System.out.println("Access Tocken JSON:::::"+response.getBody());
	
	JSONParser parser=new JSONParser();
	JSONObject obj=new JSONObject();
	obj=(JSONObject)parser.parse(response.getBody().toString());
	
	HttpHeaders headers2 = new HttpHeaders();
	headers2.set("Accept", MediaType.APPLICATION_JSON_VALUE);

	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/oauth2/v3/userinfo")
	        .queryParam("access_token", obj.get("access_token"))
	        .queryParam("alt", "json");

	HttpEntity<?> entity = new HttpEntity<>(headers2);

	HttpEntity<String> response1 = restTemplate.exchange(
	        builder.toUriString(), 
	        HttpMethod.GET, 
	        entity, 
	        String.class);
	
	System.out.println("USER Info JSON:::::"+response1.getBody());
}
}
