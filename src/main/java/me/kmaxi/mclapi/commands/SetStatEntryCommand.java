package me.kmaxi.mclapi.commands;

import me.kmaxi.mclapi.EventStatsHandler;
import org.bukkit.command.CommandSender;

public class SetStatEntryCommand {


    public static boolean onCommand(CommandSender sender, String[] args) {


        if (args.length != 5) {
            sender.sendMessage("Invalid arguments. Usage: /api set_stat_entry <player_uuid> <stat_type> <value> <game_id>");
            return true;
        }

        String playerUuid = args[1];

        String statType = args[2];
        String valeAsString = args[3];

        String gameIdAsString = args[4];

        int value = getInt(valeAsString, sender);
        if (value == Integer.MAX_VALUE) {
            return true;
        }

        int gameId = getInt(gameIdAsString, sender);
        if (gameId == Integer.MAX_VALUE) {
            return true;
        }


        EventStatsHandler.updateStat(playerUuid, statType, value, gameId);


        sender.sendMessage("Updated stats");

        return true;
    }

    private static int getInt(String s, CommandSender sender) {
        int value;

        if (s.matches("[0-9]+")) {
            value = Integer.parseInt(s);
        } else {
            sender.sendMessage("The value contains non-numeric characters.");
            return Integer.MAX_VALUE;
        }
        return value;
    }
}
