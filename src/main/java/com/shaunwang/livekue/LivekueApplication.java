package com.shaunwang.livekue;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
		LivekueApplication.class,
		Jsr310JpaConverters.class 
})
public class LivekueApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Phoenix"));
		DateTimeZone.setDefault(DateTimeZone.forID("America/Phoenix"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LivekueApplication.class, args);
	}

}

