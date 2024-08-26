package mod.master_bw3.hex_action_manifest.fabric.datagen

import at.petrak.hexcasting.common.lib.hex.HexActions
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture


object FabricHexActionManifestDataGen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator?) {
        val pack = generator!!.createPack()

        pack.addProvider(FabricHexActionManifestDataGen::ActionManifestGenerator)
    }

    private class ActionManifestGenerator(
        output: FabricDataOutput,
    ) : HexActionProvider(output) {
        override fun generateManifest(builder: ActionManifestBuilder) {
            HexActions.REGISTRY.entrySet().forEach {
                builder.add(it.component1(), it.component2())
            }
        }
    }
}

