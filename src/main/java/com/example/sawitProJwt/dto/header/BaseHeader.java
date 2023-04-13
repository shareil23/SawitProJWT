package com.example.sawitProJwt.dto.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestHeader;

@Data
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class BaseHeader {
    @NotBlank(message = "Authorization is mandatory")
    @JsonProperty("Authorization")
    private String authorization;
}
