package com.insideresttest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.insideresttest.controller.Controller;

public class ControllerTests{
	Controller controller;
	MockMvc mockMvc;
	
	@Autowired
	public ControllerTests(Controller controller, MockMvc mockMvc){
		this.controller = controller;
		this.mockMvc = mockMvc;
	}
	
	@Test
	public void contextLoadTest() {
		assertThat(controller).isNot(null);
	}
	
	@Test
	public void authenticateTest() throws Exception {
		String uriString = "http://127.0.0.1:8080/auth?name=insidetest&password=lookup";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uriString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andReturn();

		String resultAuthenticate = mvcResult.getResponse().getContentAsString();
		assertThat(resultAuthenticate).isNotEmpty();
		assertThat(resultAuthenticate).isNotBlank();
	}
	
	@Test
	public void 
}