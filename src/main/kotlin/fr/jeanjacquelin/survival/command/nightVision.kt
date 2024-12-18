package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.hasNightVision
import fr.jeanjacquelin.survival.util.nightVision
import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerNightVisionCommand() = registerBasicPlayerCommand("nightvision") { sender ->
    if (nightVision(!hasNightVision())) {
        sendMessage("§aNight vision enabled.")
        if (sender != this)
            sender.sendMessage("§a$name has enabled night vision.")
    } else {
        sendMessage("§cNight vision disabled.")
        if (sender != this)
            sender.sendMessage("§c$name has disabled night vision.")
    }
}