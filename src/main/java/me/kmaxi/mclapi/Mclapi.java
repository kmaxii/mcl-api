package me.kmaxi.mclapi;

import me.kmaxi.mclapi.commands.ApiCommands;
import me.kmaxi.mclapi.commands.SetStatEntryCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mclapi extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("api").setExecutor(new ApiCommands());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
