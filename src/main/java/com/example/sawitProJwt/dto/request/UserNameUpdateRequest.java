package com.example.sawitProJwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class UserNameUpdateRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 60, message = "Name length should not more than 60 characters")
    @JsonProperty("name")
    private String name;
}
