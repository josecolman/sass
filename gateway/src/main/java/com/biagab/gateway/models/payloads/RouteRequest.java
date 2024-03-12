package com.biagab.gateway.models.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteRequest {

    private String id;
    @NotBlank
    private String path;
    private String method;
    @NotBlank
    private String uri;
    private RewritePath rewrite;
    private String apiKey;

    @Builder
    @Data
    public static class RewritePath {
        @Builder.Default
        private String pattern = "/api/routing/(.*)";
        @Builder.Default
        private String replacement = "/$1";
    }
}
