package fr.jeanjacquelin.survival

import fr.jeanjacquelin.survival.config.setupCommandsAndEvents
import fr.jeanjacquelin.survival.config.setupKoin
import fr.jeanjacquelin.survival.logger.logInfo
import fr.jeanjacquelin.survival.task.registerPlayerListTask
import org.bukkit.plugin.java.JavaPlugin

class Survival : JavaPlugin() {

    override fun onEnable() {
        setupKoin()
        setupCommandsAndEvents()

        registerPlayerListTask()

        logInfo("§aSurvival enabled")
    }

    override fun onDisable() {
        logInfo("§cSurvival disabled. Goodbye!")
    }

}