package com.shaunwang.livekue.controller;

import javax.persistence.Convert;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shaunwang.livekue.dto.AddStudentRequest;
import com.shaunwang.livekue.dto.ApiResponse;
import com.shaunwang.livekue.dto.StudentDto;
import com.shaunwang.livekue.model.Status;
import com.shaunwang.livekue.model.Student;
import com.shaunwang.livekue.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@Valid @RequestBody AddStudentRequest request){
		long added = studentService.addStudent(request.getEmail(), request.getDescription(), request.getServiceType());
		if(added == -1) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Failed addding student!"), HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(new ApiResponse("Student got in line!"));
		}
	}
	
	@PostMapping("/cancelStudent")
	public ResponseEntity<?> cancelStudent(@Valid @RequestBody StudentDto student){
		long id = student.getId();
		Student savedStudent = studentService.studentCanceled(id);
		if(savedStudent.getStatus() == Status.CANCELED) {
			return ResponseEntity.ok(new ApiResponse("Student canceled!"));
		} else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Failed canceling student!"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getWaitTime")
	public ResponseEntity<Integer> getWaitTime(){
		int waitTime = studentService.getUpdatedWaitTime();
		return ResponseEntity.ok(waitTime);
	}
	
	@GetMapping("/getStudentInfo/{email}")
	public ResponseEntity<Student> getStudentInfo(@PathVariable String email){
		Student student = studentService.getStudentInfo(email);
		return ResponseEntity.ok(student);
	}
	
	@GetMapping("/getWaitTimeForStudent/{id}")
	public ResponseEntity<Integer> getWaitTimeForStudent(long id) {
		return ResponseEntity.ok(studentService.getWaitTimeForStudent(id));
		
	}
	
	
	
	
	private Student MapStudentDtoToStudent(StudentDto dto) {
		Student student = new Student();
		student.setEmail(dto.getStudentName());
		student.setStudentNumber(dto.getStudentNumber());
		student.setDisplayName(dto.getDisplayName());
		student.setId(dto.getId());
		student.setDescription(dto.getDescription());
		student.setStationComment(dto.getStationComment());
		return student;
	}


}

