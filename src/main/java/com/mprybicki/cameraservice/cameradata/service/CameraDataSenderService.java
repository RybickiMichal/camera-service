package com.mprybicki.cameraservice.cameradata.service;


import com.mprybicki.cameraservice.common.model.CameraData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class CameraDataSenderService {

    @Autowired
    private KafkaTemplate<String, CameraData> kafkaTemplate;

    @Value("${kafka.camera.data.topic}")
    private String kafkaTopic;

    public void send(CameraData cameraData) {
        ListenableFuture<SendResult<String, CameraData>> future = kafkaTemplate.send(kafkaTopic, cameraData);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, CameraData> result) {
                log.info("Sent message: " + cameraData.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + cameraData.toString(), ex);
            }
        });
    }
}