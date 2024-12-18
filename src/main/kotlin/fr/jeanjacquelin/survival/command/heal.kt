package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.feed
import fr.jeanjacquelin.survival.util.heal
import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import fr.jeanjacquelin.survival.util.saturate
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerHealCommand() = registerBasicPlayerCommand("heal") { sender ->
    heal()
    feed()
    saturate()
    sendMessage("§aYou have been healed.")
    if (this != sender)
        sender.sendMessage("§a${this.name} has been healed.")
}