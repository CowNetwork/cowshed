package network.cow.mc.cowshed

import com.github.juliarn.npc.NPC
import com.github.juliarn.npc.NPCPool
import com.github.juliarn.npc.modifier.MetadataModifier
import com.github.juliarn.npc.profile.Profile
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.ThreadLocalRandom


object NpcRegistry {

    private val pool = NPCPool.builder(JavaPlugin.getPlugin(CowshedPlugin::class.java))
        .actionDistance(30)
        .spawnDistance(60)
        .build()

    private val map = hashMapOf<String, NPC>()

    fun register(config: NpcConfig) {
        val uuid = UUID(ThreadLocalRandom.current().nextLong(), 0)
        val profile = Profile(UUID.fromString("fdef0011-1c58-40c8-bfef-0bdcb1495938"))

        val ser = LegacyComponentSerializer.builder().hexColors().build()

        // reverse list so that the entries in the config
        // actually represent the order of the lines in-game,
        // because we create them from bottom to top and not top to bottom.
        val rev = config.lines.asReversed()

        if (config.lines.isNotEmpty()) {
            val loc = config.location.clone().add(0.0, 1.9, 0.0)
            rev.forEach {
                if (rev.first() != it ) {
                    val stand = config.location.world.spawnEntity(loc.add(0.0, 0.3, 0.0), EntityType.ARMOR_STAND) as ArmorStand
                    stand.setArms(false)
                    stand.isMarker = true
                    stand.isVisible = false
                    stand.isCustomNameVisible = true
                    stand.customName(ser.deserialize(it))
                } else {
                    // first entry should be the name of the NPC,
                    // in order to utilize his nameplate which otherwise
                    // would be empty.
                    profile.name = it
                }
            }
        } else {
            profile.name = config.name
        }

        profile.uniqueId = uuid
        profile.setProperty(Profile.Property("textures", config.textureValue, config.textureSignature))
        profile.complete()

        val npc = NPC.builder()
            .profile(profile)
            .location(config.location)
            .spawnCustomizer { npc, _ -> npc.metadata().queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true).send() }
            .imitatePlayer(false)
            .lookAtPlayer(config.lookAtPlayer)
            .build(pool)

        map[config.id] = npc
    }

    fun get(id: String): NPC? {
        return map[id]
    }
}