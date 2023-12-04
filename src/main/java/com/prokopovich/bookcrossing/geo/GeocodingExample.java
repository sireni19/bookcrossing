package com.prokopovich.bookcrossing.geo;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeocodingExample {

    public static void main(String[] args) throws IOException, InterruptedException {

        Geocoder geocoder = new Geocoder();

        String response = geocoder.GeocodeSync("Москва, улица Новый Арбат, 36");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJsonNode = objectMapper.readTree(response);
        JsonNode geoObjectCollection = responseJsonNode.get("response").get("GeoObjectCollection");
        JsonNode featureMember = geoObjectCollection.get("featureMember");

        for (JsonNode item : featureMember) {
            JsonNode geoObject = item.get("GeoObject");
            JsonNode metaData = geoObject.get("metaDataProperty").get("GeocoderMetaData");
            String text = metaData.get("text").asText();
            JsonNode point = geoObject.get("Point");
            String pos = point.get("pos").asText();//latitude and longitude
            String[] coordinates = pos.split(" ");
            String lat = coordinates[1];
            String lng = coordinates[0];

            System.out.println(text + " is located at " + lat + "," + lng + ".");
        }
    }
}