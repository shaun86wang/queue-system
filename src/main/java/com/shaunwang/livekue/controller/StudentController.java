package com.shaunwang.livekue.controller;

import java.util.List;

import javax.persistence.Convert;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shaunwang.livekue.dto.AddStudentRequest;
import com.shaunwang.livekue.dto.ApiResponse;
import com.shaunwang.livekue.dto.StudentDto;
import com.shaunwang.livekue.dto.UpdateDescriptionRequest;
import com.shaunwang.livekue.model.Status;
import com.shaunwang.livekue.model.Student;
import com.shaunwang.livekue.service.StudentService;

import helper.Mapper;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@Valid @RequestBody AddStudentRequest request){
		long added = studentService.addStudent(request.getEmail(), request.getDescription(), request.getServiceType());
		// if student is already in line
		if(added == -1 ) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Student already in line"));
		}
		else if(added == -2) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Ling is full"), HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok(new ApiResponse("Student got in line! Line id: " + added));
		}
	}
	
	@PostMapping("/cancelStudent/{id}")
	public ResponseEntity<?> cancelStudent(@PathVariable long id){
		long response = studentService.cancelStudent(id);
		if(response == id) {
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
	
	@GetMapping("/getWaitingStudentsCount")
	public ResponseEntity<Integer> getWaitingStudentsCount(){
		return ResponseEntity.ok(studentService.getWaitingStudentsCount());
	}
	
	@GetMapping("/getStudentInfo/{email}")
	public ResponseEntity<StudentDto> getStudentInfo(@PathVariable String email){
		Student student = studentService.getStudentInfo(email);
		StudentDto dto = new Mapper().mapStudentToDto(student);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/getWaitTimeForStudent/{id}")
	public ResponseEntity<Integer> getWaitTimeForStudent(long id) {
		return ResponseEntity.ok(studentService.getWaitTimeForStudent(id));
	}
	
	@PostMapping("/updateDescription")
	public ResponseEntity<ApiResponse> updateDescription(@Valid @RequestBody UpdateDescriptionRequest request) {
		studentService.updateDescription(request.getId(), request.getDescription());
		return ResponseEntity.ok(new ApiResponse("Description Updated"));
	}
	
	@GetMapping("/getInlineStudents")
	public ResponseEntity<ApiResponse> getInlineStudents() {
		List<String> students = studentService.getInlineStudents();
		return ResponseEntity.ok(new ApiResponse(students.toString()));
	}
	

}

