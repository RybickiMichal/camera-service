package com.mprybicki.cameraservice.cameradata.scheduler;

import com.mprybicki.cameraservice.cameradata.service.CameraDataService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FetchCameraDataScheduler {

    CameraDataService cameraDataService;

    //TODO move time to properties
    @Scheduled(fixedRate = 2000)
    public void actualiseCameraData() {
        cameraDataService.actualiseCamerasData();
    }
}

