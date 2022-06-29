package com.jawapro.jawapro.DTO.Request;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    private String firstName;
    private String lastName;
    private String indexNumber;

}
