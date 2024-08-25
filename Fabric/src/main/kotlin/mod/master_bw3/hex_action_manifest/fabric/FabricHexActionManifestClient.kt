package mod.master_bw3.hex_action_manifest.fabric

import mod.master_bw3.hex_action_manifest.HexActionManifestClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType.CLIENT
import net.fabricmc.api.Environment

@Environment(CLIENT)
object FabricHexActionManifestClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexActionManifestClient.init()
    }
}
