package fr.jeanjacquelin.survival.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerBasicPlayerStringCommand(
    commandName: String,
    playerAction: Player.(sender: CommandSender, arg: String?) -> Unit,
) = getCommand(commandName)?.setExecutor { sender, _, _, args ->
    val player = when (args.size) {
        2 -> args.firstOrNull()?.let { server.getPlayerByName(it) }
        else -> sender as? Player
    }
        ?: return@setExecutor false.also { sender.sendMessage("§cYou must be a player to perform this command.") }
    val arg = (args.getOrNull(1) ?: args.firstOrNull())
    player.playerAction(sender, arg)
    true
}
