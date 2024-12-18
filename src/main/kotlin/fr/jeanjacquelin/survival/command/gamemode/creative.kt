package fr.jeanjacquelin.survival.command.gamemode

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerCreativeCommand() = registerBasicPlayerCommand("creative") { sender ->
    val oldGameMode = gameMode
    when (oldGameMode) {
        GameMode.CREATIVE -> when {
            this != sender -> sender.sendMessage("§c$name is already in creative mode.")
            else -> sendMessage("§cYou are already in creative mode.")
        }

        else -> {
            gameMode = GameMode.CREATIVE
            if (this != sender) sender.sendMessage("§b$name is now in creative mode.")
            sendMessage("§bYou are now in creative mode.")
        }
    }
}