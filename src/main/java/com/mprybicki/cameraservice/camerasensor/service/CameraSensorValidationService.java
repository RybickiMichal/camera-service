package com.mprybicki.cameraservice.camerasensor.service;


import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.exception.InvalidSensorException;
import com.mprybicki.cameraservice.common.model.Camera;
import com.mprybicki.cameraservice.common.model.Sensor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mprybicki.cameraservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class CameraSensorValidationService {

    private CameraSensorRepository cameraSensorRepository;


    public void validateUnregisterSensor(String id) {
        Camera sensorToUnregister = cameraSensorRepository.findById(id).orElseThrow(() -> new InvalidSensorException("Camera Sensor with given id doesn't exist"));
        validateIsSensorActive(sensorToUnregister, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(Sensor newSenor) {
        validateIsSensorActive(newSenor, "To unregister sensor try delete endpoint");
    }

    private void validateIsSensorActive(Sensor sensor, String errorMessage) {
        if (sensor.getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
