package com.mprybicki.cameraservice.camerasensor.service;

import com.mprybicki.cameraservice.camerasensor.client.CameraSensorClient;
import com.mprybicki.cameraservice.common.model.Camera;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RegistrationService {

    private CameraSensorClient cameraSensorClient;


    //TODO write tests
    public Optional<Camera> getSensorWithoutRegisteredService() {
        return cameraSensorClient.getSensorWithoutRegisteredService();
    }

    public void registerCameraServiceToCameraSensor(String cameraSensorId, int cameraServicePort) {
        cameraSensorClient.registerCameraServiceToCameraSensor(cameraSensorId, cameraServicePort);
    }

}
