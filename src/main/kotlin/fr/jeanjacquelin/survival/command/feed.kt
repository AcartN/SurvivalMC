package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.feed
import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import fr.jeanjacquelin.survival.util.saturate
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerFeedCommand() = registerBasicPlayerCommand("feed") { sender ->
    feed()
    saturate()
    sendMessage("§aYou have been fed.")
    if (this != sender)
        sender.sendMessage("§a${this.name} has been fed.")
}