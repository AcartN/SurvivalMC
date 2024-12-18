package fr.jeanjacquelin.survival.command

import fr.jeanjacquelin.survival.logger.getPluginLogger
import fr.jeanjacquelin.survival.util.asTextComponent
import fr.jeanjacquelin.survival.util.getPlayerByName
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * Register the /tp command.
 *
 * Usage:
 *  - /teleport [player] <targetPlayer>
 *  - /teleport [player] <x> <y> <z> [yaw pitch] [world]
 * All possibilities:
 * /teleport [player] targetPlayer
 * /teleport [player] x y z
 * /teleport [player] x y z yaw pitch
 * /teleport [player] x y z world
 * /teleport [player] x y z yaw pitch world
 */
@Suppress("KDocUnresolvedReference")
fun JavaPlugin.registerTpCommand() {
    val command = getCommand("teleport")
    command?.setTabCompleter { sender, _, _, args ->
        when (args.size) {
            1 -> listPlayers(args[0]) + listSelector(args[0]) + if (args[0].isEmpty()) listOf(
                "~",
                "~ ~",
                "~ ~ ~"
            ) else emptyList()

            2 -> {
                var targetFound = false
                findPlayerOrSelectorOrSender(
                    sender = sender,
                    arg = args[0],
                    onTargetFound = { targetFound = true },
                )
                if (targetFound) {
                    listPlayers(args[1]) + if (args[1].isEmpty()) listOf(
                        "~",
                        "~ ~",
                        "~ ~ ~"
                    ) else emptyList()
                } else {
                    if (args[1].isEmpty()) listOf("~", "~ ~") else emptyList()
                }
            }

            3 -> {
                var targetFound = false
                findPlayerOrSelectorOrSender(
                    sender = sender,
                    arg = args[0],
                    onTargetFound = { targetFound = true },
                )
                if (targetFound) {
                    if (args[2].isEmpty()) listOf("~", "~ ~") else emptyList()
                } else {
                    if (args[2].isEmpty()) listOf("~") else emptyList()
                }
            }

            4 -> {
                var targetFound = false
                findPlayerOrSelectorOrSender(
                    sender = sender,
                    arg = args[0],
                    onTargetFound = { targetFound = true },
                )
                if (targetFound) {
                    if (args[3].isEmpty()) listOf("~") else emptyList()
                } else {
                    listWorlds(args[3])
                }
            }

            5 -> listWorlds(args[4])
            6 -> listWorlds(args[5])
            else -> emptyList()
        }
    }
    command?.setExecutor { sender, _, _, args ->
        val consumableArgs = args.toMutableList()
        val playerOrSelector: PlayerOrSelector? = when {
            args.isEmpty() -> return@setExecutor false
            args.size == 1 -> (sender as? Player)?.let { PlayerOrSelector.OnePlayer(it) }
            else -> findPlayerOrSelectorOrSender(
                sender = sender,
                arg = consumableArgs.first(),
                onTargetFound = { consumableArgs.removeAt(0) },
            ) // If not a player or selector, assume the sender is the target if it's a player
                ?: (sender as? Player)?.let { PlayerOrSelector.OnePlayer(it) }
        }

        if (playerOrSelector == null) {
            sender.sendMessage(
                Component
                    .text("You must be a player to perform this command.")
                    .color(NamedTextColor.RED)
            )
            return@setExecutor true
        }

        when (consumableArgs.size) {
            1 -> { // /teleport [player] targetPlayer
                val targetPlayer = server.getPlayerByName(consumableArgs.first())
                if (targetPlayer == null) {
                    sender.sendMessage("§cPlayer not found.")
                } else {
                    teleportPlayerToPlayer(sender, playerOrSelector, targetPlayer)
                }
            }
            // /teleport [player] x y z
            3 -> {
                val coordinates = computeCoordinates(
                    xStr = consumableArgs[0],
                    yStr = consumableArgs[1],
                    zStr = consumableArgs[2],
                )
                when {
                    coordinates == null -> sender.sendMessage("§cInvalid coordinates.")
                    else -> teleportPlayerToLocation(
                        sender,
                        playerOrSelector = playerOrSelector,
                        coordinates.x,
                        coordinates.y,
                        coordinates.z,
                    )
                }
            }

            // /teleport [player] x y z world ->
            4 -> {
                val (coordinates, world) = computeCoordinatesAndWorld(
                    xStr = consumableArgs[0],
                    yStr = consumableArgs[1],
                    zStr = consumableArgs[2],
                    worldStr = consumableArgs[3],
                )
                when {
                    world == null -> sender.sendMessage("§cWorld not found.")
                    coordinates == null -> sender.sendMessage("§cInvalid coordinates.")
                    else -> teleportPlayerToLocation(
                        sender = sender,
                        playerOrSelector = playerOrSelector,
                        x = coordinates.x,
                        y = coordinates.y,
                        z = coordinates.z,
                        world = world,
                    )
                }
            }

            // /teleport [player] x y z yaw pitch
            5 -> {
                val (coordinates, yawAndPitch) = computeCoordinatesAndYawPitch(
                    xStr = consumableArgs[0],
                    yStr = consumableArgs[1],
                    zStr = consumableArgs[2],
                    yawStr = consumableArgs[3],
                    pitchStr = consumableArgs[4],
                )
                when {
                    coordinates == null -> sender.sendMessage("§cInvalid coordinates.")
                    yawAndPitch == null -> sender.sendMessage("§cInvalid yaw or pitch.")
                    else -> teleportPlayerToLocation(
                        sender = sender,
                        playerOrSelector = playerOrSelector,
                        x = coordinates.x,
                        y = coordinates.y,
                        z = coordinates.z,
                        yaw = yawAndPitch.yaw,
                        pitch = yawAndPitch.pitch,
                    )
                }
            }

            // /teleport [player] x y z yaw pitch world
            6 -> {
                operator fun List<String>.component6() = get(5)
                val (xStr, yStr, zStr, yawStr, pitchStr, worldStr) = consumableArgs
                val (coordinates, yawAndPitch, world) = computeCoordinatesAndYawPitchAndWorld(
                    xStr = xStr,
                    yStr = yStr,
                    zStr = zStr,
                    yawStr = yawStr,
                    pitchStr = pitchStr,
                    worldStr = worldStr,
                )
                when {
                    coordinates == null -> sender.sendMessage("§cInvalid coordinates.")
                    yawAndPitch == null -> sender.sendMessage("§cInvalid yaw or pitch.")
                    world == null -> sender.sendMessage("§cWorld not found.")
                    else -> teleportPlayerToLocation(
                        sender = sender,
                        playerOrSelector = playerOrSelector,
                        x = coordinates.x,
                        y = coordinates.y,
                        z = coordinates.z,
                        yaw = yawAndPitch.yaw,
                        pitch = yawAndPitch.pitch,
                        world = world,
                    )
                }
            }

            else -> return@setExecutor false
        }
        true
    }
}

private sealed interface Coordinate {
    data class Absolute(val value: Double) : Coordinate
    data class Relative(val value: Double) : Coordinate
}

private data class CoordinateX(
    val x: Coordinate,
) {
    fun computeValue(entity: Entity): Double = when (x) {
        is Coordinate.Absolute -> x.value
        is Coordinate.Relative -> entity.location.x + x.value
    }
}

private data class CoordinateY(
    val y: Coordinate,
) {
    fun computeValue(entity: Entity): Double = when (y) {
        is Coordinate.Absolute -> y.value
        is Coordinate.Relative -> entity.location.y + y.value
    }
}

private data class CoordinateZ(
    val z: Coordinate,
) {
    fun computeValue(entity: Entity): Double = when (z) {
        is Coordinate.Absolute -> z.value
        is Coordinate.Relative -> entity.location.z + z.value
    }
}

private data class Coordinates(
    val x: CoordinateX,
    val y: CoordinateY,
    val z: CoordinateZ,
)

private data class YawAndPitch(
    val yaw: Float,
    val pitch: Float,
)

private data class CoordinatesAndWorld(
    val coordinates: Coordinates?,
    val world: World?,
)

private data class CoordinatesAndYawPitch(
    val coordinates: Coordinates?,
    val yawAndPitch: YawAndPitch?,
)

private data class CoordinatesAndYawPitchAndWorld(
    val coordinates: Coordinates?,
    val yawAndPitch: YawAndPitch?,
    val world: World?,
)

private fun computeCoordinate(value: String): Coordinate? = when {
    value.startsWith("~") -> value.drop(1).let { relativeCoordinate ->
        if (relativeCoordinate.isEmpty()) Coordinate.Relative(0.0)
        else relativeCoordinate.toDoubleOrNull()?.let { Coordinate.Relative(it) }
    }

    else -> value.toDoubleOrNull()?.let { Coordinate.Absolute(it) }
}

private fun computeCoordinates(
    xStr: String,
    yStr: String,
    zStr: String
): Coordinates? {
    val x = computeCoordinate(xStr)?.let { CoordinateX(it) }
    val y = computeCoordinate(yStr)?.let { CoordinateY(it) }
    val z = computeCoordinate(zStr)?.let { CoordinateZ(it) }
    getPluginLogger().info("x: $x, y: $y, z: $z")
    return if (x == null || y == null || z == null) null else Coordinates(x, y, z)
}

private fun computeYawAndPitch(
    yawStr: String,
    pitchStr: String
): YawAndPitch? {
    val yaw = yawStr.toFloatOrNull()
    val pitch = pitchStr.toFloatOrNull()
    return if (yaw == null || pitch == null) null else YawAndPitch(yaw, pitch)
}

private fun JavaPlugin.computeCoordinatesAndWorld(
    xStr: String,
    yStr: String,
    zStr: String,
    worldStr: String,
): CoordinatesAndWorld = CoordinatesAndWorld(
    world = server.getWorld(worldStr),
    coordinates = computeCoordinates(xStr, yStr, zStr),
)

private fun computeCoordinatesAndYawPitch(
    xStr: String,
    yStr: String,
    zStr: String,
    yawStr: String,
    pitchStr: String,
): CoordinatesAndYawPitch = CoordinatesAndYawPitch(
    coordinates = computeCoordinates(xStr, yStr, zStr),
    yawAndPitch = computeYawAndPitch(yawStr, pitchStr),
)

private fun JavaPlugin.computeCoordinatesAndYawPitchAndWorld(
    xStr: String,
    yStr: String,
    zStr: String,
    yawStr: String,
    pitchStr: String,
    worldStr: String,
): CoordinatesAndYawPitchAndWorld = CoordinatesAndYawPitchAndWorld(
    world = server.getWorld(worldStr),
    coordinates = computeCoordinates(xStr, yStr, zStr),
    yawAndPitch = computeYawAndPitch(yawStr, pitchStr),
)

sealed interface PlayerOrSelector {
    data class OnePlayer(val player: Player) : PlayerOrSelector
    data class MultipleEntities(val selector: String, val entities: List<Entity>) : PlayerOrSelector
}

private fun JavaPlugin.findPlayerOrSelectorOrSender(
    sender: CommandSender,
    arg: String,
    onTargetFound: () -> Unit,
): PlayerOrSelector? {
    // Try to find a player by name
    val playerByName = server.getPlayerByName(arg)
    if (playerByName != null) {
        onTargetFound()
        return PlayerOrSelector.OnePlayer(playerByName)
    }

    // Try to find entities using a selector
    val selector = select(sender, arg)
    if (selector != null) {
        onTargetFound()
        return PlayerOrSelector.MultipleEntities(arg, selector)
    }

    return null
}

private fun teleportPlayerToPlayer(
    sender: CommandSender,
    playerOrSelector: PlayerOrSelector,
    targetPlayer: Player
) {
    when (playerOrSelector) {
        is PlayerOrSelector.OnePlayer -> {
            playerOrSelector.player.teleport(targetPlayer)
            if (sender == playerOrSelector.player) {
                sender.sendMessage("§aTeleported to §7${targetPlayer.name}§a.")
            } else {
                sender.sendMessage("§aYou teleported §7${playerOrSelector.player.name}§a to §7${targetPlayer.name}§a.")
                playerOrSelector.player.sendMessage("§aYou were teleported to §7${targetPlayer.name}§a.")
            }
        }

        is PlayerOrSelector.MultipleEntities -> {
            playerOrSelector.entities.forEach {
                it.teleport(targetPlayer)
                if (it is Player)
                    it.sendMessage("§aYou were teleported to §7${targetPlayer.name}§a.")
                sender.sendMessage("§aYou teleported §7${it.name}§a to §7${targetPlayer.name}§a.")
            }
        }
    }
}

private fun teleportPlayerToLocation(
    sender: CommandSender,
    playerOrSelector: PlayerOrSelector,
    x: CoordinateX,
    y: CoordinateY,
    z: CoordinateZ,
    yaw: Float? = null,
    pitch: Float? = null,
    world: World? = null,
) {
    when (playerOrSelector) {
        is PlayerOrSelector.OnePlayer -> {
            val player = playerOrSelector.player
            val location = Location(
                world ?: player.world,
                x.computeValue(player),
                y.computeValue(player),
                z.computeValue(player),
                yaw ?: player.location.yaw,
                pitch ?: player.location.pitch,
            )
            player.teleport(location)
            if (sender == player) {
                sender.sendMessage(
                    Component.text()
                        .append(Component.text("Teleported to ").color(NamedTextColor.GREEN))
                        .append(location.asTextComponent())
                        .append(Component.text(".").color(NamedTextColor.GREEN))
                )
            } else {
                sender.sendMessage(
                    Component.text()
                        .append(Component.text("You teleported ").color(NamedTextColor.GREEN))
                        .append(player.displayName())
                        .append(Component.text(" to ").color(NamedTextColor.GREEN))
                        .append(location.asTextComponent())
                        .append(Component.text(".").color(NamedTextColor.GREEN))
                )
                player.sendMessage(
                    Component.text()
                        .append(
                            Component.text("You were teleported to ").color(NamedTextColor.GREEN)
                        )
                        .append(location.asTextComponent())
                        .append(Component.text(".").color(NamedTextColor.GREEN))
                )
            }
        }

        is PlayerOrSelector.MultipleEntities -> {
            playerOrSelector.entities.forEach { entity ->
                val location = Location(
                    world ?: (sender as? Player)?.world ?: entity.world,
                    x.computeValue(entity),
                    y.computeValue(entity),
                    z.computeValue(entity),
                    yaw ?: entity.location.yaw,
                    pitch ?: entity.location.pitch,
                )
                entity.teleport(location)
                if (entity is Player)
                    entity.sendMessage(
                        Component.text()
                            .append(
                                Component.text("You were teleported to ")
                                    .color(NamedTextColor.GREEN)
                            )
                            .append(location.asTextComponent())
                            .append(Component.text(".").color(NamedTextColor.GREEN))
                    )
                sender.sendMessage(
                    Component.text()
                        .append(Component.text("You teleported ").color(NamedTextColor.GREEN))
                        .append(Component.text(entity.name).color(NamedTextColor.GRAY))
                        .append(Component.text(" to ").color(NamedTextColor.GREEN))
                        .append(location.asTextComponent())
                        .append(Component.text(".").color(NamedTextColor.GREEN))
                )
            }
        }
    }
}

private fun JavaPlugin.listWorlds(startingWith: String): List<String> = server.worlds
    .filter { it.name.startsWith(startingWith, ignoreCase = true) }
    .map { it.name }

private fun listSelector(startingWith: String): List<String> = listOf("@a", "@e", "@p", "@r", "@s")
    .filter { it.startsWith(startingWith, ignoreCase = true) }

private fun JavaPlugin.listPlayers(startingWith: String): List<String> = server.onlinePlayers
    .map { it.name }
    .filter { it.startsWith(startingWith, ignoreCase = true) }

private fun JavaPlugin.select(sender: CommandSender, selector: String) = runCatching {
    server.selectEntities(sender, selector)
}.getOrNull()