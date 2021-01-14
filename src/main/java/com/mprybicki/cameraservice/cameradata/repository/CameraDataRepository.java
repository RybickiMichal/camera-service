package com.mprybicki.cameraservice.cameradata.repository;

import com.mprybicki.cameraservice.common.model.CameraData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraDataRepository extends MongoRepository<CameraData, String> {

    CameraData findFirstByCameraIpOrderByDataFetchDateDesc(String ip);
}
