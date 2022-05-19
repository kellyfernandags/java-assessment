package com.exercise.assessment.controller;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.exercise.assessment.form.RoleAssignmentForm;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MembershipControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@Autowired
	private RoleAssignmentController assignment;
	
	@Test
	public void shouldSaveNewMembershipStatusCode201() throws Exception {
		
		String requestBodyAssignment = "{\"userId\":\"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\",\"roleId\":1}";
		
		ObjectMapper objectMapper = new ObjectMapper();
		RoleAssignmentForm form = objectMapper.readValue(requestBodyAssignment, RoleAssignmentForm.class);
		assignment.addNewRoleAssignment(form);
		
		URI uri = new URI ("/memberships");
		String requestBody = "{\"userId\":\"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\""
							+ ",\"teamId\":\"7676a4bf-adfe-415c-941b-1739af07039b\"}";
		
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
	public void shouldNotSaveNewMembershipAlreadyExistsStatusCode406() throws Exception  {
		
		String requestBodyAssignment = "{\"userId\":\"b03fb9bb-5dc1-4783-9258-65c0d14a113d\",\"roleId\":2}";
		
		ObjectMapper objectMapper = new ObjectMapper();
		RoleAssignmentForm form = objectMapper.readValue(requestBodyAssignment, RoleAssignmentForm.class);
		assignment.addNewRoleAssignment(form);
		
		URI uri = new URI ("/memberships");
		String requestBody = "{\"userId\":\"b03fb9bb-5dc1-4783-9258-65c0d14a113d\""
							+ ",\"teamId\":\"778f3814-57b9-44d1-996f-eb5630322983\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON));
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(HttpStatus.NOT_ACCEPTABLE.value()));
	}
	
	@Test
	public void shouldNotSaveNewMembershipStatusCode400_InvalidUser() throws Exception {
		URI uri = new URI ("/memberships");
		String requestBody = "{\"userId\":\"XXXXXXXXXXXXX\",\"teamId\":\"7676a4bf-adfe-415c-941b-1739af07039b\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()))
			.andExpect(MockMvcResultMatchers
					.content()
					.string(Matchers.containsString("is an invalid user or is not assigned to a role yet")));
	}
	
	@Test
	public void shouldNotSaveNewMembershipStatusCode400_InvalidTeamId() throws Exception {
		URI uri = new URI ("/memberships");
		String requestBody = "{\"userId\":\"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\",\"teamId\":\"XXXXXXXXXXXXX\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()))
			.andExpect(MockMvcResultMatchers
					.content()
					.string(Matchers.containsString("is an invalid team")));
	}
	
	@Test
	public void shouldGetAllMembershipStatusCode200() throws Exception {
		URI uri = new URI ("/memberships");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	public void shouldGetMembershipByRoleStatusCode200() throws Exception {
		URI uri = new URI ("/memberships");
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", "1"))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	public void shouldNotGetMembershipByRoleStatusCode400_invalidCharAsRoleId() throws Exception {
		URI uri = new URI ("/memberships");
	
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
	public void shouldNotGetMembershipByRoleStatusCode400_invalidRoleId() throws Exception {
		URI uri = new URI ("/memberships");
	
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
