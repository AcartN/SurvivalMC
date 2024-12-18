package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerGodCommand() = registerBasicPlayerCommand("god") { sender ->
    isInvulnerable = !isInvulnerable
    sendMessage(
        if (isInvulnerable)
            "§aYou are now invincible !"
        else
            "§cYou are no longer invincible !"
    )
    if (this != sender)
        sender.sendMessage(
            if (isInvulnerable)
                "§a$name is now invincible !"
            else
                "&c$name is no longer invincible !"
        )
}