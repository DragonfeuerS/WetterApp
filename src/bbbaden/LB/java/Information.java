/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbbaden.LB.java;

import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.Dialog;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author serge
 */
public class Information {
    String temp;
    String cityData;
    String wetter;
    String city;
    public void refresh(){
        Location position = LocationManager.getLocationManager().getCurrentLocationSync();
        String lat = Double.toString(position.getLatitude());
        String lon = Double.toString(position.getLongitude());
        String myUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=891ce0a8cb808764ee9ab6a0bbb52187";
        Response<Map> jsonData = Rest.get(myUrl).getAsJsonMap();

        Map tempData = (Map) jsonData.getResponseData().get("main");
        double temperature = (double)tempData.get("temp");
        double cTemp = temperature -273.15;
        int l = (int) cTemp;
        temp = Integer.toString(l);
        
        
        cityData = (String) jsonData.getResponseData().get("name");
        
        Map wetterData = (Map) ((ArrayList) jsonData.getResponseData().get("weather")).get(0);
        wetter = wetterData.get("main").toString();
    }
    
    public void cityWetter(){
        String myUrl = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=891ce0a8cb808764ee9ab6a0bbb52187";
        Response<Map> jsonData = Rest.get(myUrl).getAsJsonMap();
        
        if(jsonData.getResponseCode() == 200){
            Map tempData = (Map) jsonData.getResponseData().get("main");
            double temperature = (double)tempData.get("temp");
            double cTemp = temperature -273.15;
            int l = (int) cTemp;
            temp = Integer.toString(l);
        
            Map wetterData = (Map) ((ArrayList) jsonData.getResponseData().get("weather")).get(0);
            wetter = wetterData.get("main").toString();
        }else{
            Dialog.show("Error", jsonData.getResponseCode() + ": " + jsonData.getResponseErrorMessage(), "ok","");
        }
        
    }
    
    public String getTemp() {
        return temp;
    }

    public String getCityData() {
        return cityData;
    }

    public String getWetter() {
        return wetter;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
