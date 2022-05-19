package com.exercise.assessment.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleAssignmentControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@Test
	public void shouldSaveNewRoleAssignmentStatusCode201() throws Exception {
		URI uri = new URI ("/assignment");
		String requestBody = "{\"userId\":\"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\",\"roleId\":1}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.CREATED.value()));
	}
	
	@Test
	public void shouldNotSaveNewRoleAssignmentStatusCode400_InvalidUser() throws Exception {
		URI uri = new URI ("/assignment");
		String requestBody = "{\"userId\":\"XXXXXXXXXXXXX\",\"roleId\":1}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void shouldNotSaveNewRoleAssignmentStatusCode400_InvalidRole() throws Exception {
		URI uri = new URI ("/assignment");
		String requestBody = "{\"userId\":\"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\",\"roleId\":12345}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()));
	}
}
