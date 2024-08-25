@file:OptIn(ExperimentalStdlibApi::class)

package mod.master_bw3.hex_action_manifest.forge.datagen

import mod.master_bw3.hex_action_manifest.HexActionManifest
import mod.master_bw3.hex_action_manifest.items.ItemDebugger
import mod.master_bw3.hex_action_manifest.items.ItemDebugger.DebugState
import mod.master_bw3.hex_action_manifest.items.ItemDebugger.StepMode
import mod.master_bw3.hex_action_manifest.items.ItemEvaluator
import mod.master_bw3.hex_action_manifest.items.ItemEvaluator.EvalState
import mod.master_bw3.hex_action_manifest.registry.HexActionManifestItems
import mod.master_bw3.hex_action_manifest.utils.itemPredicate
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelBuilder
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

class HexActionManifestModels(output: PackOutput, efh: ExistingFileHelper) : ItemModelProvider(output, HexActionManifest.MODID, efh) {
    override fun registerModels() {
        basicItem(HexActionManifestItems.DUMMY_ITEM.id)
            .parent(ModelFile.UncheckedModelFile("item/handheld_rod"))
    }
}

// utility function for adding multiple possibly missing layers to a generated item model
fun <T : ModelBuilder<T>> T.layers(start: Int, vararg layers: String?): T {
    var index = start
    for (layer in layers) {
        if (layer != null) {
            texture("layer$index", layer)
            index += 1
        }
    }
    return this
}
