package fr.jeanjacquelin.survival.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerBasicPlayerCommand(
    commandName: String,
    playerAction: Player.(sender: CommandSender) -> Unit,
) = getCommand(commandName)?.setExecutor { sender, _, _, args ->
    when {
        args.isNotEmpty() -> server.getPlayerByName(args.first())?.playerAction(sender) != null
        sender is Player -> true.also { sender.playerAction(sender) }
        else -> false.also { sender.sendMessage("§cVous devez être un joueur pour effectuer cette commande") }
    }
}
