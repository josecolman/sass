package com.biagab.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "routes")
public class Route {

    @Id
    private String id;
    private String path;
    private String method;
    private String uri;
    private RewritePath rewrite;
    private String apiKey;

    public boolean hasRewrite() {
        return rewrite != null
                && StringUtils.hasLength(rewrite.getPattern())
                && StringUtils.hasLength(rewrite.getReplacement());
    }

    public void setRewritePath(String pattern, String replacement) {
        this.rewrite = new RewritePath(pattern, replacement);
    }

    @AllArgsConstructor
    @Builder
    @Data
    public static class RewritePath {
        @Builder.Default
        private String pattern = "/api/routing/(.*)";
        @Builder.Default
        private String replacement = "/$1";
    }
}
