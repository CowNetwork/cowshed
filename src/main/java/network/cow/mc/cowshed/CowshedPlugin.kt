package network.cow.mc.cowshed

import network.cow.mc.cowshed.command.SonicCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Benedikt Wüller
 */
class CowshedPlugin : JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(MultiJumpListener(), this)

        Bukkit.getPluginCommand("sonic")?.setExecutor(SonicCommand())
    }

}
