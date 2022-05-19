package com.exercise.assessment.controller;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRoleControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@Test
	public void shouldGetAllUserRoleStatusCode200() throws Exception {
		URI uri = new URI ("/users/role");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	public void shouldGetUserRoleByRoleStatusCode200() throws Exception {
		URI uri = new URI ("/users/role");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", "1"))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	public void shouldNotGetUserRoleByRoleStatusCode400_invalidCharAsRoleId() throws Exception {
		URI uri = new URI ("/users/role");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", "n"))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()))
			.andExpect(MockMvcResultMatchers
					.content()
					.string(Matchers.containsString("'roleId' should be a valid number")));
	}
	
	@Test
	public void shouldNotGetUserRoleByRoleStatusCode400_invalidRoleId() throws Exception {
		URI uri = new URI ("/users/role");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", "12345"))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()))
			.andExpect(MockMvcResultMatchers
					.content()
					.string(Matchers.containsString("id is not related to a valid role")));
	}
}
