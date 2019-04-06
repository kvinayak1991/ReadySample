package com.vin.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vin.dao.LoginLogoutDao;

@Component
@Aspect
public class RestAspect {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LoginLogoutDao dao;

	@Before("execution(* com.vin.controller.HelloController.*(..))")
	public String getNameAdvice(JoinPoint  joinPoint) throws Exception{
		JSONObject response=new JSONObject();
		System.out.println("execution(* com.vin.controller.HelloController");
		System.out.println(joinPoint.getArgs()[0]);
		System.out.println(joinPoint.getSignature().getName());
		JSONParser parser=new JSONParser();
		JSONObject obj=new JSONObject();
		obj=(JSONObject)parser.parse((joinPoint.getArgs()[0]).toString());
		if(dao.isLoggedinUser((String)obj.get("sessionid"))) {
			System.out.println("USER is LOGGEDIN");
		}
		else{
			System.out.println("USER is NOT LOGGEDIN");
			response.put("201", "USER is NOT LOGGEDIN Exception");
			throw new Error("USER is NOT LOGGEDIN Exceptionaa");
		}
		return response.toJSONString();
	}
	
		@Around("execution(* com.vin.controller.HelloWorldRestController.getHello(..))")
		public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			System.out.println("Around advice Before" + proceedingJoinPoint.getSignature());
			proceedingJoinPoint.proceed();
			System.out.println("Around advice after+" + proceedingJoinPoint.getSignature());
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}
