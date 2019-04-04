package com.shaunwang.livekue.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.shaunwang.livekue.model.Student;

@Service
public class TwilioNotificationService implements NotificationServiceInterface {
	@Value("${app.twilioSid}")
    private String twilioSid;
	
	@Value("${app.twilioAuth}")
    private String twilioAuth;
	
	@Value("${app.twilioNumber}")
    private String twilioNumber;
	
	
	@Override
	public void sendNotification(Student student, String m) {
		if(student.getPhoneNumber() == null || student.getPhoneNumber().length() != 10) return;
		Twilio.init(twilioSid, twilioAuth);
		Message message = Message.creator(new PhoneNumber("+1" + student.getPhoneNumber()), new PhoneNumber(twilioNumber), m).create();
		System.out.println(message.getSid());
	}

}
