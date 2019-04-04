package com.shaunwang.livekue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaunwang.livekue.dto.ApiResponse;
import com.shaunwang.livekue.dto.StudentDto;
import com.shaunwang.livekue.dto.StudentServedRequest;
import com.shaunwang.livekue.helper.Mapper;
import com.shaunwang.livekue.model.Student;
import com.shaunwang.livekue.service.NotificationServiceInterface;
import com.shaunwang.livekue.service.StudentService;

@RestController
@RequestMapping("/api/station")
public class StationController {
	@Autowired 
	StudentService studentService;
	
	@Autowired
	NotificationServiceInterface notificationService;
	
	@GetMapping("/getNextStudent")
	public ResponseEntity<StudentDto> getNextStudent(){
		Student student = studentService.getNextStudent();
		String studentMessage = "Hi " + student.getStudentName() + " , please go up to the counter.";
		notificationService.sendNotification(student, studentMessage);
		
		Student studentAfter = studentService.peekNextStudent();
		if(studentAfter != null) {
			String studentAfterMessage = "Hi " + studentAfter.getStudentName() + ", you are next in line to be served.";
			notificationService.sendNotification(studentAfter, studentAfterMessage);
		}
		
		//mapping to be refactored
		StudentDto studentDto = new Mapper().mapStudentToDto(student);
		return ResponseEntity.ok(studentDto);
	}
	
	@PostMapping("/studentNotHere/{id}")
	public ResponseEntity studentNotHere(@PathVariable long id) {
		studentService.studentAbsent(id);
		return ResponseEntity.ok(new ApiResponse("Student Status Updated"));
	}
	
	@PostMapping("/studentServed")
	public ResponseEntity studentServed(@RequestBody StudentServedRequest request) {
		studentService.studentServed(request.id, request.comment);
		return ResponseEntity.ok(new ApiResponse(Long.toString(request.id)));
	}
}
