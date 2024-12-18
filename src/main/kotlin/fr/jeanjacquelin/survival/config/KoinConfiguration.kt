package fr.jeanjacquelin.survival.config

import fr.jeanjacquelin.survival.Survival
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun Survival.setupKoin() {
    val koinModule = module {
        single { this@setupKoin.logger }
        single<JavaPlugin> { this@setupKoin }
    }
    startKoin {
        modules(koinModule)
    }
}