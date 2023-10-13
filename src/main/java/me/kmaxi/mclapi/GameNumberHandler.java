package me.kmaxi.mclapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameNumberHandler {


    public static int getEventId() {
        try {
            // Define the URL
            String apiUrl = "https://mclegends.co.uk/api/event/";

            // Create a URL object
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Close the reader and the connection
                reader.close();
                connection.disconnect();

                // Parse the JSON response using GSON
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                if (jsonObject.get("event_id") == null || jsonObject.get("event_id").getAsString().equals("null") ) {
                    return 0;
                }

                return jsonObject.get("event_id").getAsInt();
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
