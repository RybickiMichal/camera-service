package com.mprybicki.cameraservice.cameradata.controller;

import com.mprybicki.cameraservice.cameradata.service.CameraDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CameraDataController {

    CameraDataService cameraDataService;

    @GetMapping(value = "/pan-tilt-zoom")
    public String getActualPanTiltZoom() {
        return cameraDataService.actualiseCamerasData();
    }
}
