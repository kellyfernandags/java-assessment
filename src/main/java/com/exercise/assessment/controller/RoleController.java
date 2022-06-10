package com.exercise.assessment.controller;

import com.exercise.assessment.dto.ResponseWrapper;
import com.exercise.assessment.dto.RoleDTO;
import com.exercise.assessment.exception.BadRequestException;
import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.form.RoleForm;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.service.RoleService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "Route to get a list of existing roles")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> getRolesList() {
        ResponseWrapper<List<RoleDTO>> response = new ResponseWrapper<>();
        List<Role> roles = this.roleService.findAll();
        List<RoleDTO> rolesDTO = new ArrayList<>();
        roles.forEach(t -> rolesDTO.add(t.convertEntityToDTO()));
        response.setData(rolesDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Route to get role by role id")
    public ResponseEntity<ResponseWrapper<RoleDTO>> getRoleById(@PathVariable Long id) throws NotFoundException {
        ResponseWrapper<RoleDTO> response = new ResponseWrapper<>();
        Optional<Role> role = this.roleService.findById(id);
        if (role.isPresent()) {
            response.setData(role.get().convertEntityToDTO());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
    }

    @PostMapping
    @ApiOperation(value = "Route to create a new role")
    public ResponseEntity<ResponseWrapper<RoleDTO>> addNewRole(@RequestBody @Valid RoleForm form) throws BadRequestException {
        ResponseWrapper<RoleDTO> response = new ResponseWrapper<>();

        Optional<Role> optionalRole = this.roleService.findByName(form.getName());
        if (optionalRole.isPresent()) {
            String errors = String.format("'%s' already exists", form.getName());
            throw new BadRequestException(errors);
        }
        Role role = form.convert();
        Role savedRole = this.roleService.save(role);
        response.setData(savedRole.convertEntityToDTO());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Route to update an existing role")
    public ResponseEntity<ResponseWrapper<RoleDTO>> updateRole(@PathVariable Long id, @RequestBody @Valid RoleForm form) throws NotFoundException {
        ResponseWrapper<RoleDTO> response = new ResponseWrapper<>();

        Optional<Role> optionalRole = this.roleService.findById(id);
        if (!optionalRole.isPresent()) {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
        Role role = form.convert();
        role.setId(id);
        Role savedRole = this.roleService.save(role);
        response.setData(savedRole.convertEntityToDTO());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Route to delete an existing role using role id")
    public ResponseEntity<ResponseWrapper<String>> deleteRoleById(@PathVariable Long id) throws NotFoundException {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        Optional<Role> role = this.roleService.findById(id);
        if (role.isPresent()) {
            this.roleService.deleteById(id);
            String dataMsg = String.format("'%s' successfully deleted", id);
            response.setData(dataMsg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
    }
}
