package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerEnderChestCommand() = registerBasicPlayerCommand("enderchest") { sender ->
    when (sender) {
        this -> openInventory(enderChest)
        is Player -> sender.openInventory(enderChest)
        else -> sender.sendMessage("§cYou must be a player to use this command.")
    }
}