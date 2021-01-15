package com.mprybicki.cameraservice.camerasensor.service;

import com.mprybicki.cameraservice.camerasensor.client.CameraSensorClient;
import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.util.Optional;

import static com.mprybicki.cameraservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private CameraSensorRepository cameraSensorRepository;

    @Mock
    private ServerProperties serverProperties;

    @Mock
    private CameraSensorClient cameraSensorClient;

    @InjectMocks
    private RegistrationService registrationService;


    @Test
    void shouldNotRegisterToNewSensor() {
        when(cameraSensorClient.getSensorWithoutRegisteredService()).thenReturn(Optional.empty());

        assertThat(registrationService.registerToNewSensorIfItIsPossible()).isEqualTo(false);
    }

    @Test
    void shouldRegisterToNewSensor() {
        when(cameraSensorClient.getSensorWithoutRegisteredService()).thenReturn(Optional.of(correctActiveCamera()));

        assertThat(registrationService.registerToNewSensorIfItIsPossible()).isEqualTo(true);
    }
}