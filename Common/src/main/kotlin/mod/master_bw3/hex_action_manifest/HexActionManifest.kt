package mod.master_bw3.hex_action_manifest

import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object HexActionManifest {

    const val MODID = "hex_action_manifest"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    fun id(path: String) = Identifier(MODID, path)

    fun init() {
    }
}