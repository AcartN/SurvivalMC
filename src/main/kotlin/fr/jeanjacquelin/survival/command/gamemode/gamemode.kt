package fr.jeanjacquelin.survival.command.gamemode

import fr.jeanjacquelin.survival.util.asTextComponent
import fr.jeanjacquelin.survival.util.getPlayerByName
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerGameModeCommand() {
    val command = getCommand("gamemode")
    command?.setTabCompleter { _, _, _, args ->
        when (args.size) {
            1 -> listOfGameModes(args.last()).ifEmpty { null }
            2 -> when {
                args[0].asGameMode() == null -> listOfGameModes(args[1]).ifEmpty { null }
                else -> null
            }

            else -> emptyList()
        }
    }
    command?.setExecutor { sender, _, _, args ->
        when (args.size) {
            0 -> when (sender) {
                is Player -> showActualGameMode(sender, sender)
                else -> sender.sendMessage("§cYou must be a player to perform this command.")
            }

            1 -> when (val gameMode = args.first().asGameMode()) {
                null -> when (val player = server.getPlayerByName(args.first())) {
                    null -> sender.sendMessage("§cInvalid gamemode")
                    else -> showActualGameMode(sender, player)
                }

                else -> when (sender) {
                    is Player -> setPlayerGameMode(sender, sender, sender.gameMode, gameMode)
                    else -> sender.sendMessage("§cYou must be a player to perform this command.")
                }
            }

            2 -> {
                var player = server.getPlayerByName(args[0])
                val gameMode: GameMode?
                when (player) {
                    null -> {
                        player = server.getPlayerByName(args[1])
                        gameMode = args[0].asGameMode()
                    }

                    else -> gameMode = args[1].asGameMode()
                }
                when {
                    player == null -> sender.sendMessage("§cPlayer not found.")
                    gameMode == null -> sender.sendMessage("§cInvalid gamemode")
                    else -> setPlayerGameMode(sender, player, player.gameMode, gameMode)
                }
            }

            else -> return@setExecutor false
        }
        true
    }
}

private fun showActualGameMode(sender: CommandSender, player: Player) {
    sender.sendMessage(
        when {
            player != sender -> Component.text()
                .append(player.displayName())
                .append(Component.text(" is in ").color(NamedTextColor.GRAY))
                .append(player.gameMode.asTextComponent())
                .append(Component.text(" mode.").color(NamedTextColor.GRAY))

            else -> Component
                .text("You are in ")
                .color(NamedTextColor.GRAY)
                .append(player.gameMode.asTextComponent())
                .append(Component.text(" mode.").color(NamedTextColor.GRAY))
        }
    )
}

private fun setPlayerGameMode(
    sender: CommandSender,
    player: Player,
    oldGameMode: GameMode,
    gameMode: GameMode
) {
    when {
        oldGameMode != gameMode -> {
            player.gameMode = gameMode
            player.sendMessage(
                Component
                    .text("You are now in ")
                    .color(NamedTextColor.GREEN)
                    .append(gameMode.asTextComponent())
                    .append(Component.text(" mode.").color(NamedTextColor.GREEN))
            )
            if (player != sender) {
                sender.sendMessage(
                    Component.text()
                        .append(player.displayName())
                        .append(Component.text(" is now in ").color(NamedTextColor.GREEN))
                        .append(gameMode.asTextComponent())
                        .append(Component.text(" mode.").color(NamedTextColor.GREEN))
                )
            }
        }

        else -> sender.sendMessage(
            when {
                player != sender -> Component.text()
                    .append(player.displayName())
                    .append(Component.text(" is already in ").color(NamedTextColor.RED))
                    .append(gameMode.asTextComponent())
                    .append(Component.text(" mode.").color(NamedTextColor.RED))

                else -> Component.text("You are already in ")
                    .color(NamedTextColor.RED)
                    .append(gameMode.asTextComponent())
                    .append(Component.text(" mode.").color(NamedTextColor.RED))
            }
        )
    }
}

private fun String.asGameMode(): GameMode? = when (this) {
    "survival", "0" -> GameMode.SURVIVAL
    "creative", "1" -> GameMode.CREATIVE
    "adventure", "2" -> GameMode.ADVENTURE
    "spectator", "3" -> GameMode.SPECTATOR
    else -> null
}

private fun listOfGameModes(startingWith: String) = listOf(
    "survival",
    "creative",
    "adventure",
    "spectator",
).filter { it.startsWith(startingWith, ignoreCase = true) }
