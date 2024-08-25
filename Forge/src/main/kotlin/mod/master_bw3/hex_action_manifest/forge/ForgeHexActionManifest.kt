package mod.master_bw3.hex_action_manifest.forge

import dev.architectury.platform.forge.EventBuses
import mod.master_bw3.hex_action_manifest.HexActionManifest
import mod.master_bw3.hex_action_manifest.forge.datagen.HexActionManifestModels
import mod.master_bw3.hex_action_manifest.forge.datagen.HexActionManifestRecipes
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(HexActionManifest.MODID)
class HexActionManifestForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(HexActionManifest.MODID, this)
            addListener(ForgeHexActionManifestClient::init)
            addListener(::gatherData)
        }
        HexActionManifest.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            val efh = existingFileHelper
            addProvider(includeClient()) { HexActionManifestModels(it, efh) }
            addProvider(includeServer()) { HexActionManifestRecipes(it) }
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
