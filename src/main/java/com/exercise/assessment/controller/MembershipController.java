package com.exercise.assessment.controller;

import com.exercise.assessment.dto.MembershipDTO;
import com.exercise.assessment.dto.ResponseWrapper;
import com.exercise.assessment.exception.BadRequestException;
import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.form.MembershipForm;
import com.exercise.assessment.model.Membership;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.Team;
import com.exercise.assessment.model.User;
import com.exercise.assessment.service.MembershipService;
import com.exercise.assessment.service.RoleService;
import com.exercise.assessment.service.TeamService;
import com.exercise.assessment.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/membership")
public class MembershipController {

    TeamService teamService;

    UserService userService;

    MembershipService membershipService;

    RoleService roleService;

    @Autowired
    public MembershipController(TeamService teamService, UserService userService, MembershipService membershipService, RoleService roleService) {
        this.teamService = teamService;
        this.userService = userService;
        this.membershipService = membershipService;
        this.roleService = roleService;
    }

    @PostMapping
    @ApiOperation(value = "Route to create a new membership")
    public ResponseEntity<ResponseWrapper<MembershipDTO>> addNewMembership(@RequestBody @Valid MembershipForm form) throws BadRequestException, NotFoundException {
        ResponseWrapper<MembershipDTO> response = new ResponseWrapper<>();

        //check if membership exists for this tuple before save and throw error if yes
        Optional<Membership> membershipOpt = this.membershipService.findExistingMembership(form);
        if (membershipOpt.isPresent()) {
            String errors = String.format("'%s' already exists in the system", form);
            throw new BadRequestException(errors);
        }
        // get record from local database, so they are already assigned to role
        Optional<User> userOpt = this.userService.findUserWithRoleById(form.getUserId());
        // get team from external API/domain
        Optional<Team> teamOpt = this.teamService.findByIdOnApi(form.getTeamId());

        if (!userOpt.isPresent()) {
            String errors = String.format("'%s' is an invalid user or is not assigned to a role yet", form.getUserId());
            throw new NotFoundException(errors);
        }
        if (!teamOpt.isPresent()) {
            String errors = String.format("'%s' is an invalid team", form.getTeamId());
            throw new NotFoundException(errors);
        }

        User user = userOpt.get();
        Team team = teamOpt.get();

        // save record from existing API locally
        this.teamService.saveOnRepository(team);

        Membership membership = new Membership(user, team);
        Membership savedMembership = this.membershipService.save(membership);

        response.setData(savedMembership.convertEntityToDTO());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Route to get existing memberships. It can be called using a specific roleId")
    public ResponseEntity<ResponseWrapper<List<MembershipDTO>>> getMembershipsList(@RequestParam(required = false) String roleId) throws NotFoundException {
        ResponseWrapper<List<MembershipDTO>> response = new ResponseWrapper<>();
        List<MembershipDTO> membershipsDTO = new ArrayList<>();
        List<Membership> memberships;
        long longRoleId;

        if (roleId == null) {
            memberships = this.membershipService.findAll();
        } else {
            try {
                longRoleId = Long.valueOf(roleId);
            } catch (NumberFormatException e) {
                String errors = String.format("'%s' is an invalid 'roleId'. 'roleId' should be a valid number", roleId);
                throw new NotFoundException(errors);
            }

            Optional<Role> roleOpt = this.roleService.findById(longRoleId);
            if (!roleOpt.isPresent()) {
                String errors = String.format("'%s' id is not related to a valid role", roleId);
                throw new NotFoundException(errors);
            }
            memberships = this.membershipService.findByRole(roleOpt.get());
        }
        memberships.forEach(t -> membershipsDTO.add(t.convertEntityToDTO()));
        response.setData(membershipsDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Route to get membership by id")
    public ResponseEntity<ResponseWrapper<MembershipDTO>> getMembershipById(@PathVariable Long id) throws NotFoundException {
        ResponseWrapper<MembershipDTO> response = new ResponseWrapper<>();
        Optional<Membership> membership = this.membershipService.findById(id);
        if (membership.isPresent()) {
            response.setData(membership.get().convertEntityToDTO());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Route to delete an existing membership using membership id")
    public ResponseEntity<ResponseWrapper<String>> deleteMembershipById(@PathVariable Long id) throws NotFoundException {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        Optional<Membership> membership = this.membershipService.findById(id);
        if (membership.isPresent()) {
            this.membershipService.deleteById(id);
            String dataMsg = String.format("'%s' successfully deleted", id);
            response.setData(dataMsg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String errors = String.format("'%s' not found", id);
            throw new NotFoundException(errors);
        }
    }
}
