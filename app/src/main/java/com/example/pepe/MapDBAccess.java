package com.example.pepe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapDBAccess {

    // in results set (select * from blah blah blah)
    private void sendGet() throws Exception {
        // verify url
<<<<<<< HEAD
<<<<<<< HEAD
        String url = "http:/localhost:3001/";
=======
        String url = "http:/localhost:3006/";
>>>>>>> 8b1cef663212190d6f26454b244b80bde8cdb264
=======
        String url = "jdbc:mysql://localhost:3001/310project";
>>>>>>> 15861cb5c1b17dc703d7ad19b2b4f4a37ce6c4ce
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod("GET");

        // request handler
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
    }
}
