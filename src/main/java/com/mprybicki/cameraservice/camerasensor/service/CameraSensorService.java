package com.mprybicki.cameraservice.camerasensor.service;

import com.google.gson.Gson;
import com.mprybicki.cameraservice.camerasensor.client.CameraSensorClient;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.model.Camera;
import com.mprybicki.cameraservice.common.model.CameraDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class CameraSensorService {

    private CameraSensorRepository cameraSensorRepository;
    private CameraSensorValidationService cameraSensorValidationService;
    private Gson gson;

    public void updateSensor(String message) {
        CameraDTO cameraDTO = gson.fromJson(message, CameraDTO.class);
        switch (cameraDTO.getSensorOperation()) {
            case REGISTERED:
                registerSensor(cameraDTO.getCamera());
                break;
            case UNREGISTERED:
                unregisterSensor(cameraDTO.getCamera());
                break;
            case UPDATED:
                updateSensor(cameraDTO.getCamera());
                break;
        }
    }

    private void registerSensor(Camera cameraSensorToRegister) {
        cameraSensorRepository.save(cameraSensorToRegister);
        log.info("Camera sensor registered " + cameraSensorToRegister);
    }

    private void unregisterSensor(Camera cameraSensorToUnregister) {
        cameraSensorValidationService.validateUnregisterSensor(cameraSensorToUnregister.getId());
        cameraSensorRepository.delete(cameraSensorToUnregister);
        log.info("Camera sensor unregistered " + cameraSensorToUnregister);
    }

    private void updateSensor(Camera newCameraSensor) {
        cameraSensorValidationService.validateUpdateSensor(newCameraSensor);
        cameraSensorRepository.save(newCameraSensor);
        log.info("Camera sensor updated " + newCameraSensor);
    }
}
