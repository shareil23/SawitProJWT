package com.example.sawitProJwt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class UserNameOnlyResponse {
    @JsonProperty("name")
    private String name;
}
