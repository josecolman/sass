package com.biagab.gateway.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrUpdateApiRouteRequest {

    private String id;
    @NotBlank
    private String path;
    private String method;
    @NotBlank
    private String uri;
    private RewritePath rewrite;
    private String apiKey;

    @Data
    public static class RewritePath {
        private String pattern = "/api/routing/(.*)";
        private String replacement = "/$1";
    }
}
