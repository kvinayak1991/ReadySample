package com.vin.cron;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SampleCron {
	   @Scheduled(fixedRate = 10000)
	   public void fixedRateSch() {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	      Date now = new Date();
	      String strDate = sdf.format(now);
	      System.out.println("Fixed Rate scheduler:: " + strDate);
	   }
}
