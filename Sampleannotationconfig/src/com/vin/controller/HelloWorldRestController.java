package com.vin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//import com.websystique.springmvc.model.User;
//import com.websystique.springmvc.service.UserService;

@RestController
public class HelloWorldRestController {

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public String listAllUsers() {
		return "Success Response for user";
	}

		@RequestMapping(value="api/Hello1" , method=RequestMethod.GET)
	public String getHello() {
		String msg="Hello Vinayak";
		try {
//			logger.info("Entry of Hello");
			msg="Hello Vinayak";
//			logger.info("Exit of Hello");
		}
		catch(Exception e) {
			msg=e.getMessage();
//			logger.error("HelloController getHello Exception::"+e);
		}
		return msg;
	}

}
