package com.github.eighty88.findassassin;

import com.github.eighty88.findassassin.player.FTAPlayer;
import com.github.eighty88.findassassin.player.RoleType;
import com.github.eighty88.findassassin.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GameController {
    private final Settings settings;

    public boolean isStarted = false;

    public GameController(FindAssassin findAssassin) {
        this.settings = findAssassin.getSettings();
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
        isStarted = false;

        if(winner != RoleType.None)
            for(Player player: Bukkit.getOnlinePlayers()) {
                player.getInventory().clear();
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

        broadcast(Arrays.asList(
                ChatColor.GREEN + "-----=====" + ChatColor.UNDERLINE + "今回の役職" + ChatColor.RESET + ChatColor.GREEN + "=====-----",
                ChatColor.DARK_RED.toString() + ChatColor.BOLD + "暗殺者",
                ChatColor.DARK_RED + Assassins.toString(),
                ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "偽メイド",
                ChatColor.LIGHT_PURPLE + FakeMaids.toString(),
                ChatColor.AQUA.toString() + ChatColor.BOLD + "執事",
                ChatColor.AQUA + Butlers.toString(),
                ChatColor.YELLOW.toString() + ChatColor.BOLD + "メイド",
                ChatColor.YELLOW + Maids.toString(),
                ChatColor.GREEN.toString() + ChatColor.BOLD + "富豪",
                ChatColor.GREEN + Millionaire.toString(),
                ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "使用人",
                ChatColor.DARK_GREEN.toString() + ServantName,
                ChatColor.GREEN.toString() + ChatColor.STRIKETHROUGH + "=============================="
        ));

        FTAPlayer.RefreshPlayers();
    }


    public void decideEnd() {
        int assassin = 0;
        for(FTAPlayer player: FTAPlayer.getFTAPlayers()) {
            if (!player.isDead()) {
                if (player.getRole() == RoleType.Assassin) {
                    assassin++;
                }
            } else if (player.getRole() == RoleType.Millionaire) {
                end(RoleType.Assassin);
                return;
            }
        }
        if(assassin == 0)
            end(RoleType.Millionaire);
    }

    private void LotteryRole() {
        FTAPlayer millionaire;
        int Servant = 0;
        List<FTAPlayer> TempList = new ArrayList<>();
        for(FTAPlayer ftaPlayer: FTAPlayer.getFTAPlayers()) {
            if(ftaPlayer.getPlayer().getGameMode() != GameMode.SPECTATOR)
                TempList.add(ftaPlayer);
        }
        StringBuilder TempAssassin = new StringBuilder();
        Collections.shuffle(TempList);
        for(int i = settings.Assassin;i>0;i--) {
            TempList.get(0).setRole(RoleType.Assassin);
            TempAssassin.append(TempList.get(0).getPlayer().getName()).append(" ");
            TempList.remove(0);
            Collections.shuffle(TempList);
        }
        SetRole(settings.Maid, TempList, RoleType.Maid);
        SetRole(settings.Butler, TempList, RoleType.Butler);
        SetRole(settings.FakeMaid, TempList, RoleType.FakeMaid);
        millionaire = TempList.get(0);
        SetRole(settings.Millionaire, TempList, RoleType.Millionaire);
        for(FTAPlayer ftaPlayer:TempList) {
            Servant++;
            ftaPlayer.setRole(RoleType.Servant);
        }

        broadcast(Arrays.asList(
                "役職構成:",
                "  暗 殺 者: " + settings.Assassin,
                "  偽メイド: " + settings.FakeMaid,
                "  富    豪: " + settings.Millionaire,
                "  執    事: " + settings.Butler,
                "  メ イ ド: " + settings.Maid,
                "  使 用 人: " + Servant, ""
        ));

        for(FTAPlayer player:FTAPlayer.getFTAPlayers()) {
            player.getPlayer().getInventory().clear();
            player.sendMessage("あなたは" + player.getRole().toString() + ChatColor.WHITE + "です。");
            player.addItem(new ItemStack(Material.WOODEN_SWORD));
            player.getPlayer().sendTitle("あなたの役職: " + player.getRole().toString(), "", 0, 50, 50);
            switch (player.getRole()) {
                case Assassin:
                    player.sendMessage("仲間は " + TempAssassin + "です。");
                    player.addItem(ItemStacks.getSword());
                    player.addItem(ItemStacks.getPotion());
                    break;
                case Millionaire:
                    player.addItem(ItemStacks.getSword());
                    player.addItem(new ItemStack(Material.CAKE, 3));
                    player.addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.addItem(ItemStacks.getTorch());
                    break;

                case Butler:
                    player.sendMessage("富豪は " + Objects.requireNonNull(millionaire).getPlayer().getName() + "です。");
                case Servant:
                    player.addItem(ItemStacks.getDummySword());
                    break;
                case FakeMaid:
                case Maid:
                    player.addItem(ItemStacks.getBow());
                    player.addItem(new ItemStack(Material.ARROW, 2));
                    break;
            }
        }
    }

    public void broadcast(List<String> list) {
        for(String string: list) {
            Bukkit.broadcastMessage(string);
        }
    }

    public void SetRole(int amount, List<FTAPlayer> list, RoleType roleType) {
        for(int i = amount;i>0;i--) {
            list.get(0).setRole(roleType);
            list.remove(0);
            Collections.shuffle(list);
        }
    }
}
