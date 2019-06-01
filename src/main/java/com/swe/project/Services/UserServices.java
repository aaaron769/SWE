package com.swe.project.Services;

import com.swe.project.Entity.UserEntity;
import com.swe.project.Repository.UserRepository;
import com.swe.project.Request.LoginRequest;
import com.swe.project.Response.LoginResponse;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServices {

    private Logger logger = LogManager.getLogger(UserServices.class);

    @Autowired
    protected UserRepository userRepository;

    // Login by email
    public LoginResponse login(LoginRequest loginRequest) {
        logger.info("Validate email [{}] login", loginRequest.getUserEntity().getEmail());
        if (StringUtils.isEmpty(loginRequest.getUserEntity().getEmail())) {
            return new LoginResponse("Email or password is blank!");
        }
        if (StringUtils.isEmpty(loginRequest.getUserEntity().getPassword())) {
            return new LoginResponse("Email or password is blank!");
        }
        UserEntity userEntity = userRepository.findByEmailEquals(loginRequest.getUserEntity().getEmail());
        if (userEntity == null) {
            return new LoginResponse("Email or password is invalid!");
        }
        if (!loginRequest.getUserEntity().getPassword().equals(userEntity.getPassword())) {
            return new LoginResponse("Email or password is blank!");
        }
        logger.info("Email [{}] logged in successfully!", loginRequest.getUserEntity().getEmail());
        return new LoginResponse(true, "Email @@email@@ logged in successfully!".replaceAll("@@email@@", loginRequest.getUserEntity().getEmail()));
    }

    // Sign Up by email
    public UserEntity signup(UserEntity userEntity) {
        logger.info("Email [{}] created successfully!", userEntity.getEmail());
        if (!StringUtils.isEmpty(userEntity.getEmail())) {
            userEntity.setPassword(userEntity.getPassword());
            userEntity.setFirstName(userEntity.getFirstName());
            userEntity.setLastName(userEntity.getLastName());
            return userRepository.save(userEntity);
        }
        return null;
    }

    // View Profile
    public UserEntity viewProfile(String email) {
        return userRepository.findByEmailEquals(email);
    }

    // Validate email
    public String validateSignup(UserEntity userEntity) {
        logger.info("Validate new email [{}]", userEntity.getEmail());
        if (userEntity.getEmail() == null) {
            return "Email or password is blank!";
        }
        if (userEntity.getPassword() == null) {
            return "Email or password is blank!";
        }
        if (userRepository.countByEmail(userEntity.getEmail()) > 0) {
            return String.format("%s is existed!", "Email " + userEntity.getEmail());
        }
        return null;
    }
}
