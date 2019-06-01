package com.swe.project.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swe.project.Entity.UserEntity;

public class LoginRequest {

    @JsonProperty("user")
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
