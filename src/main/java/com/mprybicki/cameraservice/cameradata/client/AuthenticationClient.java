package com.mprybicki.cameraservice.cameradata.client;

import com.mprybicki.cameraservice.common.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface AuthenticationClient {

    @PostMapping("/register")
    void registerUser(@RequestBody User user);

    @PostMapping("/authenticate")
    String loginUser(@RequestBody User user);
}

