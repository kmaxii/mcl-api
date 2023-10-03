package me.kmaxi.mclapi.commands;

import me.kmaxi.mclapi.GameNumberHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ApiCommands  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Please supply a command");
            return true;
        }

        if (!sender.isOp()) {
            return false;
        }

        return switch (args[0].toLowerCase()) {
            case "set_stat_entry" -> SetStatEntryCommand.onCommand(sender, args);
            case "get_stat_entry" -> GetStatEntryCommand.onCommand(sender, args);
            case "reset_game_number" -> GameNumberHandler.resetNumber();
            case "incremement_game_number" -> GameNumberHandler.increaseNumber();
            case "set_winner" -> SetWinnerCommand.onCommand(sender, args);
            default -> false;
        };
    }
}
