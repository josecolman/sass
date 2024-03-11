package com.biagab.gateway.configs;

import com.biagab.gateway.services.ApiPathRouteLocatorImpl;
import com.biagab.gateway.services.ApiRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder) {
        return new ApiPathRouteLocatorImpl(apiRouteService, routeLocatorBuilder);
    }

  /* // @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("dynamic_routes", this::configureDynamicRoutes)
                .build();
    }

    private Buildable<Route> configureDynamicRoutes(PredicateSpec predicateSpec) {
        return predicateSpec
                .path("/api/**")
                .filters(f -> f
                        //.rewritePath("/api/(?<remaining>.*)", "/${remaining}")
                        .filter(gatewayFilter()))
                .uri("lb://dynamicService");
    }

    private GatewayFilter gatewayFilter() {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String apiKey = request.getHeaders().getFirst("X-API-KEY");
            String path = request.getURI().getPath();

            return serviceRouteRepository.findFirstByPathStartingWithAndApiKey(path, apiKey)
                    .flatMap(route -> {

                        //URI uri = URI.create(route.getUrl());

                        String newPath = path.replaceFirst("^" + route.getPath(), "");

                        URI destinationUri = URI.create(route.getUrl() + newPath);

                        log.info("Destination URI: {}", destinationUri);

                        ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(destinationUri).build();
                        return chain.filter(exchange.mutate().request(newRequest).build());

                    })
                    .switchIfEmpty(chain.filter(exchange));
        };
    }*/

   /* @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        return builder.routes()
                .route("dynamic_route",
                        r -> r.path("/api/**")
                                .filters(f -> f
                                        .filter(loggingFilter)
                                        .filter(gatewayFilter()))
                                .uri("lb://dynamicService"))
                .build();
    }

    private GatewayFilter gatewayFilter() {

        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String key = request.getHeaders().getFirst("X-API-KEY");
            String path = request.getURI().getPath();
            String newPath = path.replaceFirst("^/api/routing/", "");

            Mono<ServiceRoute> route = serviceRouteRepository.findFirstByPathStartingWithAndApiKey(path, key);

            return route.flatMap(serviceRoute -> {

                URI destinationUri = URI.create(serviceRoute.getUrl() + newPath);
                log.info("Destination URI: {}", destinationUri);

                ServerHttpRequest newRequest = request.mutate().uri(destinationUri).build();
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
                return chain.filter(exchange);

            }).switchIfEmpty(Mono.defer(() -> {
                exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                return exchange.getResponse().setComplete();
            }));
        };
    }*/

   /* @Bean
    public RouterFunction<ServerResponse> dynamicRoutes() {
        return request -> {
            Flux<RouteConfig> routeConfigs = routeRepository.findAll();

            return routeConfigs.flatMap(config -> {
                HandlerFunction<ServerResponse> handlerFunction = serverRequest -> {
                    URI destinationUri = URI.create(config.getServiceUrl() + serverRequest.uri().getPath());
                    return ServerResponse.temporaryRedirect(destinationUri).build();
                };

                RouterFunction<ServerResponse> route = RouterFunctions.route()
                        .path("/api" + config.getPath(), builder -> builder
                                .GET("", handlerFunction)
                                .POST("", handlerFunction)
                                .PUT("", handlerFunction)
                                .DELETE("", handlerFunction)
                        )
                        .build();

                return Mono.just(route);
            }).next().switchIfEmpty(Mono.just(RouterFunctions.route(serverRequest -> ServerResponse.status(HttpStatus.NOT_FOUND).build())));
        };
    }*/

    /*@Bean
    public RouterFunction<ServerResponse> dynamicRoutes() {
        return RouterFunctions.route()
                .filter((serverRequest, serverResponse) -> {
                    Flux<RouteConfig> routeConfigs = routeRepository.findAll();

                    return routeConfigs.flatMap(config -> {
                        URI destinationUri = URI.create(config.getServiceUrl() + serverRequest.uri().getPath());
                        return ServerResponse.temporaryRedirect(destinationUri).build();
                    }).next().switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
                })
                .GET("/api/routing/**", serverRequest -> {
                    URI destinationUri = URI.create("http://localhost:8082" + serverRequest.uri().getPath());
                    return ServerResponse.temporaryRedirect(destinationUri)
                            .build();
                })
                .POST("/api/routing/**", serverRequest -> {
                    URI destinationUri = URI.create("http://localhost:8082" + serverRequest.uri().getPath());
                    return ServerResponse.temporaryRedirect(destinationUri).build();
                })
                .GET("/api/visitors/**", serverRequest -> {
                    URI destinationUri = URI.create("http://localhost:8081" + serverRequest.uri().getPath());
                    return ServerResponse.temporaryRedirect(destinationUri).build();
                })
                .POST("/api/visitors/**", serverRequest -> {
                    URI destinationUri = URI.create("http://localhost:8081" + serverRequest.uri().getPath());
                    return ServerResponse.temporaryRedirect(destinationUri).build();
                })
                .build();
    }*/

    /*@Bean
    public RouteDefinitionLocator routeDefinitionLocator() {
        return () -> routeRepository
                .findAll()
                .map(this::convertToRouteDefinition);
    }

    private RouteDefinition convertToRouteDefinition(RouteConfig config) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(config.getId());
        routeDefinition.setUri(URI.create(config.getServiceUrl()));

        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        predicateDefinition.addArg("pattern", config.getPattern());
        routeDefinition.getPredicates().add(predicateDefinition);

        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("RewritePath");
        filterDefinition.addArg("regexp", config.getPattern());
        filterDefinition.addArg("replacement", config.getReplacement());
        routeDefinition.getFilters().add(filterDefinition);

        return routeDefinition;
    }*/

   /* @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        return builder.routes()
                .route("routing-service",
                        r -> r.path("/api/routing/**")
                                .filters(f ->
                                        f.filter(loggingFilter)
                                                .filter(new RewritePathGatewayFilterFactory()
                                                        .apply(c -> c
                                                                .setRegexp("/api/routing/(.*)")
                                                                .setReplacement("/$1")
                                                        )
                                                )
                                )
                                .uri("http://localhost:8082")
                )
                .route("visitors-service", r -> r.path("/api/visitors/**")
                        .filters(f -> f.filter(loggingFilter)

                                .filter(new RewritePathGatewayFilterFactory()
                                        .apply(c -> c
                                                .setRegexp("/api/visitors/(.*)")
                                                .setReplacement("/$1")
                                        )
                                )

                        )

                        .uri("http://localhost:8081/")) // URL de tu servicio visitors en el puerto 8083
                .build();
    }
*/

    /*private final RouteRepository routeRepository;
    private final RouteLocatorBuilder builder;
    private final LoggingFilter loggingFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {

        return routeRepository.findAll()

                .map(routeConfig -> builder.routes()
                        .route("routing-service",
                                r -> r.path(routeConfig.getPath())
                                        .filters(f ->
                                                f.filter(loggingFilter)
                                                        .filter(new RewritePathGatewayFilterFactory()
                                                                .apply(c -> c
                                                                        .setRegexp(routeConfig.getPattern())
                                                                        .setReplacement(routeConfig.getReplacement())
                                                                )
                                                        )
                                        )
                                        .uri(routeConfig.getServiceUrl())
                        ).build()
                )
                .onErrorMap(throwable -> new RuntimeException("Error al configurar las rutas dinámicas", throwable))
                .blockFirst();
    }*/

   /* @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {

        return routeRepository.findAll()

                .map(routeConfig -> builder.routes()
                .route("routing-service",
                        r -> r.path(routeConfig.getPath())
                              .filters(f ->
                                        f.filter(loggingFilter)
                                        .filter(new RewritePathGatewayFilterFactory()
                                                        .apply(c -> c
                                                                .setRegexp(routeConfig.getPattern())
                                                                .setReplacement(routeConfig.getReplacement())
                                                        )
                                        )
                              )
                              .uri(routeConfig.getServiceUrl())
                ).build()
                )
                .blockFirst();
    }*/

    /*routeRepository.findAll().subscribe(routeConfig -> {

            builder.routes()
                    .route("routing-service",
                            r -> r.path(routeConfig.getPath())
                                    .filters(f ->
                                            f.filter(loggingFilter)
                                                    .filter(new RewritePathGatewayFilterFactory()
                                                            .apply(c -> c
                                                                    .setRegexp(routeConfig.getPattern())
                                                                    .setReplacement(routeConfig.getReplacement())
                                                            )
                                                    )
                                    )
                                    .uri(routeConfig.getServiceUrl())
                    );
        });*/


    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        return builder.routes()

                .route("routing-service",
                        r -> r.path("/api/routing/**")
                                .filters(f ->
                                        f.filter(loggingFilter)
                                                .filter(new RewritePathGatewayFilterFactory()
                                                        .apply(c -> c
                                                                .setRegexp("/api/routing/(.*)")
                                                                .setReplacement("/$1")
                                                        )
                                                )
                                )
                                .uri("http://localhost:8082")
                )*/

              /*  .route("routing-service-test",
                        r -> r.path("/test")
                                .filters(f -> f.filter(loggingFilter))
                                .uri("http://localhost:8082/v1/test/time")

                )*/
                /*.route("routing-service-direct",
                        r -> r.path("/api/routing/v1/test/time")
                                .filters(f ->
                                        f.filter(loggingFilter)
                                        .filter(new RewritePathGatewayFilterFactory()
                                                .apply(c -> c
                                                        .setRegexp("/api/routing/v1/test/time")
                                                        .setReplacement("/v1/test/time")
                                                )
                                        )
                                )
                                .uri("http://localhost:8082")
                )*/
                /*.route("routing-service-direct",
                        r -> r.path("/api/routing/v1/test/time")
                                .filters(f -> f.filter(loggingFilter))
                                .uri("http://localhost:8082/")

                )*/
/*
                .route("routing-service-direct",
                        r -> r.path("/api/routing/v1/test/time")
                        .filters(f -> f.filter(loggingFilter))
                        .uri("http://saas.biagab.com:8082/v1/test/time")

                )
                .route("routing-service", r -> r.path("/api/routing2/**")
                        .filters(f -> f.filter(loggingFilter))
                        .uri("http://localhost:8082/")) // URL de tu servicio routing en el puerto 8082
                .route("visitors-service", r -> r.path("/api/visitors/**")
                        .filters(f -> f.filter(loggingFilter))
                        .uri("http://localhost:8081/")) // URL de tu servicio visitors en el puerto 8083*/
              /*  .build();
    }
*/

   /* @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("routing-service", r -> r.path("/api/routing/**")
                        .filters(f -> f.rewritePath("/api/routing/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8082")) // URL de tu servicio routing
                .route("visitors-service", r -> r.path("/api/visitors/**")
                        .filters(f -> f.rewritePath("/api/visitors/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8081")) // URL de tu servicio visitors
                .build();
    }*/

   /* private final DynamicRoutingFilter filter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
 return builder.routes()
                .route("dynamic_route", this::configureDynamicRoute)
                .build();


        return builder.routes()
                .route("dynamic_route", r ->
                        r.path("/api/**")
                                .filters(f -> f.filter(filter))
                                //.uri("lb://service-id")
                                .uri("http://localhost:8081/v1/test/time")
                )
                // Puedes agregar más rutas aquí según sea necesario
                .build();
    }*/



 /*private Route.AsyncBuilder configureDynamicRoute(PredicateSpec r) {
        return r.path("/api/**")
                .filters(f -> f.filter(apiKeyFilter))
                .uri(getServiceUriFromConfig());
    }

    private String getServiceUriFromConfig() {
        // Aquí obtienes la configuración de la base de datos
        ConfiguracionServicio configuracion = configuracionServicioService.obtenerConfiguracionPorApiKey("apikey");
        return (configuracion != null) ? configuracion.getUrlServicio() : "http://tuservicio.com";
    }*/


}



