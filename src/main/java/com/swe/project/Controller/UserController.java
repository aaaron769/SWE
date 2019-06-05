package com.swe.project.Controller;

import com.swe.project.Entity.UserEntity;
import com.swe.project.Request.LoginRequest;
import com.swe.project.Request.UserRequest;
import com.swe.project.Response.AppResponse;
import com.swe.project.Response.LoginResponse;
import com.swe.project.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
	
	//the back end

    @Autowired
    private UserServices userServices;

    // Login by email
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody
    LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            return userServices.login(loginRequest);
        } catch (Exception e) {
            return new LoginResponse(e.getMessage());
        }
    }

    // Sign Up by email
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public @ResponseBody
    AppResponse signup(@RequestBody UserRequest userRequest) {
        try {
            String message = userServices.validateSignup(userRequest.getUserEntity());
            if (message != null) {
                return new AppResponse(false, message);
            } else {
                UserEntity userEntity = userServices.signup(userRequest.getUserEntity());
                if (userEntity.getId() != null) {
                    return new AppResponse(true, null, "Email @@email@@ created successfully!".replaceAll("@@email@@", userEntity.getEmail()));
                } else {
                    return new AppResponse(false, "Email @@email@@ cannot create successfully!".replaceAll("@@email@@", userEntity.getEmail()));
                }
            }
        } catch (Exception e) {
            return new AppResponse(false, e.getMessage());
        }
    }

    // View profile
    @RequestMapping(value = "profile/{email}", method = RequestMethod.GET)
    public @ResponseBody
    UserEntity viewProfile(@PathVariable("email") String email) {
        return userServices.viewProfile(email);
    }
}
