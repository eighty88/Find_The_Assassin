package com.github.eighty88.findassassin.player;

import org.bukkit.ChatColor;

public enum RoleType {
    None("観戦者"),
    Assassin(ChatColor.RED + "暗殺者"),
    Millionaire(ChatColor.GREEN + "富豪"),
    Maid(ChatColor.YELLOW + "メイド"),
    Butler(ChatColor.AQUA + "執事"),
    Servant(ChatColor.DARK_GREEN + "使用人"),
    FakeMaid(ChatColor.GOLD + "偽メイド");

    private final String Name;

    RoleType(String Name) {
        this.Name = Name;
    }

    public String toString() {
        return this.Name;
    }
}
