package com.github.eighty88.findassassin.settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemStacks {
    public ItemStack Assassin(int amount) {
        ItemStack Stack = new ItemStack(Material.RED_STAINED_GLASS_PANE, amount);
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.RED + "暗殺者");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.RED + "暗殺者", "",
                "持ち物:",
                "  即死剣 ×1",
                "  透明化ポーション ×2", "",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "クリックで一人追加",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "SHIFT + クリックで一人減らす"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack Servant(int amount) {
        ItemStack Stack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, amount);

        if(amount == 0) {
            Stack.setType(Material.GRAY_STAINED_GLASS_PANE);
            Stack.setAmount(1);
        }

        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.DARK_GREEN + "使用人");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.DARK_GREEN + "使用人", "",
                "持ち物:",
                "  鉄の剣 ×1"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack Butler(int amount) {
        ItemStack Stack = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, amount);
        if(amount == 0) {
            Stack.setType(Material.GRAY_STAINED_GLASS_PANE);
            Stack.setAmount(1);
        }
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + "占い師");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.AQUA + "執事", "",
                "特筆事項: 執事は富豪が誰かを知っています", "",
                "持ち物:",
                "  即死剣 ×1", "",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "クリックで一人追加",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "SHIFT + クリックで一人減らす"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack Maid(int amount) {
        ItemStack Stack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, amount);
        if(amount == 0) {
            Stack.setType(Material.GRAY_STAINED_GLASS_PANE);
            Stack.setAmount(1);
        }
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.YELLOW + "メイド");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.YELLOW + "メイド", "",
                "持ち物:",
                "  即死弓 ×1",
                "  矢 ×2", "",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "クリックで一人追加",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "SHIFT + クリックで一人減らす"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack Millionaire(int amount) {
        ItemStack Stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, amount);
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.GREEN + "富豪");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.GREEN + "富豪", "",
                "持ち物:",
                "  即死剣 ×1",
                "  ケーキ ×3",
                "  不死のトーテム ×3",
                "  メイド召喚杖 ×1"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack FakeMaid(int amount) {
        ItemStack Stack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, amount);
        if(amount == 0) {
            Stack.setType(Material.GRAY_STAINED_GLASS_PANE);
            Stack.setAmount(1);
        }
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.RESET.toString() + ChatColor.GOLD + "偽メイド");
        itemMeta.setLore(Arrays.asList(
                ChatColor.RESET + "役職: " + ChatColor.GOLD + "偽メイド", "",
                "持ち物:",
                "  即死弓 ×1",
                "  矢 ×2", "",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "クリックで一人追加",
                ChatColor.RESET.toString() + ChatColor.YELLOW + "SHIFT + クリックで一人減らす"
        ));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack Start() {
        ItemStack Stack = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.YELLOW + "開始");
        itemMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "クリックで開始"));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }

    public ItemStack End() {
        ItemStack Stack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = Stack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColor.YELLOW + "強制終了");
        itemMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "クリックで強制終了"));
        Stack.setItemMeta(itemMeta);

        return Stack;
    }
}
