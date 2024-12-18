package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.intWalkSpeed
import fr.jeanjacquelin.survival.util.registerBasicPlayerIntCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerWalkSpeedCommand() = registerBasicPlayerIntCommand("walkspeed") { sender, speed ->
    speed?.let { intWalkSpeed = speed }
    if (sender != this)
        sender.sendMessage("§7Walk speed of $name: $intWalkSpeed")
    sendMessage("§7Walk speed: $intWalkSpeed")
}