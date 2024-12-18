package fr.jeanjacquelin.survival.config

import fr.jeanjacquelin.survival.command.gamemode.registerAdventureCommand
import fr.jeanjacquelin.survival.command.gamemode.registerCreativeCommand
import fr.jeanjacquelin.survival.command.gamemode.registerGameModeCommand
import fr.jeanjacquelin.survival.command.gamemode.registerSpectatorCommand
import fr.jeanjacquelin.survival.command.gamemode.registerSurvivalCommand
import fr.jeanjacquelin.survival.command.registerEnderChestCommand
import fr.jeanjacquelin.survival.command.registerFeedCommand
import fr.jeanjacquelin.survival.command.registerFlyCommand
import fr.jeanjacquelin.survival.command.registerFlySpeedCommand
import fr.jeanjacquelin.survival.command.registerGodCommand
import fr.jeanjacquelin.survival.command.registerHealCommand
import fr.jeanjacquelin.survival.command.registerInfoCommand
import fr.jeanjacquelin.survival.command.registerInvisibleCommand
import fr.jeanjacquelin.survival.command.registerNightVisionCommand
import fr.jeanjacquelin.survival.command.registerSetDisplayNameCommand
import fr.jeanjacquelin.survival.command.registerTpCommand
import fr.jeanjacquelin.survival.command.registerWalkSpeedCommand
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.setupCommandsAndEvents() {
    registerFeedCommand()
    registerFlyCommand()
    registerFlySpeedCommand()
    registerGodCommand()
    registerHealCommand()
    registerInfoCommand()
    registerInvisibleCommand()
    registerWalkSpeedCommand()
    registerEnderChestCommand()
    registerNightVisionCommand()
    registerSetDisplayNameCommand()
    registerTpCommand()
    registerGameModeCommand()
    registerSurvivalCommand()
    registerCreativeCommand()
    registerAdventureCommand()
    registerSpectatorCommand()

//    registerTabCompleteListener()
}