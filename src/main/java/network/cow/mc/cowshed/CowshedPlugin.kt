package network.cow.mc.cowshed

import network.cow.mc.cowshed.command.*
import network.cow.mc.cowshed.listener.*
import network.cow.messages.adventure.gradient
import network.cow.messages.core.Gradients
import network.cow.messages.spigot.MessagesPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Benedikt Wüller
 */
class CowshedPlugin : JavaPlugin() {

    lateinit var lobbyConfig: Config

    override fun onEnable() {
        MessagesPlugin.PREFIX = "Lobby".gradient(Gradients.CORPORATE)

        this.lobbyConfig = Config.from(this.config)

        Bukkit.getPluginManager().registerEvents(PlayerListener(this.lobbyConfig), this)
        Bukkit.getPluginManager().registerEvents(MultiJumpListener(), this)
        Bukkit.getPluginManager().registerEvents(ChatListener(), this)
        Bukkit.getPluginManager().registerEvents(CancelListener(), this)
        Bukkit.getPluginManager().registerEvents(NpcListener(), this)

        Bukkit.getPluginCommand("spawn")?.setExecutor(SpawnCommand())
        Bukkit.getPluginCommand("sonic")?.setExecutor(SonicCommand())
        Bukkit.getPluginCommand("fly")?.setExecutor(FlyCommand())
        Bukkit.getPluginCommand("build")?.setExecutor(BuildCommand())

        this.lobbyConfig.npcs.forEach { NpcRegistry.register(it) }
    }
}
