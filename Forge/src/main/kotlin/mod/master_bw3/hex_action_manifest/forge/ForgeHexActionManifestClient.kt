package mod.master_bw3.hex_action_manifest.forge

import mod.master_bw3.hex_action_manifest.HexActionManifestClient
import mod.master_bw3.hex_action_manifest.config.HexActionManifestConfig
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeHexActionManifestClient {
    fun init(event: FMLClientSetupEvent) {
        HexActionManifestClient.init()
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { _, parent -> HexActionManifestConfig.getConfigScreen(parent) }
        }
    }
}
