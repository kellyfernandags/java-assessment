package com.exercise.assessment.controller;

import com.exercise.assessment.dto.ResponseWrapper;
import com.exercise.assessment.dto.UserDTO;
import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.form.RoleAssignmentForm;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import com.exercise.assessment.service.RoleService;
import com.exercise.assessment.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/assignment")
public class RoleAssignmentController {

    RoleService roleService;
    UserService userService;

    @Autowired
    public RoleAssignmentController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "Route to assign a role to an user")
    public ResponseEntity<ResponseWrapper<UserDTO>> addNewRoleAssignment(@RequestBody @Valid RoleAssignmentForm form) throws NotFoundException {
        ResponseWrapper<UserDTO> response = new ResponseWrapper<>();

        // get user from external API/domain
        Optional<User> userOpt = this.userService.findByIdOnApi(form.getUserId());
        // get role that is already loaded to application (set 'Developer' if it was not provided on input form)
        Optional<Role> roleOpt = this.roleService.findById(form.getRoleId());

        if (!userOpt.isPresent()) {
            String errors = String.format("'%s' is an invalid user", form.getUserId());
            throw new NotFoundException(errors);
        }
        if (!roleOpt.isPresent()) {
            String errors = String.format("'%s' is an invalid role", form.getRoleId());
            throw new NotFoundException(errors);
        }

        User user = userOpt.get();
        Role role = roleOpt.get();
        user.setRole(role);

        User savedUser = this.userService.saveOnRepository(user);
        response.setData(savedUser.convertEntityToDTO());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Route to get existing users that are assigned to a role. It can be called using a specific roleId")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getUserAssignedToRolesList(@RequestParam(required = false) String roleId) throws NotFoundException {
        ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>();
        List<UserDTO> usersDTO = new ArrayList<>();
        List<User> users;

        if (roleId == null) {
            users = this.userService.findAll();
        } else {
            long longRoleId;
            try {
                longRoleId = Long.valueOf(roleId);
            } catch (NumberFormatException e) {
                String errors = String.format("'%s' is an invalid 'roleId'. 'roleId' should be a valid number", roleId);
                throw new NotFoundException(errors);
            }

            Optional<Role> roleOpt = roleService.findById(longRoleId);
            if (!roleOpt.isPresent()) {
                String errors = String.format("'%s' id is not related to a valid role", roleId);
                throw new NotFoundException(errors);
            }
            users = this.userService.findByRole(roleOpt.get());
        }
        users.forEach(t -> usersDTO.add(t.convertEntityToDTO()));
        response.setData(usersDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Route to get assigned user by user id")
    public ResponseEntity<ResponseWrapper<UserDTO>> getUserAssignedToARoleById(@PathVariable String id) throws NotFoundException {
        ResponseWrapper<UserDTO> response = new ResponseWrapper<>();
        Optional<User> user = this.userService.findById(id);
        if (user.isPresent()) {
            response.setData(user.get().convertEntityToDTO());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found or not assigned to any role", id);
            throw new NotFoundException(errors);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Route to update an existing user")
    public ResponseEntity<ResponseWrapper<UserDTO>> updateAssignment(@PathVariable String id, @RequestBody @Valid RoleAssignmentForm form) throws NotFoundException {
        ResponseWrapper<UserDTO> response = new ResponseWrapper<>();

        Optional<User> optionalUser = this.userService.findById(id);
        if (!optionalUser.isPresent()) {
            String errors = String.format("'%s' user not found", id);
            throw new NotFoundException(errors);
        }
        Optional<Role> optionalRole = this.roleService.findById(form.getRoleId());
        if (!optionalRole.isPresent()) {
            String errors = String.format("'%s' role not found", form.getRoleId());
            throw new NotFoundException(errors);
        }
        User user = optionalUser.get();
        Role role = optionalRole.get();
        user.setRole(role);
        User savedUser = this.userService.saveOnRepository(user);
        response.setData(savedUser.convertEntityToDTO());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Route to delete an existing assignment for an user id")
    public ResponseEntity<ResponseWrapper<String>> deleteUserAssignedById(@PathVariable String id) throws NotFoundException {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        Optional<User> user = this.userService.findById(id);
        if (user.isPresent()) {
            this.userService.deleteById(id);
            String dataMsg = String.format("'%s' successfully deleted", id);
            response.setData(dataMsg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
    }
}
