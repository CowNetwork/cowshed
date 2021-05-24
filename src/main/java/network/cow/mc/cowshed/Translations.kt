package network.cow.mc.cowshed

/**
 * @author Benedikt Wüller
 */
object Translations {

    val JOIN_MESSAGES = (0..12).map { "cowshed.join.$it" }

    object Sonic {
        const val ITEM_NAME = "cowshed.sonic.item_name"
    }

    object Minecraft {
        object Build {
            const val MESSAGE_STATE = "minecraft.build.message.state"
            const val MESSAGE_ENABLED = "minecraft.build.message.enabled"
            const val MESSAGE_DISABLED = "minecraft.build.message.disabled"

            const val STATE_ENABLED = "minecraft.build.state.enabled"
            const val STATE_DISABLED = "minecraft.build.state.disabled"
        }

        object Fly {
            const val MESSAGE_STATE = "minecraft.fly.message.state"
            const val MESSAGE_ENABLED = "minecraft.fly.message.enabled"
            const val MESSAGE_DISABLED = "minecraft.fly.message.disabled"

            const val STATE_ENABLED = "minecraft.fly.state.enabled"
            const val STATE_DISABLED = "minecraft.fly.state.disabled"
        }
    }

    object Common {
        object State {
            const val ENABLED = "common.state.enabled"
            const val DISABLED = "common.state.disabled"
        }

        object Command {
            const val ERROR_REQUIRES_PLAYER = "common.command.error.requires_player"
            const val ERROR_PLAYER_NOT_FOUND = "common.command.error.player_not_found"
        }
    }

}
