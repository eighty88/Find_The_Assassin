package com.github.eighty88.findassassin;

import com.github.eighty88.findassassin.player.FTAPlayer;
import com.github.eighty88.findassassin.player.RoleType;
import com.github.eighty88.findassassin.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class FindAssassin extends JavaPlugin {

    private Settings settings;

    private GameController gameController;

    @Override
    public void onEnable() {

        getLogger().info("|====================");
        getLogger().info("|  Loading Find The Assassin  ");
        getLogger().info("|                    ");
        getLogger().info("|  By eight_y_88     ");
        getLogger().info("|  Version: " + this.getDescription().getVersion());
        getLogger().info("|  Website:          ");
        getLogger().info("|   " + this.getDescription().getWebsite());
        getLogger().info("|  For 1.14～");
        getLogger().info("|                    ");
        getLogger().info("|====================");

        settings = new Settings();
        gameController = new GameController(this);

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        FTAPlayer.RefreshPlayers();
    }

    @Override
    public void onDisable() {
        gameController.end(RoleType.None);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("findtheassassin") || command.getName().equals("fta")) {
            if(sender instanceof Player) {
                settings.displayGUI((Player) sender);
            }
        }
        return false;
    }

    public Settings getSettings() {
        return settings;
    }

    public GameController getGameController() {
        return this.gameController;
    }
}
