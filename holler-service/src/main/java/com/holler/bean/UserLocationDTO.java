package com.holler.bean;

import com.holler.holler_dao.util.AddressConverter;

/**
 *
 *
 * @author Abhishek Somani
 *
 */
public class UserLocationDTO {

    private int userId;

    private double lat;

    private double lng;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static String getLocationInCommaSeparatedString(UserLocationDTO userLocationDTO) {
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(String.valueOf(userLocationDTO.getLat())).append(",").append(String.valueOf(userLocationDTO.getLng()));
        return locationBuilder.toString();
    }

    public static String getAddressFromLocation(UserLocationDTO userLocationDTO){
        return AddressConverter.getAddressFromLatLong(getLocationInCommaSeparatedString(userLocationDTO));
    }

}