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
public class UserLoginRequest {
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 13, message = "Phone number length should be between 10 and 13 digits")
    @Pattern(regexp = "^08.*", message = "Phone number should start with '08'")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    @JsonProperty("password")
    private String password;
}
