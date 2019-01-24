package com.shaunwang.livekue.service;


import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.shaunwang.livekue.model.ServiceType;
import com.shaunwang.livekue.model.Status;
import com.shaunwang.livekue.model.Student;
import com.shaunwang.livekue.model.User;
import com.shaunwang.livekue.repository.StudentRepository;
import com.shaunwang.livekue.repository.UserRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	EstimatorService estService;
	
	@Autowired
	UserRepository userRepository;
	
	
	// select
	public Student getNextStudent() {
		Student student = studentRepo.findFirstByStatusAndPlannedTimeLessThanOrderByInlineDateTimeAsc(Status.INLINE, new DateTime());
		student.setStatus(Status.WAITING);
		student.setStartTime(new DateTime());;
		return student;
	}
	
	public Student getStudentInfo(String email) {
		return studentRepo.findFirstByEmailOrderByInlineDateTimeDesc(email);
	}
	
	public Boolean isStudentInline(String email) {
		int ct = studentRepo.countByEmailAndStatus(email, Status.INLINE);
		return ct > 0;
	}
	
	
	public int getWaitTimeForStudent(long id) {
		int lineLength =  studentRepo.countByIdLessThanAndStatus(id, Status.INLINE);
		return lineLength * EstimatorService.getEstServiceTime();
	}
	
	//update
	
	public void studentServed(Student student) {
		student.setEndTime(new DateTime());
		long totalTime = student.getEndTime().compareTo(student.getStartTime());
		student.setTotalTime(Math.toIntExact(totalTime) * 1000);
		studentRepo.save(student);
	}
	
	public Student studentCanceled(long id) {
		Student student = studentRepo.getOne(id);
		student.setStatus(Status.CANCELED);
		Student savedStudent = studentRepo.save(student);
		return savedStudent;
	}
	
	public void updateDescription(long id, String description) {
		studentRepo.updateDescription(id, description);
	}
	
	//create
	public long addStudent(String email, String description, ServiceType serviceType) {
		User user = userRepository.findByEmail(email);
		Student student = new Student();
		student.setStudentName(user.getStudentName());
		student.setStudentNumber(user.getStudentNumber());
		student.setDisplayName(user.getDisplayName());
		student.setEmail(user.getEmail());
		student.setInlineDateTime(new DateTime());
		student.setStatus(Status.INLINE);
		student.setDescription(description);
		student.setServiceType(serviceType);
		
		if(EstimatorService.getWaitTime() == -1) {
			return -1;
		} else {
			
			Student s = studentRepo.save(student);
			return s.getId();
		}
		
	}
	
	// estService
	public int getUpdatedWaitTime() {
		return estService.calculateWaitTime(studentRepo.countByStatus(Status.INLINE));
	}
}
