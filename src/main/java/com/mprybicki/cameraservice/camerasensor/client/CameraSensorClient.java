package com.mprybicki.cameraservice.camerasensor.client;

import com.mprybicki.cameraservice.common.model.Camera;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("sensor-service")
public interface CameraSensorClient {

    @GetMapping("/camera-sensors/active")
    List<Camera> getActiveSensors();
}
