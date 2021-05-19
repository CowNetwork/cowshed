package network.cow.mc.cowshed

import network.cow.mc.cowshed.command.BuildCommand
import network.cow.mc.cowshed.command.FlyCommand
import network.cow.mc.cowshed.command.SonicCommand
import network.cow.mc.cowshed.command.SpawnCommand
import network.cow.mc.cowshed.listener.CancelListener
import network.cow.mc.cowshed.listener.ChatListener
import network.cow.mc.cowshed.listener.PlayerListener
import network.cow.mc.cowshed.listener.MultiJumpListener
import network.cow.messages.adventure.gradient
import network.cow.messages.core.Gradients
import network.cow.messages.spigot.MessagesPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Benedikt Wüller
 */
class CowshedPlugin : JavaPlugin() {

    override fun onEnable() {
        MessagesPlugin.PREFIX = "Lobby".gradient(Gradients.CORPORATE)

        Bukkit.getPluginManager().registerEvents(PlayerListener(), this)
        Bukkit.getPluginManager().registerEvents(MultiJumpListener(), this)
        Bukkit.getPluginManager().registerEvents(ChatListener(), this)
        Bukkit.getPluginManager().registerEvents(CancelListener(), this)

        Bukkit.getPluginCommand("spawn")?.setExecutor(SpawnCommand())
        Bukkit.getPluginCommand("sonic")?.setExecutor(SonicCommand())
        Bukkit.getPluginCommand("fly")?.setExecutor(FlyCommand())
        Bukkit.getPluginCommand("build")?.setExecutor(BuildCommand())
    }

}
