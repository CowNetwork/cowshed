package network.cow.mc.cowshed.npc

import com.github.juliarn.npc.NPC
import com.github.juliarn.npc.NPCPool
import com.github.juliarn.npc.modifier.MetadataModifier
import com.github.juliarn.npc.profile.Profile
import net.kyori.adventure.text.Component
import network.cow.mc.cowshed.CowshedPlugin
import network.cow.mc.cowshed.NpcConfig
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.ThreadLocalRandom


object WrappedNpcFactory {

    private val pool = NPCPool.builder(JavaPlugin.getPlugin(CowshedPlugin::class.java))
        .actionDistance(10)
        .spawnDistance(60)
        .build()

    fun createNpc(config: NpcConfig): WrappedNpc {
        val uuid = UUID(ThreadLocalRandom.current().nextLong(), 0)
        val profile = Profile(UUID.fromString("fdef0011-1c58-40c8-bfef-0bdcb1495938"))
        profile.name = config.name
        profile.uniqueId = uuid
        profile.setProperty(Profile.Property("textures", config.textureValue, config.textureSignature))
        profile.complete()

        val handle = NPC.builder()
            .profile(profile)
            .location(config.location)
            .spawnCustomizer { npc, _ -> npc.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true).send() }
            .imitatePlayer(false)
            .lookAtPlayer(config.lookAtPlayer)
            .build(this.pool)

        val lineHandles = mutableListOf<ArmorStand>()
        if (config.lines.isNotEmpty()) {
            val loc = config.location.clone().add(0.0, 2.0, 0.0)
            config.lines.forEach {
                val stand = config.location.world.spawnEntity(loc.add(0.0, 0.2, 0.0), EntityType.ARMOR_STAND) as ArmorStand
                stand.setArms(false)
                stand.isMarker = true
                stand.isVisible = false
                stand.isCustomNameVisible = true
                stand.customName(Component.text(it))
                lineHandles.add(stand)
            }
        }

        return WrappedNpc(handle, lineHandles)
    }


}