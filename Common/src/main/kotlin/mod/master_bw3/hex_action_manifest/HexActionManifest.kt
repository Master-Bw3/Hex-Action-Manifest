package mod.master_bw3.hex_action_manifest

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import at.petrak.hexcasting.datagen.recipe.HexplatRecipes

object HexActionManifest {

    const val MODID = "hex_action_manifest"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
    }
}