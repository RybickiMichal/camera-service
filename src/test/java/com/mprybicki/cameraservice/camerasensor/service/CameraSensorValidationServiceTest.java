package com.mprybicki.cameraservice.camerasensor.service;

import com.mprybicki.cameraservice.camerasensor.repository.CameraSensorRepository;
import com.mprybicki.cameraservice.common.exception.InvalidSensorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mprybicki.cameraservice.camerasensor.sampledata.CameraSampleData.correctActiveCamera;
import static com.mprybicki.cameraservice.camerasensor.sampledata.CameraSampleData.correctInactiveCamera;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CameraSensorValidationServiceTest {

    @Mock
    private CameraSensorRepository cameraSensorRepository;

    @InjectMocks
    private CameraSensorValidationService cameraSensorValidationService;

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> cameraSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Camera Sensor with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndSensorStatusIsInactive() {
        when(cameraSensorRepository.findById(any())).thenReturn(Optional.of(correctInactiveCamera()));

        assertThatThrownBy(() -> cameraSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(cameraSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveCamera()));

        assertDoesNotThrow(() -> cameraSensorValidationService.validateUnregisterSensor(any()));
    }

}