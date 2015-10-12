package com.holler.holler_dao.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddressConverter {
 /*
  * Geocode request URL. Here see we are passing "json" it means we will get
  * the output in JSON format. You can also pass "xml" instead of "json" for
  * XML output. For XML output URL will be
  * "http://maps.googleapis.com/maps/api/geocode/xml";
  */

    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    /*
     * Here the fullAddress String is in format like
     * "address,city,state,zipcode". Here address means "street number + route"
     * .
     */
    public static GoogleResponse convertToLatLong(String fullAddress) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
        URL url = new URL(URL + "?address="
                + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
        // Open the Connection
        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
        in.close();
        return response;


    }

    public static GoogleResponse convertFromLatLong(String latlongString) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
        URL url = new URL(URL + "?latlng="
                + URLEncoder.encode(latlongString, "UTF-8") + "&sensor=false");
        // Open the Connection
        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
        in.close();
        return response;


    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String getLatLongFromAddress(String address) {
        try {
            StringBuilder latLong = new StringBuilder();
            GoogleResponse res = AddressConverter.convertToLatLong(address);
            if (res.getStatus().equals("OK")) {
                for (Result result : res.getResults()) {
                    latLong.append(result.getGeometry().getLocation().getLat())
                            .append(",")
                            .append(result.getGeometry().getLocation().getLng());
                }
            }
            return latLong.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAddressFromLatLong(String latLong) {
        try{
            String address = "";
            GoogleResponse response = AddressConverter.convertFromLatLong(latLong);
            if (response.getStatus().equals("OK")) {
                address = response.getResults()[0].getFormatted_address();
            }
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Double calculateDistanceUsingLatLong(double lat1, double lon1, double lat2, double lon2){
        return distance(lat1, lon1, lat2, lon2, 'K');
    }

    public static void main(String[] args) throws IOException {

        GoogleResponse res = AddressConverter.convertToLatLong("Laxmi Vrindavan, Pimple Saudagar, Pune, Maharashtra");
        if (res.getStatus().equals("OK")) {
            for (Result result : res.getResults()) {
                System.out.println("Lattitude of address is :" + result.getGeometry().getLocation().getLat());
                System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
                System.out.println("Location is " + result.getGeometry().getLocation_type());
            }
        } else {
            System.out.println(res.getStatus());
        }

        System.out.println("\n");
        GoogleResponse res1 = AddressConverter.convertFromLatLong("18.5743807,73.77763299999999");
        if (res1.getStatus().equals("OK")) {
            for (Result result : res1.getResults()) {
                System.out.println("address is :" + result.getFormatted_address());
            }
        } else {
            System.out.println(res1.getStatus());
        }

        System.out.println(distance(18.5743807, 73.77763299999999, 18.5983628, 73.7929903, 'M') + " Miles\n");
        System.out.println(distance(18.5743807, 73.77763299999999, 18.5983628, 73.7929903, 'K') + " Kilometers\n");


    }


}