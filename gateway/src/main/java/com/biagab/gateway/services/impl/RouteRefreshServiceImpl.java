package com.biagab.gateway.services.impl;

import com.biagab.gateway.services.RouteRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RouteRefreshServiceImpl implements RouteRefreshService {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void refreshRoutes() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
