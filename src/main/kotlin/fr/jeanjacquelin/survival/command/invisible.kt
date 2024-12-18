package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerInvisibleCommand() = registerBasicPlayerCommand("invisible") { sender ->
    isInvisible = !isInvisible
    sendMessage(
        if (isInvisible)
            "§aYou are now invisible."
        else
            "§aYou are now visible."
    )
    if (this != sender)
        sender.sendMessage(
            if (isInvisible)
                "§a$name is now invisible."
            else
                "§a$name is now visible."
        )
}