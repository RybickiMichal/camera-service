package com.mprybicki.cameraservice.cameradata.service;

import com.mprybicki.cameraservice.cameradata.client.CameraDataClient;
import com.mprybicki.cameraservice.cameradata.repository.CameraDataRepository;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.model.CameraData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
@Slf4j
public class CameraDataService {

    private CameraDataClient cameraDataClient;
    private CameraSensorRepository cameraSensorRepository;
    private CameraDataSenderService cameraDataSenderService;
    private CameraDataRepository cameraDataRepository;
    private ServerProperties serverProperties;


    //TODO validation for non existing sensors
    public void actualiseCamerasData() {
        cameraSensorRepository.findAll().forEach(camera -> {
            if (camera != null && camera.getCameraServicePort().equals(serverProperties.getPort())) {
                CameraData cameraData = new CameraData(cameraDataClient.getPositionData("http://" + camera.getIp() + "/position-data"),
                        camera.getIp(), new Date());
                if (hasCameraDataChanged(cameraData)) {
                    cameraDataRepository.insert(cameraData);
                    cameraDataSenderService.send(cameraData);
                    log.info("add new camera data " + cameraData.toString());
                }
            }
        });
    }

    private boolean hasCameraDataChanged(CameraData newCameraData) {
        CameraData oldCameraData = cameraDataRepository.findFirstByCameraIpOrderByDataFetchDateDesc(newCameraData.getCameraIp());
        if (oldCameraData == null) {
            return true;
        }
        return !newCameraData.equals(oldCameraData);
    }
}
