package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.getInfoAsTextComponent
import fr.jeanjacquelin.survival.util.registerBasicPlayerCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerInfoCommand() = registerBasicPlayerCommand("info") { sender ->
    sender.sendMessage(getInfoAsTextComponent())
}