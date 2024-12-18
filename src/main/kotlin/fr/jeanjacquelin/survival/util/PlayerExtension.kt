package fr.jeanjacquelin.survival.util

import org.bukkit.Server
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun Player.heal() {
    health = attributeMaxHealth()
}

fun Player.attributeMaxHealth(): Double = getAttribute(Attribute.MAX_HEALTH)?.value ?: 20.0

fun Player.feed() {
    foodLevel = 20
}

fun Player.saturate() {
    saturation = 10.0f
}

// getIngo with Component

/** The speed at which a client will fly in range -10..10. Negative values indicate reverse directions */
var Player.intFlySpeed: Int
    get() = (this.flySpeed * 10).toInt()
    set(speed) {
        require(speed in -10..10) { "Speed must be in range -10..10" }
        flySpeed = speed / 10.0f
    }

/** The speed at which a client will walk in range -10..10. Negative values indicate reverse directions */
var Player.intWalkSpeed: Int
    get() = (this.walkSpeed * 10).toInt()
    set(speed) {
        require(speed in -10..10) { "Speed must be in range -10..10" }
        this.walkSpeed = speed / 10.0f
    }

fun Player.resetFlySpeed() {
    this.flySpeed = 0.1f
}

fun Player.resetWalkSpeed() {
    this.walkSpeed = 0.2f
}

fun Player.hasNightVision(): Boolean =
    activePotionEffects.any { it.type == PotionEffectType.NIGHT_VISION }

fun Player.nightVision(activate: Boolean): Boolean {
    if (activate)
        addPotionEffect(
            PotionEffect(
                /* type = */ PotionEffectType.NIGHT_VISION,
                /* duration = */ Integer.MAX_VALUE,
                /* amplifier = */ 0,
                /* ambient = */ true,
                /* particles = */ false
            )
        )
    else
        removePotionEffect(PotionEffectType.NIGHT_VISION)
    return activate
}

fun JavaPlugin.getPlayerByName(name: String): Player? = server.getPlayerByName(name)
fun Server.getPlayerByName(name: String): Player? =
    onlinePlayers.find { it.name.equals(name, true) }