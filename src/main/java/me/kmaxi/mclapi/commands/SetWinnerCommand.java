package me.kmaxi.mclapi.commands;

import com.google.gson.JsonObject;
import me.kmaxi.mclapi.ApiKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetWinnerCommand {

    public static boolean onCommand(CommandSender sender, String[] args) {


        if (args.length != 2) {
            sender.sendMessage("Invalid arguments. Usage:/api set_winner <team_number>");
            return true;
        }

        String teamNumberString = args[1];

        int teamNumber;

        if (teamNumberString.matches("[0-9]+")) {
            teamNumber = Integer.parseInt(teamNumberString);
        } else {
            sender.sendMessage("The value contains non-numeric characters.");
            return false;
        }

        sendWinner(teamNumber);
        return true;
    }

    private static void sendWinner(int teamNumber) {
        // Create a JSON object with the "winner" field using Gson
        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("token", ApiKey.winnerKey);
        jsonPayload.addProperty("winner", teamNumber);

        // Define the URL
        String url = "https://mclegends.co.uk/api/event/";

        try {
            // Create a URL object
            URL apiUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set the HTTP request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert the JSON payload to a byte array
            byte[] postData = jsonPayload.toString().getBytes("UTF-8");

            // Get the output stream of the connection and write the JSON data
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Read the response content
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendWinner(277);
    }
}
