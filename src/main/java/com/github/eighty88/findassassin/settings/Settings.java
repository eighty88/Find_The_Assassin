package com.github.eighty88.findassassin.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class Settings {
    public Settings() {
        this.itemStacks = new ItemStacks();
    }

    public ItemStacks itemStacks;

    Inventory inventory = Bukkit.createInventory(null, 36, ChatColor.GOLD + "設定");

    boolean isSetting = false;

    public int Assassin = 0,
            Millionaire = 0,
            Maid = 0,
            Butler = 0,
            Servant = 0,
            FakeMaid = 0;

    public void displayGUI(Player player) {
        if(!isSetting) {
            setInventory();
            player.openInventory(inventory);
            isSetting = true;
        } else {
            player.sendMessage(ChatColor.RED + "他のプレイヤーがすでに設定中です！");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0);
        }
    }

    private void setInventory() {
        int PlayerCount = 0;
        for(Player player:Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() != GameMode.SPECTATOR) PlayerCount++;
        }
        Millionaire = 1;
        if(PlayerCount >= 1) {
            PlayerCount--;
            Assassin = (int) Math.ceil(PlayerCount / 5.0);
            PlayerCount = PlayerCount - Assassin;
            if(PlayerCount >= 1) {
                Maid = Assassin;
                PlayerCount = PlayerCount - Maid;
            }
            if(PlayerCount >= 1) {
                Butler = 1;
                PlayerCount--;
            }
            if(PlayerCount >= 1) {
                FakeMaid = 1;
                PlayerCount--;
            }

            Servant = PlayerCount;
        }

        inventory.setItem(10, itemStacks.Millionaire(Millionaire));
        inventory.setItem(11, itemStacks.Maid(Maid));
        inventory.setItem(12, itemStacks.Butler(Butler));
        inventory.setItem(13, itemStacks.Assassin(Assassin));
        inventory.setItem(14, itemStacks.FakeMaid(FakeMaid));

        inventory.setItem(16, itemStacks.Servant(Servant));

        inventory.setItem(28, itemStacks.End());
        inventory.setItem(34, itemStacks.Start());
    }

    public void setInv() {
        inventory.setItem(10, itemStacks.Millionaire(Millionaire));
        inventory.setItem(11, itemStacks.Maid(Maid));
        inventory.setItem(12, itemStacks.Butler(Butler));
        inventory.setItem(13, itemStacks.Assassin(Assassin));
        inventory.setItem(14, itemStacks.FakeMaid(FakeMaid));

        inventory.setItem(16, itemStacks.Servant(Servant));
    }

    public void onCloseGUI() {
        isSetting = false;
    }

    public int Set(InventoryAction action, int amount, Player player) {
        if (action == InventoryAction.PICKUP_ALL) {
            if (Servant > 0) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1);
                amount++;
                Servant--;
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0);
            }
        } else {
            if (amount > 0) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1);
                amount--;
                Servant++;
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0);
            }
        }
        return amount;
    }
}
