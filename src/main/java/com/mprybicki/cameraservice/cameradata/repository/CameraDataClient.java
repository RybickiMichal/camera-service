package com.mprybicki.cameraservice.cameradata.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gateway-service")
public interface CameraDataClient {

    @GetMapping("/pan-tilt-zoom")
    String getActualPanTiltZoom();
}

