package mod.master_bw3.hex_action_manifest.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import mod.master_bw3.hex_action_manifest.config.HexActionManifestConfig
import net.fabricmc.api.EnvType.CLIENT
import net.fabricmc.api.Environment

@Environment(CLIENT)
object FabricHexActionManifestModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexActionManifestConfig::getConfigScreen)
}
