package com.biagab.gateway.services;

import com.biagab.gateway.models.Route;
import com.biagab.gateway.models.RouteConfig;
import com.biagab.gateway.models.ServiceRoute;
import com.biagab.gateway.models.User;
import com.biagab.gateway.repos.RouteRepository;
import com.biagab.gateway.repos.RouteConfigRepository;
import com.biagab.gateway.repos.ServiceRouteRepository;
import com.biagab.gateway.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class MongoDBInitializationService {

    private final UserRepository userRepository;
    private final ServiceRouteRepository serviceRouteRepository;
    private final RouteConfigRepository routeConfigRepository;
    private final RouteRepository routeRepository;

    public void initialize() {

        log.info("Initializing MongoDB...");

        // Crear usuario admin solo si no hay usuarios
        userRepository.count().subscribe(count -> {

                if (count > 0)
                    return;

                User user = new User();
                user.setEmail("admin@biagab.com");
                user.setPassword("admin");
                user.setId(UUID.randomUUID().toString());
                user.setApiKey(UUID.randomUUID().toString());
                userRepository.save(user).subscribe();

                RouteConfig routeConfig = new RouteConfig();
                routeConfig.setId(user.getApiKey());
                routeConfig.setPath("/api/visitors");
                routeConfig.setPattern("/api/visitors/(.*)");
                routeConfig.setReplacement("/$1");
                routeConfig.setServiceUrl("http://localhost:8081");
                routeConfig.setApiKey(user.getApiKey());
                routeConfigRepository.save(routeConfig).subscribe();

                routeConfig = new RouteConfig();
                routeConfig.setId(UUID.randomUUID().toString());
                routeConfig.setPath("/api/routing");
                routeConfig.setPattern("/api/routing/(.*)");
                routeConfig.setReplacement("/$1");
                routeConfig.setServiceUrl("http://localhost:8082");
                routeConfig.setApiKey(routeConfig.getApiKey());
                routeConfigRepository.save(routeConfig).subscribe();
        });

        serviceRouteRepository.count().subscribe(count -> {

            if (count > 0)
                return;

            ServiceRoute serviceRoute = new ServiceRoute();
            serviceRoute.setId(UUID.randomUUID().toString());
            serviceRoute.setPath("/api/visitors/");
            serviceRoute.setUrl("http://localhost:8081");
            serviceRoute.setApiKey(serviceRoute.getId());
            serviceRouteRepository.save(serviceRoute).subscribe();

            serviceRoute = new ServiceRoute();
            serviceRoute.setId(UUID.randomUUID().toString());
            serviceRoute.setPath("/api/routing/");
            serviceRoute.setUrl("http://localhost:8082");
            serviceRoute.setApiKey(serviceRoute.getId());
            serviceRouteRepository.save(serviceRoute).subscribe();

        });


        routeRepository.count().subscribe(count -> {

                    if (count > 0)
                        return;

                    log.info("Creating default API routes...");

                    List<Route> routes = new ArrayList<>();

                    Route route = new Route();
                    route.setId(UUID.randomUUID().toString());
                    route.setPath("/api/visitors/**");
                    route.setMethod("GET");
                    route.setUri("http://localhost:8081");
                    route.setRewritePath("/api/visitors/(.*)", "/$1");
                    route.setApiKey(route.getId());
                    routes.add(route);

                    route = new Route();
                    route.setId(UUID.randomUUID().toString());
                    route.setPath("/get");
                    route.setMethod("GET");
                    route.setUri("https://httpbin.org/get");
                    route.setRewritePath("/get/(.*)", "/$1");
                    route.setApiKey(route.getId());
                    routes.add(route);

                    route = new Route();
                    route.setId(UUID.randomUUID().toString());
                    route.setPath("/api/routing/**");
                    route.setMethod("GET");
                    route.setUri("http://localhost:8082");
                    route.setRewritePath("/api/routing/(.*)", "/$1");
                    route.setApiKey(route.getId());
                    routes.add(route);

                    routeRepository.saveAll(routes).subscribe();
                });


        log.info("MongoDB initialized");

    }
}
