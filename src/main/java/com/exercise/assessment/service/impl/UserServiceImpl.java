package com.exercise.assessment.service.impl;

import com.exercise.assessment.exception.NotFoundException;
import com.exercise.assessment.model.Role;
import com.exercise.assessment.model.User;
import com.exercise.assessment.repository.UserRepository;
import com.exercise.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private static final String BASE_URI = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/";

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByIdOnApi(String id) throws NotFoundException {
        String uri = BASE_URI + id;

        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForEntity(uri, User.class).getBody();
        if (user != null)
            return user;
        else throw new NotFoundException("'" + id + "' user id is not found");
    }

    @Override
    public User saveOnRepository(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findUserWithRoleById(String userId) throws NotFoundException {
        return this.userRepository.findById(userId).orElseThrow (() ->
                new NotFoundException("'" + userId + "' user id is not found or doesn't have a role associated"));
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findByRole(Role role) {
        return this.userRepository.findByRole(role);
    }

    @Override
    public void deleteById(String id) {
        this.userRepository.deleteById(id);
    }
}
