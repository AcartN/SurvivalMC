package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerFlyCommand() = registerBasicPlayerCommand("fly") { sender ->
    allowFlight = !allowFlight
    sendMessage(
        if (allowFlight)
            "§aYou can now fly !"
        else
            "§cYou can no longer fly !"
    )
    if (this != sender)
        sender.sendMessage(
            if (allowFlight)
                "§a$name can now fly !"
            else
                "§c$name can no longer fly !"
        )
}