package com.exercise.assessment.controller;

import com.exercise.assessment.form.RoleForm;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;

	@Autowired 
	private RoleRepository roleRepository;

	private final static String BASE_URI = "/role";
	private final static RoleForm FORM = new RoleForm("DevOps Engineer");

	@Test
	@Order(1)
	void shouldSaveNewRoleStatusCode201() throws Exception {
		URI uri = new URI (BASE_URI);
		String requestBody = new ObjectMapper().writeValueAsString(FORM);

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
	@Order(2)
	void shouldNotSaveNewRoleStatusCode400_NoNameField() throws Exception {
		URI uri = new URI (BASE_URI);
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
	@Order(3)
	void shouldListRolesStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI);
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	@Order(4)
	void shouldGetDetailsForRoleGetByIdStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI + "/1");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()))
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.data.name")
					.exists())
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.data.name")
					.isString())
			.andExpect(MockMvcResultMatchers
					.jsonPath("$.data.name")
					.value("Developer"));
	}

	@Test
	@Order(5)
	void shouldNotGetDetailsForRoleGetByIdStatusCode404() throws Exception {
		URI uri = new URI (BASE_URI + "/0");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@Order(6)
	void shouldUpdateRoleStatusCode202() throws Exception {
		Role role = this.roleRepository.findByName(FORM.getName()).get();
		RoleForm updForm = new RoleForm("new name updated");
		String requestBody = new ObjectMapper().writeValueAsString(updForm);
		URI uri = new URI (BASE_URI + "/" + role.getId());

		mockMvc
				.perform(MockMvcRequestBuilders
						.put(uri)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.ACCEPTED.value()))
				.andExpect(jsonPath("$.data.name")
						.value(updForm.getName()));
	}

	@Test
	@Order(7)
	void shouldNotUpdateRoleDoesNotExistsStatusCode404() throws Exception {
		RoleForm updForm = new RoleForm("new name updated");
		String requestBody = new ObjectMapper().writeValueAsString(updForm);
		URI uri = new URI(BASE_URI + "/5555555555");

		mockMvc
				.perform(MockMvcRequestBuilders
						.put(uri)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@Order(8)
	void shouldNotDeleteRoleByIdStatusCode404() throws Exception {
		URI uri = new URI (BASE_URI + "/0");

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@Order(9)
	void shouldDeleteRoleByIdStatusCode200() throws Exception {
		Role role = roleRepository.findByName("new name updated").get();
		URI uri = new URI (BASE_URI + "/" + role.getId());

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.OK.value()));
	}
}
