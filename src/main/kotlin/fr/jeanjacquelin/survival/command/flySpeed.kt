package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.intFlySpeed
import fr.jeanjacquelin.survival.util.registerBasicPlayerIntCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerFlySpeedCommand() = registerBasicPlayerIntCommand("flyspeed") { sender, speed ->
    speed?.let { intFlySpeed = speed }
    if (sender != this)
        sender.sendMessage("§7Fly speed of $name: $intFlySpeed")
    sendMessage("§7Fly speed: $intFlySpeed")
}