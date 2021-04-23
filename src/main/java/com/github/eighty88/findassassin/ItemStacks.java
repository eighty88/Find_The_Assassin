package com.github.eighty88.findassassin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class ItemStacks {
    public static ItemStack getSword() {
        ItemStack result = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        ((Damageable) meta).setDamage(251);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10000,true);
        meta.setDisplayName(ChatColor.RESET + "一撃剣");
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "プレイヤーを一撃で倒せる。"));
        result.setItemMeta(meta);
        return result;
    }

    public static ItemStack getBow() {
        ItemStack result = new ItemStack(Material.BOW, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 10000,true);
        meta.setDisplayName(ChatColor.RESET + "一撃弓");
        meta.setUnbreakable(true);
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "プレイヤーを一撃で倒せる。"));
        result.setItemMeta(meta);
        return result;
    }

    public static ItemStack getTorch() {
        ItemStack result = new ItemStack(Material.REDSTONE_TORCH, 1);
        ItemMeta meta = result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "メイド召喚杖");
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "メイドを召喚できる。"));
        result.setItemMeta(meta);
        return result;
    }

    public static ItemStack getPotion() {
        ItemStack result = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "透明化のポーション");
        meta.setLore(Collections.singletonList(ChatColor.AQUA + "透明化できる。"));
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, true, true, true), true);
        result.setItemMeta(meta);
        return result;
    }
}
