package fr.jeanjacquelin.survival.command.gamemode

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerSpectatorCommand() = registerBasicPlayerCommand("spectator") { sender ->
    val oldGameMode = gameMode
    when (oldGameMode) {
        GameMode.SPECTATOR -> when {
            this != sender -> sender.sendMessage("§c$name is already in spectator mode.")
            else -> sendMessage("§cYou are already in spectator mode.")
        }

        else -> {
            gameMode = GameMode.SPECTATOR
            if (this != sender) sender.sendMessage("§7$name is now in spectator mode.")
            sendMessage("§7You are now in spectator mode.")
        }
    }
}