package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.util.registerBasicPlayerStringCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerSetDisplayNameCommand() =
    registerBasicPlayerStringCommand("pseudo") { sender, pseudo ->
        val displayName = pseudo?.replace("&", "§")
        val displayNameComponent = displayName?.let { Component.text(it) }
        this.displayName(displayNameComponent)
        this.playerListName(displayNameComponent)
        displayName?.let {
            val newPlayerProfile = server.createProfile(uniqueId, displayName)
            newPlayerProfile.setProperties(playerProfile.properties)
            newPlayerProfile.update().thenAcceptAsync(
                {
                    playerProfile = newPlayerProfile
                    playerProfile.update()
                },
                {
                    Bukkit.getScheduler().runTask(this@registerSetDisplayNameCommand, it)
                }
            )
        }
        if (sender != this)
            sender.sendMessage(
                Component.text("Display name of $name: $displayName").color(NamedTextColor.GRAY)
            )
        sendMessage(
            Component.text("Display name: $displayName").color(NamedTextColor.GRAY)
        )
    }