package com.shaunwang.livekue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.shaunwang.livekue.dto.ApiResponse;
import com.shaunwang.livekue.service.StudentService;

@Controller
public class SocketController {
	@Autowired
	StudentService studentService;
	
	@CrossOrigin
	@SendTo("/clientSocket/getInlineStudents")
	@MessageMapping("/getInlineStudents")
    public String getInlineStudents() throws Exception {
		List<String> students = studentService.getInlineStudents();
		return "[\"" + String.join("\",\"", students) + "\"]";
    }
}
