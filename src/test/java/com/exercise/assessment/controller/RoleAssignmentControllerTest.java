package com.exercise.assessment.controller;

import com.exercise.assessment.form.RoleAssignmentForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
class RoleAssignmentControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;

	private final static String BASE_URI = "/assignment";
	private final static RoleAssignmentForm FORM = new RoleAssignmentForm("a44c0f83-fce7-4099-893d-b430b5a64019",2L);

	@Test
	@Order(1)
	void shouldSaveNewRoleAssignmentStatusCode201() throws Exception {
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
	void shouldSaveNewRoleAssignmentWithDefaultRoleStatusCode201() throws Exception {
		URI uri = new URI (BASE_URI);
		RoleAssignmentForm form = new RoleAssignmentForm("eaf83ba0-7392-4f69-abbc-d060427c9330");
		String requestBody = new ObjectMapper().writeValueAsString(form);

		mockMvc
				.perform(MockMvcRequestBuilders
						.post(uri)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.CREATED.value()))
				.andExpect(jsonPath("$.data.role.id")
						.value("1"));
	}

	@Test
	@Order(3)
	void shouldNotSaveNewRoleAssignmentStatusCode400_InvalidUser() throws Exception {
		URI uri = new URI (BASE_URI);
		RoleAssignmentForm form = new RoleAssignmentForm("XXXXXXXXXXXXX",1L);
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
	@Order(4)
	void shouldNotSaveNewRoleAssignmentStatusCode400_InvalidRole() throws Exception {
		URI uri = new URI (BASE_URI);
		RoleAssignmentForm form = new RoleAssignmentForm(FORM.getUserId(),12345L);
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
	@Order(5)
	void shouldGetAllUserRoleStatusCode200() throws Exception {
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
	void shouldGetUserRoleByRoleStatusCode200() throws Exception {
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
	void shouldNotGetUserRoleByRoleStatusCode400_invalidCharAsRoleId() throws Exception {
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

	@Test
	@Order(8)
	void shouldNotGetUserRoleByRoleStatusCode404_invalidRoleId() throws Exception {
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
						.string(Matchers.containsString("is not found")));
	}


	@Test
	@Order(9)
	void shouldGetUserRoleByUserIdStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI + "/" + FORM.getUserId());

		mockMvc
				.perform(MockMvcRequestBuilders
						.get(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.OK.value()));
	}

	@Test
	@Order(10)
	void shouldNotGetUserRoleByUserIdStatusCode404_invalidUserId() throws Exception {
		URI uri = new URI (BASE_URI + "/5555555");

		mockMvc
				.perform(MockMvcRequestBuilders
						.get(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()))
				.andExpect(MockMvcResultMatchers
						.content()
						.string(Matchers.containsString("user id is not found or doesn't have a role associated")));
	}

	@Test
	@Order(11)
	void shouldUpdateRoleAssignmentStatusCode202() throws Exception {
		URI uri = new URI (BASE_URI + "/" + FORM.getUserId());
		RoleAssignmentForm updForm = new RoleAssignmentForm(FORM.getUserId(),1L);
		String requestBody = new ObjectMapper().writeValueAsString(updForm);

		mockMvc
				.perform(MockMvcRequestBuilders
						.put(uri)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.ACCEPTED.value()))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.data.role.id")
						.value(updForm.getRoleId()));
	}

	@Test
	@Order(12)
	void shouldNotUpdateRoleAssignmentStatusCode400_InvalidUser() throws Exception {
		RoleAssignmentForm form = new RoleAssignmentForm("XXXXXXXXXXXXX",1L);
		URI uri = new URI (BASE_URI + "/" + form.getUserId());
		String requestBody = new ObjectMapper().writeValueAsString(form);

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
	@Order(13)
	void shouldNotUpdateRoleAssignmentStatusCode400_InvalidRole() throws Exception {
		URI uri = new URI (BASE_URI + "/" + FORM.getUserId());
		RoleAssignmentForm updForm = new RoleAssignmentForm(FORM.getUserId(),12345L);
		String requestBody = new ObjectMapper().writeValueAsString(updForm);

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
	@Order(14)
	void shouldDeleteRoleAssignmentStatusCode200() throws Exception {
		URI uri = new URI (BASE_URI + "/" + FORM.getUserId());

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.OK.value()));
	}

	@Test
	@Order(15)
	void shouldNotDeleteRoleAssignmentStatusCode400_InvalidUser() throws Exception {
		URI uri = new URI (BASE_URI + "/" + "XXXXXXXXXXXXX");

		mockMvc
				.perform(MockMvcRequestBuilders
						.delete(uri))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(HttpStatus.NOT_FOUND.value()));
	}
}
