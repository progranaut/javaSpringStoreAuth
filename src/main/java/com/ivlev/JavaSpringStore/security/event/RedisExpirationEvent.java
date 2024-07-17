package com.ivlev.JavaSpringStore.security.event;

import com.ivlev.JavaSpringStore.security.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handleRedisKeyExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event) {
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();

        if (expiredRefreshToken == null) {
            throw new RuntimeException("Refresh token is null");
        }

        log.info("Refresh token with key={} has expired! Refresh token is: {}", expiredRefreshToken.getId(),
                expiredRefreshToken.getToken());
    }

}
