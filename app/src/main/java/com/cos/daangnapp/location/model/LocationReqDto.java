package com.cos.daangnapp.location.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationReqDto {
    private double longitude;
    private double  latitude;

    @Override
    public String toString() {
        return "LocationReqDto{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
