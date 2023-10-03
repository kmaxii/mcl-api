package me.kmaxi.mclapi.commands;

import me.kmaxi.mclapi.EventStatsHandler;
import org.bukkit.command.CommandSender;

public class SetStatEntryCommand {


    public static boolean onCommand(CommandSender sender, String[] args) {


        if (args.length != 4) {
            sender.sendMessage("Invalid arguments. Usage: /api set_stat_entry <player_uuid> <stat_type> <value>");
            return true;
        }

        String playerUuid = args[1];

        String statType = args[2];
        String valeAsString = args[3];

        int value;

        if (valeAsString.matches("[0-9]+")) {
            value = Integer.parseInt(valeAsString);
        } else {
            sender.sendMessage("The value contains non-numeric characters.");
            return false;
        }


        EventStatsHandler.updateStat(playerUuid, statType, value);


        sender.sendMessage("Updated stats");

        return true;
    }
}
