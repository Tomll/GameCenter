package com.gameley.youzi;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

public class GetCity {

    static final String GOOGLE_MAPS_API_KEY = "abcdefg";

    private LocationManager locationManager;
    private Location currentLocation;
    private String city = "全国";

    public GetCity(Context context) {

        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


    }

    /**
     *   * 开始解析
     *  
     */


    public void start() {
        if (currentLocation != null) {
            new Thread() {


                public void run() {
                    String temp = reverseGeocode(currentLocation);
                    if (temp != null && temp.length() >= 2)
                        city = temp;
                }

            }.start();
        } else {
            System.out.println("GetCity.start()未获得location");
        }
    }

    /**
     *   * 获得城市
     *   * @return
     *  
     */


    public String getCity() {
        return city;
    }

    /**
     *   * 通过Google  map api 解析出城市
     *   * @param loc
     *   * @return
     *  
     */


    public String reverseGeocode(Location loc) {
        // http://maps.google.com/maps/geo?q=40.714224,-73.961452&output=json&oe=utf8&sensor=true_or_false&key=your_api_key
        String localityName = "";
        HttpURLConnection connection = null;
        URL serverAddress = null;

       return "";
    }
}

