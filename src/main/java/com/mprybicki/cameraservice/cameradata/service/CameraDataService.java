package com.mprybicki.cameraservice.cameradata.service;

import com.mprybicki.cameraservice.cameradata.client.AuthenticationClient;
import com.mprybicki.cameraservice.cameradata.client.CameraDataClient;
import com.mprybicki.cameraservice.cameradata.repository.CameraDataRepository;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.model.CameraData;
import com.mprybicki.cameraservice.common.model.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CameraDataService {

    private AuthenticationClient authenticationClient;
    private CameraDataClient cameraDataClient;
    private CameraSensorRepository cameraSensorRepository;
    private CameraDataSenderService cameraDataSenderService;
    private CameraDataRepository cameraDataRepository;
    private ServerProperties serverProperties;

    @Value("${camera.service.user}")
    private String user;

    @Value("${camera.service.password}")
    private String password;

    private static String token;

    public CameraDataService(AuthenticationClient authenticationClient, CameraDataClient cameraDataClient, CameraSensorRepository cameraSensorRepository, CameraDataSenderService cameraDataSenderService, CameraDataRepository cameraDataRepository, ServerProperties serverProperties) {
        this.authenticationClient = authenticationClient;
        this.cameraDataClient = cameraDataClient;
        this.cameraSensorRepository = cameraSensorRepository;
        this.cameraDataSenderService = cameraDataSenderService;
        this.cameraDataRepository = cameraDataRepository;
        this.serverProperties = serverProperties;
    }

    public void actualiseCamerasData() {
        cameraSensorRepository.findAll().forEach(camera -> {
            if (camera != null && camera.getCameraServicePort().equals(serverProperties.getPort())) {
                CameraData cameraData = null;
                try {
                    cameraData = new CameraData(cameraDataClient.getPositionData("http://" + camera.getIp() + "/position-data", "Bearer " + token),
                            camera.getIp(), new Date());
                } catch (FeignException exception) {
                    log.error("cannot fetch data");
                }
                if (cameraData == null) {
                    login();
                    return;
                }
                if (hasCameraDataChanged(cameraData)) {
                    cameraDataRepository.insert(cameraData);
                    cameraDataSenderService.send(cameraData);
                    log.info("Fetched new camera data " + cameraData.toString());
                }
            }
        });
    }

    private String login() {
        token = null;
        try {
            token = authenticationClient.loginUser(new User(user, password));
        } catch (FeignException exception) {
            log.warn("user is not registered or gateway/user-service is down");
        }
        if (token == null) {
            register();
            return null;
        }
        return token;
    }

    private void register() {
        authenticationClient.registerUser(new User(user, password, List.of("FetchCameraDataRole")));
    }

    private boolean hasCameraDataChanged(CameraData newCameraData) {
        CameraData oldCameraData = cameraDataRepository.findFirstByCameraIpOrderByDataFetchDateDesc(newCameraData.getCameraIp());
        if (oldCameraData == null) {
            return true;
        }
        return !newCameraData.equals(oldCameraData);
    }
}
