package com.shaunwang.livekue.service;


import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
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
		Student student = studentRepo.findFirstByStatusOrderByInlineDateTimeAsc(Status.INLINE);
		student.setStatus(Status.WAITING);
		student.setStartTime(new DateTime());
		return studentRepo.save(student);
	}
	
	public Student getStudentInfoById(long id) {
		return studentRepo.findById(id);
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
	
	public List<String> getInlineStudents(){
		return studentRepo.queryInlineStudents();
	}
	
	//update
	
	public void studentServed(long id, String comment) {
		DateTime startTime = studentRepo.getOne(id).getStartTime();
		int totalTime = Seconds.secondsBetween(startTime, new DateTime()).getSeconds();
		studentRepo.updateEndTimeAndTotalTimeAndStatusAndComment(id, new DateTime(), totalTime, Status.SERVED, comment);
	}
	
	public void studentAbsent(long id) {
		studentRepo.updateEndTimeAndTotalTimeAndStatusAndComment(id, new DateTime(), 0, Status.ABSENT, "");
	}
	
	public void studentHere(long id) {
		studentRepo.updateStartTime(id, new DateTime());
	}
	
	public long cancelStudent(long id) {
		studentRepo.updateStatus(id, Status.CANCELED);
		Student s = studentRepo.getOne(id);
		if(s.getStatus() == Status.CANCELED) {
			return id;
		}else {
			return -1;
		}
	}
	
	public void updateDescription(long id, String description) {
		studentRepo.updateDescription(id, description);
	}
	
	//create
	public long addStudent(String email, String description, ServiceType serviceType) {
		if(this.isStudentInlineOrWaiting(email)) return -1;
		if(EstimatorService.getWaitTime() == -1) return -2;
		
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
		
		Student s = studentRepo.save(student);
		
		return s.getId();
	}
	
	// estService
	public int getUpdatedWaitTime() {
		return estService.calculateWaitTime(studentRepo.countByStatus(Status.INLINE));
	}
	
	public int getWaitingStudentsCount() {
		return studentRepo.countByStatus(Status.INLINE);
	}
	
	private boolean isStudentInlineOrWaiting(String email) {
		int studentCount = studentRepo.countByEmailAndStatus(email, Status.INLINE);
		studentCount += studentRepo.countByEmailAndStatus(email, Status.WAITING);
		if(studentCount > 0) {
			return true;
		}else {
			return false;
		}
	}
}
