package com.mprybicki.cameraservice.scheduler;

import com.mprybicki.cameraservice.camerasensor.service.RegistrationService;
import com.mprybicki.cameraservice.common.model.Camera;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
@Slf4j
public class RegistrationScheduler {

    RegistrationService registrationService;
    private ServerProperties serverProperties;

    //TODO move time to properties
    @Scheduled(fixedRate = 1000 * 60 * 2)
    public void registerToNewSensorIfItIsPossible() {
        Optional<Camera> camera = registrationService.getSensorWithoutRegisteredService();
        if (camera.isPresent()) {
            registrationService.registerCameraServiceToCameraSensor(camera.get().getId(), serverProperties.getPort());
            log.info("Registered to new sensor: " + camera.get().toString());
        }
    }
}

