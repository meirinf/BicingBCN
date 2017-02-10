package com.example.a53638138e.bicingbcn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 53638138e on 31/01/17.
 */

public class EstacionesBici  implements Serializable {

    //Variables

    int idEstacion; //id
    double lat;  //Latitud
    double lon; //Longitud
    String streetName; //Nombre de calle
    int streetNumber; //Numero
    int altitude; //Altitud
    int slots;   //espacios disponibles
    int bikes;   //Bicis disponibles
    ArrayList<Integer> nearbyStations; //Estaciones cercanas
    boolean status;  //Estado de la estacion cerrada/abierta
    String type;


    //Constructors

    public EstacionesBici(int idEstacion,String type, double lat, double lon, String streetName, int streetNumber, int slots, int altitude, int bikes, ArrayList<Integer> nearbyStations, boolean status) {
        this.idEstacion = idEstacion;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.slots = slots;
        this.altitude = altitude;
        this.bikes = bikes;
        this.nearbyStations = nearbyStations;
        this.status = status;
    }

    public EstacionesBici() {
    }


    //Getters


    public String getType() {
        return type;
    }

    public int getIdEstacion() {
        return idEstacion;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getSlots() {
        return slots;
    }

    public int getBikes() {
        return bikes;
    }

    public ArrayList<Integer> getNearbyStations() {
        return nearbyStations;
    }

    public boolean isStatus() {
        return status;
    }


    //Setters


    public void setIdEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public void setBikes(int bikes) {
        this.bikes = bikes;
    }

    public void setNearbyStations(ArrayList<Integer> nearbyStations) {
        this.nearbyStations = nearbyStations;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
