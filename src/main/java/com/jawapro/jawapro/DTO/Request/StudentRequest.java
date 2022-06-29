package com.jawapro.jawapro.DTO.Request;

import com.jawapro.jawapro.Entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String indexNumber;
    private User user;

    public StudentRequest(User user, UserRequest userRequest){
        this.user = user;
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.indexNumber = userRequest.getIndexNumber();
    }
}
