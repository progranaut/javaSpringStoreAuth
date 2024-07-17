package com.ivlev.JavaSpringStore.security.feign;

import com.ivlev.JavaSpringStore.security.web.dto.SendUserToApp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "client", url = "${feign.url}")
public interface FeignImpl {

    @PostMapping("/users/add-user")
    void sendUser(@RequestBody SendUserToApp sendUserToApp);

}
