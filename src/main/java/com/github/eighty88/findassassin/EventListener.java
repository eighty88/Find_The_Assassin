package com.github.eighty88.findassassin;

import com.github.eighty88.findassassin.player.FTAPlayer;
import com.github.eighty88.findassassin.player.RoleType;
import com.github.eighty88.findassassin.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    FindAssassin findAssassin;
    Settings settings;
    public EventListener(FindAssassin findAssassin) {
        this.findAssassin = findAssassin;
        this.settings = findAssassin.getSettings();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.GOLD + "設定")) {
            if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                switch (e.getSlot()) {
                    case 11:
                        settings.Maid = settings.Set(e.getAction(), settings.Maid, player);
                        break;
                    case 12:
                        settings.Butler = settings.Set(e.getAction(), settings.Butler, player);
                        break;
                    case 13:
                        if (settings.Assassin > 1) {
                            settings.Assassin = settings.Set(e.getAction(), settings.Assassin, player);
                        }
                        break;
                    case 14:
                        settings.FakeMaid = settings.Set(e.getAction(), settings.FakeMaid, player);
                        break;
                    case 28:
                        if (findAssassin.getGameController().isStarted) {
                            findAssassin.getGameController().end(RoleType.None);
                        } else {
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0);
                        }
                        break;
                    case 34:
                        if (settings.Assassin < 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0);
                        } else {
                            findAssassin.getSettings().onCloseGUI();
                            findAssassin.getGameController().start();
                            e.getView().close();
                        }
                        break;
                }
                settings.setInv();
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(ChatColor.GOLD + "設定"))
            settings.onCloseGUI();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(findAssassin.getGameController().isStarted) {
            FTAPlayer.getFTAPlayer(e.getEntity()).setDead(true);
            findAssassin.getGameController().decideEnd();
            e.getEntity().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        FTAPlayer.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if(findAssassin.getGameController().isStarted) {
            if(FTAPlayer.getFTAPlayer(e.getPlayer()).getRole() == RoleType.None) {
                FTAPlayer.unRegisterPlayer(e.getPlayer());
            }
        } else {
            FTAPlayer.unRegisterPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if(FTAPlayer.getFTAPlayer(e.getPlayer()).getRole() == RoleType.Millionaire)
            if(e.getItemDrop().getItemStack().getType() == Material.REDSTONE_TORCH) {
                e.getItemDrop().remove();
                for(FTAPlayer ftaPlayer:FTAPlayer.getFTAPlayers()) {
                    if(ftaPlayer.getRole() == RoleType.Maid || ftaPlayer.getRole() == RoleType.FakeMaid) {
                        ftaPlayer.getPlayer().teleport(e.getPlayer().getLocation());
                    }
                }
            }
    }

    @EventHandler
    public void onPlayerPickUpItem(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            FTAPlayer player = FTAPlayer.getFTAPlayer((Player) e.getEntity());
            switch (player.getRole()) {
                case Assassin:
                    player.getPlayer().getInventory().addItem(ItemStacks.getSword());
                    break;
                case Millionaire:
                case Butler:
                    break;
                case Servant:
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    break;
                case Maid:
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 4));
                case FakeMaid:
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 4));
                    break;
            }
        }
    }
}