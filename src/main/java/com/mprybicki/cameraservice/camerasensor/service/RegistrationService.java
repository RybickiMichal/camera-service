package com.mprybicki.cameraservice.camerasensor.service;

import com.mprybicki.cameraservice.camerasensor.client.CameraSensorClient;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.model.Camera;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class RegistrationService {

    private CameraSensorClient cameraSensorClient;
    private CameraSensorRepository cameraSensorRepository;
    private ServerProperties serverProperties;

    public boolean registerToNewSensorIfItIsPossible() {
        Optional<Camera> camera = getSensorWithoutRegisteredService();
        if (camera.isPresent()) {
            cameraSensorRepository.save(registerCameraServiceToCameraSensor(camera.get().getId(), serverProperties.getPort()));
            log.info("Registered to new sensor: " + camera.get().toString());
            return true;
        }
        return false;
    }

    private Optional<Camera> getSensorWithoutRegisteredService() {
        return cameraSensorClient.getSensorWithoutRegisteredService();
    }

    private Camera registerCameraServiceToCameraSensor(String cameraSensorId, int cameraServicePort) {
        return cameraSensorClient.registerCameraServiceToCameraSensor(cameraSensorId, cameraServicePort);
    }

}
