package fr.jeanjacquelin.survival.command.gamemode

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerSurvivalCommand() = registerBasicPlayerCommand("survival") { sender ->
    val oldGameMode = gameMode
    when (oldGameMode) {
        GameMode.SURVIVAL -> when {
            this != sender -> sender.sendMessage("§c$name is already in survival mode.")
            else -> sendMessage("§cYou are already in survival mode.")
        }

        else -> {
            gameMode = GameMode.SURVIVAL
            if (this != sender) sender.sendMessage("§a$name is now in survival mode.")
            sendMessage("§aYou are now in survival mode.")
        }
    }
}