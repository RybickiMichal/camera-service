package com.mprybicki.cameraservice.camerasensor.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CameraSensorListenerService {

    private final CameraSensorService cameraSensorService;

    @KafkaListener(topics = "${kafka.camera.sensor.topic}")
    public void listen(String message) {
        cameraSensorService.updateSensor(message);
    }
}
