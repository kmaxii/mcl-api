package me.kmaxi.mclapi.commands;

import me.kmaxi.mclapi.EventStatsHandler;
import org.bukkit.command.CommandSender;

public class GetStatEntryCommand {

    public static boolean onCommand(CommandSender sender, String[] args) {

        if (args.length != 3) {
            sender.sendMessage("Invalid arguments. Usage: /api get_stat_entry <player_uuid> <stat_type>");
            return true;
        }

        String playerUuid = args[1];
        String statType = args[2];


        sender.sendMessage(EventStatsHandler.getStat(playerUuid, statType) + "");

        return true;
    }
}
