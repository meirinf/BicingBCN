package com.example.a53638138e.bicingbcn;

import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 53638138e on 31/01/17.
 */

public class ApiBici {


    private static String url = "http://wservice.viabicing.cat/v2/stations";
    private static ArrayList<EstacionesBici> estacionesBicis = new ArrayList<>();

    public static ArrayList<EstacionesBici> getBicing() {

        //Aqui pasa los parametros de configuración categoria y nombre de receta
        Uri builtUri = Uri.parse(url)
                .buildUpon()
                .build();

        String urls = builtUri.toString();

        try {
            String JsonResponse = HttpUtils.get(urls);

            JSONObject data = new JSONObject(JsonResponse);
            JSONArray jsonRecetas = data.getJSONArray("stations");
            for (int i = 0; i < jsonRecetas.length(); i++) {

                EstacionesBici estaciones  = new EstacionesBici();
                JSONObject object = jsonRecetas.getJSONObject(i);

                estaciones.setIdEstacion(object.getInt("id"));
                estaciones.setLat(object.getDouble("latitude"));
                estaciones.setLon(object.getDouble("longitude"));
                estaciones.setStreetName(object.getString("streetName"));
                estaciones.setStreetNumber(object.getInt("streetNumber"));
                estaciones.setAltitude(object.getInt("altitude"));
                estaciones.setSlots(object.getInt("slots"));
                estaciones.setBikes(object.getInt("bikes"));
                if(object.getString("status").equalsIgnoreCase("OPN")){
                    estaciones.setStatus(true);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return estacionesBicis;
    }

}
