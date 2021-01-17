package com.mprybicki.cameraservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class CameraData {

    @NotNull
    @Valid
    private PositionData positionData;

    @NotNull
    private String cameraIp;

    @NotNull
    private Date dataFetchDate;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CameraData)) {
            return false;
        }
        CameraData cameraData = (CameraData) obj;
        return cameraData.cameraIp.equals(cameraIp)
                && cameraData.positionData.equals(positionData);
    }
}
