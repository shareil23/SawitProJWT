package com.example.sawitProJwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class UserRegistrationRequest {
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 13, message = "Phone number length should be between 10 and 13 digits")
    @Pattern(regexp = "^08.*", message = "Phone number should start with '08'")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 60, message = "Name length should not more than 60 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 16, message = "Password length should be between 6 and 16 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password should contain at least one capital letter and one number")
    @JsonProperty("password")
    private String password;
}
