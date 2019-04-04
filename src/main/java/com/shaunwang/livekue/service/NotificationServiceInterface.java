package com.shaunwang.livekue.service;

import com.shaunwang.livekue.model.Student;

public interface NotificationServiceInterface {

	public void sendNotification(Student student, String message);
}
