package com.exercise.assessment.controller;

import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.form.RoleAssignmentForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MembershipControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@Autowired
	private RoleAssignmentController assignment;
	
	private final static String BASE_URI = ("/membership");
	private final static RoleAssignmentForm FORM = new RoleAssignmentForm("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c",1L);

	@BeforeAll
	void setUp() throws Exception {
		assignment.addNewRoleAssignment(FORM);
	}

	@Test
	@Order(1)
	void shouldSaveNewMembershipStatusCode201() throws Exception {
		URI uri = new URI (BASE_URI);
		MembershipForm form = new MembershipForm("7676a4bf-adfe-415c-941b-1739af07039b", FORM.getUserId());
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
	void shouldNotSaveNewMembershipAlreadyExistsStatusCode400() throws Exception  {
		URI uri = new URI (BASE_URI);
		MembershipForm form = new MembershipForm("7676a4bf-adfe-415c-941b-1739af07039b", FORM.getUserId());
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
	void shouldNotSaveNewMembershipStatusCode400_InvalidUser() throws Exception {
		URI uri = new URI (BASE_URI);
		MembershipForm form = new MembershipForm("7676a4bf-adfe-415c-941b-1739af07039b", "XXXXXXXXXXXXX");
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
					.string(Matchers.containsString("user id is not found or doesn't have a role associated")));
	}
	
	@Test
	@Order(4)
	void shouldNotSaveNewMembershipStatusCode400_InvalidTeamId() throws Exception {
		URI uri = new URI (BASE_URI);
		MembershipForm form = new MembershipForm("XXXXXXXXXXXXX", FORM.getUserId());
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
					.string(Matchers.containsString("team id is not found")));
	}
	
	@Test
	@Order(5)
	void shouldGetAllMembershipStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI);
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	@Order(6)
	void shouldGetMembershipByRoleStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI);
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", String.valueOf(FORM.getRoleId())))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.OK.value()));
	}
	
	@Test
	@Order(7)
	void shouldNotGetMembershipByRoleStatusCode400_invalidCharAsRoleId() throws Exception {
		URI uri = new URI (BASE_URI);
	
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

	@Order(8)
	@Test
	void shouldNotGetMembershipByRoleStatusCode400_invalidRoleId() throws Exception {
		URI uri = new URI (BASE_URI);
	
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.param("roleId", "12345"))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(HttpStatus.NOT_FOUND.value()))
			.andExpect(MockMvcResultMatchers
					.content()
					.string(Matchers.containsString("id is not found")));
	}

	@Test
	@Order(9)
	void shouldGetMembershipByMembershipIdStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI + "/1");

		mockMvc
				.perform(MockMvcRequestBuilders
						.get(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.OK.value()));
	}

	@Test
	@Order(10)
	void shouldNotGetMembershipByMembershipIdStatusCode404() throws Exception {
		URI uri = new URI (BASE_URI + "/55555555");

		mockMvc
				.perform(MockMvcRequestBuilders
						.get(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	@Order(11)
	void shouldDeleteMembershipByMembershipIdStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI + "/2");

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.OK.value()));
	}

	@Test
	@Order(12)
	void shouldNotDeleteMembershipByMembershipIdStatusCode404() throws Exception {
		URI uri = new URI (BASE_URI + "/55555555");

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()));
	}
}
