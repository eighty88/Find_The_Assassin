package com.github.eighty88.findassassin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FTAPlayer {
    Player player;
    RoleType roleType = RoleType.None;
    boolean isDead = false;

    private static final HashMap<Player, FTAPlayer> FTAPlayers = new HashMap<>();

    public FTAPlayer(Player player) {
        this.player = player;
    }

    public RoleType getRole() {
        return roleType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setRole(RoleType roleType) {
        this.roleType = roleType;
    }

    public static FTAPlayer getFTAPlayer(Player player) {
        return FTAPlayers.get(player);
    }

    public static void RefreshPlayers() {
        FTAPlayers.clear();
        for(Player player: Bukkit.getOnlinePlayers()) {
            FTAPlayers.put(player, new FTAPlayer(player));
        }
    }

    public static void registerPlayer(Player player) {
        FTAPlayer tempPlayer = null;
        for(FTAPlayer ftaPlayer: FTAPlayers.values()) {
            if(player.getUniqueId().equals(ftaPlayer.getPlayer().getUniqueId())) {
                tempPlayer = ftaPlayer;
            }
        }
        FTAPlayers.put(player, new FTAPlayer(player));
        FTAPlayer ftaPlayer = FTAPlayers.get(player);
        if(tempPlayer != null) {
            ftaPlayer.setDead(tempPlayer.isDead);
            ftaPlayer.setRole(tempPlayer.getRole());
        }
    }

    public static void unRegisterPlayer(Player player) {
        FTAPlayers.remove(player);
    }

    public static List<FTAPlayer> getFTAPlayers() {
        return new ArrayList<>(FTAPlayers.values());
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean isDead() {
        return isDead;
    }

    public void addItem(ItemStack... stacks) {
        player.getInventory().addItem(stacks);
    }

    public void sendMessage(String str) {
        player.sendMessage(str);
    }
}
