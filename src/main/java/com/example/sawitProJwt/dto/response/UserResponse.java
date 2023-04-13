package com.example.sawitProJwt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class UserResponse {
    @JsonProperty("id_user")
    private Long id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("createdDate")
    private Date createdDate;

    @JsonProperty("updateDate")
    private Date updatedDate;
}
