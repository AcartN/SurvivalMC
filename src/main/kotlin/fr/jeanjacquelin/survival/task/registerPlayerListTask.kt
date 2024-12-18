package fr.jeanjacquelin.survival.task

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

fun JavaPlugin.registerPlayerListTask() {
    val task = object : BukkitRunnable() {
        override fun run() {
            server.onlinePlayers.forEach { player ->
                player.sendPlayerListHeader(
                    Component
                        .text("Bonjour ")
                        .color(NamedTextColor.GREEN)
                        .append(
                            player.displayName().color(NamedTextColor.GRAY)
                        )
                        .append(Component.text(" !"))
                )
                player.sendPlayerListFooter(
                    Component
                        .text(player.world.time.toFormattedString())
                        .color(NamedTextColor.GRAY)
                )
            }
        }
    }
    // Run the task every 1 second
    task.runTaskTimerAsynchronously(
        /* plugin = */this,
        /* delay = */0,
        /* period = */REFRESH_RATE_IN_SECONDS * TICKS_PER_SECOND,
    )
}

private const val REFRESH_RATE_IN_SECONDS = 1L
private const val TICKS_PER_SECOND = 20L

private fun Long.toFormattedString(): String {
    fun Long.withPadding() = toString().padStart(2, '0')
    val hourOfDay = ((this + 6000L) % 24000L / 1000L).withPadding()
    val min = ((this % 1000L) * 60L / 1000L).withPadding()
    return "${hourOfDay}h$min"
}