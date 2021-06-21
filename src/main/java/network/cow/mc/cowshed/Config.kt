package network.cow.mc.cowshed

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.*

class NpcConfig(
    val id: String,
    val name: String = "",
    val lines: List<String> = listOf(),
    val lookAtPlayer: Boolean = false,
    val textureValue: String,
    val textureSignature: String,
    val location: Location
) {
    companion object {
        fun from(id: String, section: ConfigurationSection): NpcConfig {
            val location = Location(
                Bukkit.getWorld("spawn"),
                section.getDouble("location.x"),
                section.getDouble("location.y"),
                section.getDouble("location.z")
            )

            location.yaw = section.getDouble("location.yaw", 0.0).toFloat()
            location.pitch = section.getDouble("location.pitch", 0.0).toFloat()

            return NpcConfig(
                id,
                section.getString("name") ?: "",
                section.getStringList("lines") ?: listOf(),
                section.getBoolean("lookAtPlayer"),
                section.getString("skin.value") ?: "",
                section.getString("skin.signature") ?: "",
                location
            )
        }
    }
}

class Config(
    val spawnLocation: Location,
    val npcs: List<NpcConfig>
) {
    companion object {
        fun from(section: ConfigurationSection): Config {
            val npcList = section.getConfigurationSection("npcs")!!.getKeys(false)

            println(npcList::class.java)

            val location = Location(
                Bukkit.getWorld("spawn"),
                section.getDouble("spawnLocation.x"),
                section.getDouble("spawnLocation.y"),
                section.getDouble("spawnLocation.z")
            )

            location.yaw = section.getDouble("spawnLocation.yaw", 0.0).toFloat()
            location.pitch = section.getDouble("spawnLocation.pitch", 0.0).toFloat()

            return Config(
                location,
                npcList.map { NpcConfig.from(it, section.getConfigurationSection("npcs.$it")!!); }.toList()
            )
        }
    }
}