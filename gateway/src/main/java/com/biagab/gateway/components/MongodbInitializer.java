package com.biagab.gateway.components;

import com.biagab.gateway.services.MongodbInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MongodbInitializer implements CommandLineRunner {

    private final MongodbInitializationService mongoDBInitializationService;

    @Override
    public void run(String... args) {
        mongoDBInitializationService.initialize();
    }
}

