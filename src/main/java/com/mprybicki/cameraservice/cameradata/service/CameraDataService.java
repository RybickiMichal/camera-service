package com.mprybicki.cameraservice.cameradata.service;

import com.mprybicki.cameraservice.cameradata.repository.CameraDataClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CameraDataService {
    CameraDataClient cameraDataClient;


    //TODO
    public String actualiseCamerasData(){

        return cameraDataClient.getActualPanTiltZoom();
    }
}
