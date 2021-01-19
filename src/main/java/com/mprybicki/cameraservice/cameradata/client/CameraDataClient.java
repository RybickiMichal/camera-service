package com.mprybicki.cameraservice.cameradata.client;

import com.mprybicki.cameraservice.common.model.PositionData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("gateway-service")
public interface CameraDataClient {

    @GetMapping("/position-data")
    PositionData getPositionData(@RequestHeader("Target") String target, @RequestHeader("Authorization") String authorizationHeader);
}

