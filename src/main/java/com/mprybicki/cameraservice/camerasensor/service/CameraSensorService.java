package com.mprybicki.cameraservice.camerasensor.service;

import com.google.gson.Gson;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorClient;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.model.Camera;
import com.mprybicki.cameraservice.common.model.CameraDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@Service
@Slf4j
public class CameraSensorService {

    private CameraSensorClient cameraSensorClient;
    private CameraSensorRepository cameraSensorRepository;
    private CameraSensorValidationService cameraSensorValidationService;
    private Gson gson;

    @Setter
    private boolean AreSensorsDownloaded = false;

    public CameraSensorService(CameraSensorClient cameraSensorClient, CameraSensorRepository cameraSensorRepository, CameraSensorValidationService cameraSensorValidationService, Gson gson) {
        this.cameraSensorClient = cameraSensorClient;
        this.cameraSensorRepository = cameraSensorRepository;
        this.cameraSensorValidationService = cameraSensorValidationService;
        this.gson = gson;
    }


    @Retryable(value = ConnectException.class, maxAttempts = 89280, backoff = @Backoff(delay = 30000))
    public List<Camera> getActiveCameraSensors() {
        return cameraSensorClient.getActiveSensors();
    }

    public void updateSensor(String message) {
        CameraDTO cameraDTO = gson.fromJson(message, CameraDTO.class);

        if (AreSensorsDownloaded) {
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
        } else {
            cameraSensorRepository.deleteAll();
            List<Camera> sensors = getActiveCameraSensors();
            log.info("successfully downloaded sensors");

            setAreSensorsDownloaded(true);
            cameraSensorRepository.saveAll(sensors);
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
