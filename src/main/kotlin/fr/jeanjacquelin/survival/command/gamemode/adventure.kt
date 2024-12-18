package fr.jeanjacquelin.survival.command.gamemode

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerAdventureCommand() = registerBasicPlayerCommand("adventure") { sender ->
    val oldGameMode = gameMode
    when (oldGameMode) {
        GameMode.ADVENTURE -> when {
            this != sender -> sender.sendMessage("§c$name is already in adventure mode.")
            else -> sendMessage("§cYou are already in adventure mode.")
        }

        else -> {
            gameMode = GameMode.ADVENTURE
            if (this != sender) sender.sendMessage("§6$name is now in adventure mode.")
            sendMessage("§6You are now in adventure mode.")
        }
    }
}