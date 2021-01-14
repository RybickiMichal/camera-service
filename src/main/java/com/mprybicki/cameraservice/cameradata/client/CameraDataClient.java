package com.mprybicki.cameraservice.cameradata.client;

import com.mprybicki.cameraservice.common.model.PanTiltZoom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("gateway-service")
public interface CameraDataClient {

    @GetMapping("/pan-tilt-zoom")
    PanTiltZoom getActualPanTiltZoom(@RequestHeader("Target") String target);
}

