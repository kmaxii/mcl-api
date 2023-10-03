package me.kmaxi.mclapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventStatsHandler {

    public static int getStat(String minecraftId, String statType) {

        try {
            int eventId = GameNumberHandler.getEventId();
            if (eventId == 0){
                return 0;
            }

            ResultSet resultSet = getResultSet(minecraftId, statType, eventId);
            // Check if the query was successful
            if (resultSet.next()) {
                int stat = resultSet.getInt(statType);
                System.out.println("Stat retrieved successfully.");
                return stat;
            } else {
                System.out.println("No rows were retrieved. Minecraft ID or event/game details may not exist.");
                return 0;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ResultSet getResultSet(String minecraftId, String statType, int eventId) throws SQLException {
        Connection connection = DataBaseManager.getInstance().getConnection();

        // Define the SQL query to get the specified statistic
        String getStatQuery = "SELECT " + statType + " FROM event_stats WHERE minecraft_id = ? AND event_id = ? AND game_number = ?";

        // Create a prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(getStatQuery);
        preparedStatement.setString(1, minecraftId);
        preparedStatement.setInt(2, eventId);
        preparedStatement.setInt(3, GameNumberHandler.getGameNumber());

        // Execute the query
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }


    public static void updateStat(String minecraftId, String statType, int value) {
        try {

            int eventId = GameNumberHandler.getEventId();
            if (eventId == 0){
                return;
            }

            PreparedStatement preparedStatement = getUpdatePrepareStatement(minecraftId, statType, value, eventId);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                System.out.println("Stat updated successfully.");
            } else {
                System.out.println("No rows were updated. Minecraft ID or event/game details may not exist.");

                switch (statType) {
                    case "stars" -> insertNewEntry(minecraftId, value, 0, 0);
                    case "kills" -> insertNewEntry(minecraftId, 0, value, 0);
                    case "deaths" -> insertNewEntry(minecraftId, 0, 0, value);
                    default -> {
                    }
                }
            }
            // Close the resources
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement getUpdatePrepareStatement(String minecraftId, String statType, int value, int eventId) throws SQLException {
        Connection connection = DataBaseManager.getInstance().getConnection();

        // Define the SQL query to update the specified statistic
        String updateQuery = "UPDATE event_stats SET " + statType + " = ? " +
                "WHERE minecraft_id = ? AND event_id = ? AND game_number = ?";

        // Create a prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setInt(1, value);
        preparedStatement.setString(2, minecraftId);
        preparedStatement.setInt(3, eventId);
        preparedStatement.setInt(4, GameNumberHandler.getGameNumber());
        return preparedStatement;
    }

    private static void insertNewEntry(String minecraftId, int stars, int kills, int deaths) {
        try {
            int eventId = GameNumberHandler.getEventId();
            if (eventId == 0){
                System.out.println("NOT INSTERING! EVENT ID IS 0");
                return;
            }

            Connection connection = DataBaseManager.getInstance().getConnection();


            // Define the SQL query for inserting a new entry
            String insertQuery = "INSERT INTO event_stats (minecraft_id, event_id, game_number, stars, kills, deaths) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, minecraftId);
            preparedStatement.setInt(2, eventId);
            preparedStatement.setInt(3, GameNumberHandler.getGameNumber());
            preparedStatement.setInt(4, stars);
            preparedStatement.setInt(5, kills);
            preparedStatement.setInt(6, deaths);

            // Execute the insert
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insert was successful
            if (rowsAffected > 0) {
                System.out.println("New entry inserted successfully.");
            } else {
                System.out.println("No rows were inserted.");
            }

            // Close the resources
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
