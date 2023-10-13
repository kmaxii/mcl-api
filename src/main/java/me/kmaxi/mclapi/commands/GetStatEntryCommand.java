package me.kmaxi.mclapi.commands;

import me.kmaxi.mclapi.EventStatsHandler;
import org.bukkit.command.CommandSender;

import static me.kmaxi.mclapi.commands.SetStatEntryCommand.getInt;

public class GetStatEntryCommand {

    public static boolean onCommand(CommandSender sender, String[] args) {

        if (args.length != 4) {
            sender.sendMessage("Invalid arguments. Usage: /api get_stat_entry <player_uuid> <stat_type> <game number>");
            return true;
        }

        String playerUuid = args[1];
        String statType = args[2];

        int gameNumber = getInt(args[3], sender);
        if (gameNumber == Integer.MAX_VALUE) {
            return true;
        }


        sender.sendMessage(EventStatsHandler.getStat(playerUuid, statType, gameNumber) + "");

        return true;
    }
}
