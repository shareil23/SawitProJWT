package com.example.sawitProJwt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class BaseResponse {
    @Builder.Default
    @JsonProperty("time_stamp")
    private Date timeStamp = new Date();

    @Builder.Default
    @JsonProperty("message")
    private String message = "SUCCESS";

    @JsonProperty("data")
    private Object data;
}
