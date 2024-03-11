package com.biagab.gateway.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiRouteResponse {
    private String id;
    private String path;
    private String method;
    private String uri;
    private String apiKey;
    private RewritePath rewrite;

    @Builder
    @Data
    public static class RewritePath {
        private String pattern = "/api/routing/(.*)";
        private String replacement = "/$1";
    }
}
