package com.vin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vin.requestmodel.LoginModel;
import com.vin.services.LoginLogoutService;

@RestController
@RequestMapping("api")
public class LoginLogoutController {
	
	@Autowired
	private LoginLogoutService service;
	
	@Autowired
	private HttpServletRequest request;
	
    private static final Logger logger = Logger.getLogger(LoginLogoutController.class);
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	private String getLogin(@RequestBody LoginModel loginModel) {
		//islocked
		//isExpire
		return service.ValidateUser(loginModel);
	}
	
	@RequestMapping(value="testapi" , method=RequestMethod.GET)
	public String testapi() throws ParseException {
//		  https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.profile&response_type=code&access_type=offline&redirect_uri=http://localhost:8080/Sampleannotationconfig/api/testapi&client_id=605554397035-014ok8tl2rlqs5u06qqouaq2aedj69ps.apps.googleusercontent.com
		logger.info("request:::"+request.getParameter("code"));
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "authorization_code");
		map.add("code", request.getParameter("code"));
		map.add("client_id", "605554397035-014ok8tl2rlqs5u06qqouaq2aedj69ps.apps.googleusercontent.com");
		map.add("client_secret", "TgRym37t3x4eJg_M7WZpgmWe");
		map.add("redirect_uri", "http://localhost:8080/Sampleannotationconfig/api/testapi");

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
		
		return response1.getBody();
	}
}
