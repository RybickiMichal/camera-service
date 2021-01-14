package com.mprybicki.cameraservice.camerasensor.scheduler;

import com.mprybicki.cameraservice.camerasensor.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RegistrationScheduler {

    private RegistrationService registrationService;

    //TODO move time to properties
    @Scheduled(fixedRate = 1000 * 20)
    public void registerToNewSensorIfItIsPossible() {
        registrationService.registerToNewSensorIfItIsPossible();
    }
}

