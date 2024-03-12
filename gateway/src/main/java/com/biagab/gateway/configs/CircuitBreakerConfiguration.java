/*
package com.biagab.gateway.configs;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Porcentaje de fallos para abrir el circuit breaker
                .waitDurationInOpenState(Duration.ofMinutes(1)) // Tiempo en el que el circuit breaker permanece abierto
                .permittedNumberOfCallsInHalfOpenState(10) // Número de llamadas permitidas en estado semiabierto
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // Tipo de ventana deslizante
                .slidingWindowSize(100) // Tamaño de la ventana deslizante
                .minimumNumberOfCalls(10) // Número mínimo de llamadas antes de que el circuit breaker pueda abrirse
                .build();
    }

    @Bean
    public CircuitBreakerEventConsumer circuitBreakerEventConsumer() {
        return new CircuitBreakerEventConsumer();
    }

    @Slf4j
    public static class CircuitBreakerEventConsumer implements EventConsumer<CircuitBreakerEvent> {
        @Override
        public void consumeEvent(CircuitBreakerEvent event) {
            if (event.getEventType() == CircuitBreakerEvent.Type.ERROR) {
                CircuitBreakerOnErrorEvent errorEvent = (CircuitBreakerOnErrorEvent) event;
                log.error("Circuit breaker error: {}", errorEvent.getThrowable().getMessage());
            } else if (event.getEventType() == CircuitBreakerEvent.Type.STATE_TRANSITION) {
                CircuitBreakerOnStateTransitionEvent stateTransitionEvent = (CircuitBreakerOnStateTransitionEvent) event;
                log.info("Circuit breaker state transition: from {} to {}", stateTransitionEvent.getStateTransition().getFromState(), stateTransitionEvent.getStateTransition().getToState());
            } else if (event.getEventType() == CircuitBreakerEvent.Type.SUCCESS) {
                CircuitBreakerOnSuccessEvent successEvent = (CircuitBreakerOnSuccessEvent) event;
                log.info("Circuit breaker operation success: {}", successEvent.toString());
            }
        }
    }
}
*/
