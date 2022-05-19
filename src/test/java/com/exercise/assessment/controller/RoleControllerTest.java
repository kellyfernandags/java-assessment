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

import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.RoleRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;

	@Autowired 
	private RoleRepository roleRepository;
	
	@Test
	public void shouldSaveNewRoleStatusCode201() throws Exception {
		URI uri = new URI ("/roles");
		String requestBody = "{\"name\":\"Scrum master\"}";
		
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
	public void shouldNotSaveNewRoleStatusCode400_NoNameField() throws Exception {
		URI uri = new URI ("/roles");
		String requestBody = "{\"nome\":\"Scrum master\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void shouldListRolesStatusCode200() throws Exception {
		URI uri = new URI ("/roles");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	public void shouldGetDetailsForRoleGetByIdStatusCode200() throws Exception {
		URI uri = new URI ("/roles/1");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.name")
					.exists())
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.name")
					.isString())
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.name")
					.value("Developer"));
	}

	@Test
	public void shouldNotGetDetailsForRoleGetByIdStatusCode404() throws Exception {
		URI uri = new URI ("/roles/0");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void shouldNotDeleteRoleByIdStatusCode404() throws Exception {
		URI uri = new URI ("/roles/0");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.delete(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void shouldDeleteRoleByIdStatusCode200() throws Exception {
		URI uri = new URI ("/roles");
		String requestBody = "{\"name\":\"To Be Deleted\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON));
		
		Role role = roleRepository.findByName("To Be Deleted");
		uri = new URI ("/roles/" + role.getId());
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.delete(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
}
