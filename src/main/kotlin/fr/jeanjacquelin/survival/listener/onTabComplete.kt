package fr.jeanjacquelin.survival.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.TabCompleteEvent
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerTabCompleteListener() = server.pluginManager.registerEvents(
    object : Listener {
        @EventHandler
        fun onTabComplete(event: TabCompleteEvent) {
            // TODO: Add tab completion
        }
    },
    this
)