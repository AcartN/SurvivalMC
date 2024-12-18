# Survival MC

## Description

This is a Minecraft plugin for PaperMC servers.
It adds basic commands to the game

## Commands

| Command                                                                                | Description                                         | Usage                                                                                  | Permission                     | Aliases                                    |
|----------------------------------------------------------------------------------------|-----------------------------------------------------|----------------------------------------------------------------------------------------|--------------------------------|--------------------------------------------|
| `/feed [player]`                                                                       | Feeds a player.                                     | `/feed [player]`                                                                       | `survival.command.feed`        |                                            |
| `/fly [player]`                                                                        | Toggles fly mode for a player.                      | `/fly [player]`                                                                        | `survival.command.fly`         |                                            |
| `/god [player]`                                                                        | Toggles god mode for a player.                      | `/god [player]`                                                                        | `survival.command.god`         |                                            |
| `/invisible [player]`                                                                  | Toggles invisibility for a player.                  | `/invisible [player]`                                                                  | `survival.command.invisible`   |                                            |
| `/flyspeed [player] [-10..10]`                                                         | Sets the fly speed of a player.                     | `/flyspeed [player] [-10..10]`                                                         | `survival.command.flyspeed`    |                                            |
| `/walkspeed [player] [-10..10]`                                                        | Sets the walk speed of a player.                    | `/walkspeed [player] [-10..10]`                                                        | `survival.command.walkspeed`   |                                            |
| `/info [player]`                                                                       | Displays information about a player.                | `/info [player]`                                                                       | `survival.command.info`        |                                            |
| `/pseudo [player] <pseudo>`                                                            | Sets the display name of a player.                  | `/pseudo [player] <pseudo>`                                                            | `survival.command.pseudo`      | `setDisplayName`, `name`, `nick`, `rename` |
| `/heal [player]`                                                                       | Heals a player.                                     | `/heal [player]`                                                                       | `survival.command.heal`        |                                            |
| `/nightVision <player>`                                                                | Toggles night vision for a player.                  | `/nightVision <player>`                                                                | `survival.command.nightVision` | `nv`, `vision`, `nightvision`              |
| `/enderchest <player>`                                                                 | Opens the ender chest of a player.                  | `/enderchest <player>`                                                                 | `survival.command.enderchest`  |                                            |
| `/teleport [player] <x> <y> <z> [yaw pitch] [world]` or `/teleport [player] <player2>` | Teleports a player to a location or another player. | `/teleport [player] <x> <y> <z> [yaw pitch] [world]` or `/teleport [player] <player2>` | `survival.command.teleport`    | `tp`                                       |
| `/gamemode [player] <gamemode>`                                                        | Changes the gamemode of a player.                   | `/gamemode [player] <gamemode>`                                                        | `survival.command.gamemode`    | `gm`, `g`                                  |
| `/survival [player]`                                                                   | Changes the gamemode of a player to survival.       | `/survival [player]`                                                                   | `survival.command.gamemode`    | `g0`, `gm0`                                |
| `/creative [player]`                                                                   | Changes the gamemode of a player to creative.       | `/creative [player]`                                                                   | `survival.command.gamemode`    | `g1`, `gm1`                                |
| `/adventure [player]`                                                                  | Changes the gamemode of a player to adventure.      | `/adventure [player]`                                                                  | `survival.command.gamemode`    | `g2`, `gm2`                                |
| `/spectator [player]`                                                                  | Changes the gamemode of a player to spectator.      | `/spectator [player]`                                                                  | `survival.command.gamemode`    | `g3`, `gm3`                                |

## Installation

1. Download the latest plugin JAR file from the releases page.
2. Place the JAR file into the `plugins` folder of your PaperMC server.
3. Restart your server.

## Building

1. Clone the repository: `git clone https://github.com/AcartN/SurvivalMC.git`
2. Navigate to the project directory: `cd SurvivalMC`
3. Build the plugin using Maven: `./gradlew pluginUberJar`
4. The built JAR file will be located in the `build/libs` directory.

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue on the
GitHub repository. If you would like to contribute code, please fork the repository and submit a
pull request.

## License

This plugin is licensed under the MIT License. See the LICENSE file for more information.