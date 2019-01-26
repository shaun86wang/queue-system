package com.shaunwang.livekue;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shaunwang.livekue.controller.AccountController;
import com.shaunwang.livekue.controller.StudentController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LivekueApplicationTests {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	AccountController accountController;
	
	@Autowired
	StudentController studentController;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void makeSureMockMvcRuns() throws Exception{
		assertThat(mockMvc).isNotNull();
	}

}

