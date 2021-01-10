package com.mprybicki.cameraservice.camerasensor.repository;

import com.mprybicki.cameraservice.common.model.Camera;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraSensorRepository extends CrudRepository<Camera, String> {
}
