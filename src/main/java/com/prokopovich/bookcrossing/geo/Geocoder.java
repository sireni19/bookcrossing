package com.prokopovich.bookcrossing.geo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Geocoder {

    private static final String GEOCODING_RESOURCE = "https://geocode-maps.yandex.ru/1.x/";
    private static final String API_KEY = "d8e228fd-f63a-4791-b07d-d8a043035416";

    public String GeocodeSync(String query) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?apikey=" + API_KEY + "&geocode=" + encodedQuery + "&format=json";

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        return (String) geocodingResponse.body();
    }

    public String extractCoordinates(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJsonNode = objectMapper.readTree(response);
        JsonNode geoObjectCollection = responseJsonNode.get("response").get("GeoObjectCollection");
        JsonNode featureMember = geoObjectCollection.get("featureMember");
        String pos="";
        for (JsonNode item : featureMember) {
            JsonNode geoObject = item.get("GeoObject");
            JsonNode metaData = geoObject.get("metaDataProperty").get("GeocoderMetaData");
            String text = metaData.get("text").asText();
            JsonNode point = geoObject.get("Point");
            pos= point.get("pos").asText();//latitude and longitude


        }
        return pos;
    }
}

