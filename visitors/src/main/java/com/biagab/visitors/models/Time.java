package com.biagab.visitors.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Time {

    private LocalDateTime timestamp;
    @Builder.Default
    private String context = "routing-service";

}
