package com.biagab.visitors.controllers;

import com.biagab.visitors.models.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping({
        "/test",
        "/api/test",
        "/v1/test",
        "/api/v1/test"

})
public class TestController {

    @GetMapping("/time")
    public Mono<Time> time() {
        return Mono.just(new Time(LocalDateTime.now(), "visitors-service"));
    }

    @GetMapping
    public Mono<Time> timeIndex() {
        return time();
    }

    @GetMapping("/")
    public Mono<Time> timeIndexSlash() {
        return time();
    }

    @PostMapping("/time")
    public Mono<ResponseEntity<Time>> createTime(@RequestBody Time time) {
        // Aquí podrías llamar a un servicio o repositorio para crear un nuevo objeto Time
        // En este ejemplo, devolveré un Mono con el objeto creado y un ResponseEntity 201
        return Mono.just(time)
                .map(createdTime -> ResponseEntity.status(HttpStatus.CREATED).body(createdTime));
    }
}
