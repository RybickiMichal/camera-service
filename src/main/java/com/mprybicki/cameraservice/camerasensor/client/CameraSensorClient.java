package com.mprybicki.cameraservice.camerasensor.client;

import com.mprybicki.cameraservice.common.model.Camera;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@FeignClient("sensor-service")
public interface CameraSensorClient {

    @GetMapping("/camera-sensors/active")
    List<Camera> getActiveSensors();

    @GetMapping("/camera/not-registered-to-camera-service")
    Optional<Camera> getSensorWithoutRegisteredService();

    @PostMapping("/camera/register-service/{cameraSensorId}/{cameraServicePort}")
    void registerCameraServiceToCameraSensor(@PathVariable String cameraSensorId, @PathVariable int cameraServicePort);
}
