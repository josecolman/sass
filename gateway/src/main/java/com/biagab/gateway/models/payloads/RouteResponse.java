package com.biagab.gateway.models.payloads;

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
public class RouteResponse {

    private String id;
    private String path;
    private String method;
    private String uri;
    private RewritePath rewrite;
    private String apiKey;

    @Builder
    @Data
    public static class RewritePath {
        private String pattern = "/api/routing/(.*)";
        private String replacement = "/$1";
    }
}
