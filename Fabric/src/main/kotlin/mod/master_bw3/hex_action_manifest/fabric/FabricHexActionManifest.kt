package mod.master_bw3.hex_action_manifest.fabric

import mod.master_bw3.hex_action_manifest.HexActionManifest
import net.fabricmc.api.ModInitializer

object FabricHexActionManifest : ModInitializer {
    override fun onInitialize() {
        HexActionManifest.init()
    }
}
