package com.biagab.gateway.components;

import com.biagab.gateway.services.MongoDBInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MongoInitializer implements CommandLineRunner {

    private final MongoDBInitializationService mongoDBInitializationService;

    @Override
    public void run(String... args) {
        mongoDBInitializationService.initialize();
    }
}

