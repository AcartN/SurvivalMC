package fr.jeanjacquelin.survival.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player

fun Location.asTextComponent(): TextComponent = Component
    .text()
    .append(getCoordinatesTextComponent())
    .appendSpace()
    .append(Component.text("(").color(NamedTextColor.GRAY))
    .append(Component.text(world?.name ?: "Unknown").color(NamedTextColor.GOLD))
    .append(Component.text(")").color(NamedTextColor.GRAY))
    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, teleportCommand()))
    .hoverEvent(
        HoverEvent.showText(
            Component.text("Click to teleport.").color(NamedTextColor.GRAY)
        )
    )
    .build()

private fun Location.teleportCommand(): String =
    "/tp $x $y $z $yaw $pitch ${world.name}"

private fun Location.getCoordinatesTextComponent(): TextComponent = Component
    .text()
    .append(Component.text("x: $blockX").color(NamedTextColor.RED))
    .appendSpace()
    .append(Component.text("y: $blockY").color(NamedTextColor.GREEN))
    .appendSpace()
    .append(Component.text("z: $blockZ").color(NamedTextColor.BLUE))
    .build()

fun Boolean.asTextComponent(): TextComponent = when {
    this -> Component.text("✔").color(NamedTextColor.GREEN)
    else -> Component.text("✘").color(NamedTextColor.RED)
}

fun GameMode.asTextComponent(): TextComponent = when (this) {
    GameMode.SURVIVAL -> Component.text("Survival").color(NamedTextColor.RED)
    GameMode.CREATIVE -> Component.text("Creative").color(NamedTextColor.GREEN)
    GameMode.ADVENTURE -> Component.text("Adventure").color(NamedTextColor.GOLD)
    GameMode.SPECTATOR -> Component.text("Specator").color(NamedTextColor.GRAY)
}

fun Player.healthAsTextComponent() = Component.text(health.toString()).color(NamedTextColor.RED)
fun Player.foodAndSaturationAsTextComponent() = Component
    .text("$foodLevel ")
    .color(NamedTextColor.GOLD)
    .append(Component.text("(").color(NamedTextColor.GRAY))
    .append(Component.text("$saturation").color(NamedTextColor.YELLOW))
    .append(Component.text(")").color(NamedTextColor.GRAY))

fun Player.levelAsTextComponent() = Component.text(level.toString()).color(NamedTextColor.GREEN)
fun Player.flySpeedAsTextComponent() =
    Component.text(intFlySpeed.toString()).color(NamedTextColor.GOLD)

fun Player.walkSpeedAsTextComponent() =
    Component.text(intWalkSpeed.toString()).color(NamedTextColor.GOLD)

fun Player.isInvulnerableAsTextComponent() = isInvulnerable.asTextComponent()
fun Player.isSleepingAsTextComponent() = isSleeping.asTextComponent()
fun Player.isSprintingAsTextComponent() = isSprinting.asTextComponent()
fun Player.isSneakingAsTextComponent() = isSneaking.asTextComponent()
fun Player.isFlyingAsTextComponent() = isFlying.asTextComponent()
fun Player.allowFlightAsTextComponent() = allowFlight.asTextComponent()
fun Player.respawnLocationAsTextComponent() = respawnLocation?.asTextComponent()
    ?: Component.text("None").color(NamedTextColor.GRAY)

fun Player.getInfoAsTextComponent(): Component = Component
    .text("---------- ").color(NamedTextColor.GREEN)
    .append(Component.text(name).color(NamedTextColor.GRAY))
    .append(Component.text(" ----------").color(NamedTextColor.GREEN))
    .appendNewline()
    .append("Location" attributedWith location.asTextComponent()).appendNewline()
    .append("GameMode" attributedWith gameMode.asTextComponent()).appendNewline()
    .append("Health" attributedWith healthAsTextComponent()).appendNewline()
    .append("Food" attributedWith foodAndSaturationAsTextComponent()).appendNewline()
    .append("XP" attributedWith levelAsTextComponent()).appendNewline()
    .append("FlyingSpeed" attributedWith flySpeedAsTextComponent()).appendNewline()
    .append("WalkingSpeed" attributedWith walkSpeedAsTextComponent()).appendNewline()
    .append("God" attributedWith isInvulnerableAsTextComponent()).appendNewline()
    .append("Sleeping" attributedWith isSleepingAsTextComponent()).appendNewline()
    .append("Sprinting" attributedWith isSprintingAsTextComponent()).appendNewline()
    .append("Sneaking" attributedWith isSneakingAsTextComponent()).appendNewline()
    .append("Flying" attributedWith isFlyingAsTextComponent()).appendNewline()
    .append("AllowFlight" attributedWith allowFlightAsTextComponent()).appendNewline()
    .append("Invisible" attributedWith isInvisible.asTextComponent()).appendNewline()
    .append("Respawn Location" attributedWith respawnLocationAsTextComponent()).appendNewline()

private infix fun String.attributedWith(
    attributeValue: Component,
): TextComponent = Component
    .text("$this: ")
    .color(NamedTextColor.GRAY)
    .append(attributeValue)