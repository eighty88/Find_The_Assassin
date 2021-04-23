package com.github.eighty88.findassassin;

import com.github.eighty88.findassassin.player.FTAPlayer;
import com.github.eighty88.findassassin.player.RoleType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameController {
    private final FindAssassin findAssassin;

    public boolean isStarted = false;

    public GameController(FindAssassin findAssassin) {
        this.findAssassin = findAssassin;
    }

    public void start() {
        LotteryRole();
        isStarted = true;
        for(Player player:Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() != GameMode.SPECTATOR)
                player.setGameMode(GameMode.ADVENTURE);
        }
    }

    public void end(RoleType winner) {
        if(winner != RoleType.None)
            for(Player player: Bukkit.getOnlinePlayers()) {
                player.sendTitle(winner.toString() + ChatColor.WHITE + "の勝利", "", 0, 50, 50);
            }

        StringBuilder Assassins = new StringBuilder(),
                Maids = new StringBuilder(),
                Butlers = new StringBuilder(),
                FakeMaids = new StringBuilder(),
                Millionaire = new StringBuilder(),
                ServantName = new StringBuilder();

        for(FTAPlayer ftaPlayer:FTAPlayer.getFTAPlayers()) {
            switch (ftaPlayer.getRole()) {
                case Maid:
                    Maids.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
                case Butler:
                    Butlers.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
                case Servant:
                    ServantName.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
                case Assassin:
                    Assassins.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
                case FakeMaid:
                    FakeMaids.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
                case Millionaire:
                    Millionaire.append(ftaPlayer.getPlayer().getName()).append(" ");
                    break;
            }
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "-----=====" + ChatColor.UNDERLINE + "今回の役職" + ChatColor.RESET + ChatColor.GREEN + "=====-----");
        Bukkit.broadcastMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "暗殺者");
        Bukkit.broadcastMessage(ChatColor.DARK_RED + Assassins.toString());
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "偽メイド");
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + FakeMaids.toString());
        Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "執事");
        Bukkit.broadcastMessage(ChatColor.AQUA + Butlers.toString());
        Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "メイド");
        Bukkit.broadcastMessage(ChatColor.YELLOW + Maids.toString());
        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "富豪");
        Bukkit.broadcastMessage(ChatColor.GREEN + Millionaire.toString());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "使用人");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + ServantName);
        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.STRIKETHROUGH + "==============================");

        FTAPlayer.RefreshPlayers();
    }


    public void decideEnd() {
        int assassin = 0;
        for(FTAPlayer player: FTAPlayer.getFTAPlayers()) {
            if(!player.isDead()) {
                if(player.getRole() == RoleType.Assassin)
                    assassin++;
            } else {
                if(player.getRole() == RoleType.Millionaire) {
                    end(RoleType.Assassin);
                    return;
                }
            }
        }
        if(assassin == 0)
            end(RoleType.Millionaire);
    }

    private void LotteryRole() {
        FTAPlayer millionaire = null;
        List<FTAPlayer> TempList = new ArrayList<>();
        for(FTAPlayer ftaPlayer: FTAPlayer.getFTAPlayers()) {
            if(ftaPlayer.getPlayer().getGameMode() != GameMode.SPECTATOR)
                TempList.add(ftaPlayer);
        }
        StringBuilder TempAssassin = new StringBuilder();
        Collections.shuffle(TempList);
        for(int i = findAssassin.getSettings().Assassin;i>0;i--) {
            TempList.get(0).setRole(RoleType.Assassin);
            TempAssassin.append(TempList.get(0).getPlayer().getName()).append(" ");
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        for(int i = findAssassin.getSettings().Maid;i>0;i--) {
            TempList.get(0).setRole(RoleType.Maid);
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        for(int i = findAssassin.getSettings().Butler;i>0;i--) {
            TempList.get(0).setRole(RoleType.Butler);
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        for(int i = findAssassin.getSettings().FakeMaid;i>0;i--) {
            TempList.get(0).setRole(RoleType.FakeMaid);
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        for(int i = findAssassin.getSettings().Millionaire;i>0;i--) {
            millionaire = TempList.get(0);
            TempList.get(0).setRole(RoleType.Millionaire);
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        for(FTAPlayer ftaPlayer:TempList) {
            ftaPlayer.setRole(RoleType.Servant);
        }

        for(FTAPlayer player:FTAPlayer.getFTAPlayers()) {
            player.getPlayer().sendMessage("あなたは" + player.getRole().toString() + ChatColor.WHITE + "です。");
            player.getPlayer().sendTitle("あなたの役職: " + player.getRole().toString(), "", 0, 50, 50);
            switch (player.getRole()) {
                case Assassin:
                    player.getPlayer().sendMessage("仲間は " + TempAssassin + "です。");
                    player.getPlayer().getInventory().addItem(ItemStacks.getSword());
                    player.getPlayer().getInventory().addItem(ItemStacks.getPotion());
                    break;
                case Millionaire:
                    player.getPlayer().getInventory().addItem(ItemStacks.getSword());
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.CAKE, 3));
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 3));
                    player.getPlayer().getInventory().addItem(ItemStacks.getTorch());
                    break;
                case Servant:
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                case Butler:
                    player.getPlayer().sendMessage("富豪は " + Objects.requireNonNull(millionaire).getPlayer().getName() + "です。");
                    break;
                case FakeMaid:
                case Maid:
                    player.getPlayer().getInventory().addItem(ItemStacks.getBow());
                    player.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 2));
                    break;
            }
        }
    }
}