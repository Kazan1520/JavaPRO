package com.jawapro.jawapro.DTO.Response;

import com.jawapro.jawapro.Entity.User;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String indexNumber;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getStudent().getFirstName();
        this.lastName = user.getStudent().getLastName();
        this.indexNumber = user.getStudent().getIndexNumber();
    }

}
