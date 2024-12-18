package fr.jeanjacquelin.survival.logger

import org.koin.java.KoinJavaComponent.getKoin
import java.util.logging.Level
import java.util.logging.Logger

fun getPluginLogger(): Logger = getKoin().get()

fun logInfo(message: String) = getPluginLogger().info(message)
fun logWarning(message: String) = getPluginLogger().warning(message)
fun logError(message: String) = getPluginLogger().severe(message)

const val DefaultErrorMessage = "An error occurred"

fun logInfo(message: String = DefaultErrorMessage, throwable: Throwable) =
    getPluginLogger().log(Level.INFO, message, throwable)

fun logWarning(message: String = DefaultErrorMessage, throwable: Throwable) =
    getPluginLogger().log(Level.WARNING, message, throwable)

fun logError(message: String = DefaultErrorMessage, throwable: Throwable) =
    getPluginLogger().log(Level.SEVERE, message, throwable)