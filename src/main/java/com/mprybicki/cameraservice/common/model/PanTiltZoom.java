package com.mprybicki.cameraservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class PanTiltZoom {

    @Min(0)
    @Max(360)
    @NotNull
    private Double pan;

    @Min(0)
    @Max(360)
    @NotNull
    private Double tilt;

    @Min(0)
    @NotNull
    private Double zoom;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PanTiltZoom)) {
            return false;
        }
        PanTiltZoom panTiltZoom = (PanTiltZoom) obj;
        return panTiltZoom.getPan().equals(pan)
                && panTiltZoom.getTilt().equals(tilt)
                && panTiltZoom.getZoom().equals(zoom);
    }
}
