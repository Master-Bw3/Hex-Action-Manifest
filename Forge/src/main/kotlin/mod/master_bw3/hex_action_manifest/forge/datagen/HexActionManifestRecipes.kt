package mod.master_bw3.hex_action_manifest.forge.datagen

import at.petrak.hexcasting.api.mod.HexTags
import at.petrak.paucal.api.datagen.PaucalRecipeProvider
import mod.master_bw3.hex_action_manifest.HexActionManifest
import mod.master_bw3.hex_action_manifest.registry.HexActionManifestItems
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import java.util.function.Consumer

// we use Paucal's recipe provider as a base because it has a bunch of helpful stuff
class HexActionManifestRecipes(output: PackOutput) : PaucalRecipeProvider(output, HexActionManifest.MODID) {
    override fun buildRecipes(writer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HexActionManifestItems.DUMMY_ITEM.value)
            .define('S', Items.STICK)
            .define('A', Blocks.AMETHYST_BLOCK)
            .pattern("  A")
            .pattern(" S ")
            .pattern("S  ")
            .unlockedBy("has_item", hasItem(HexTags.Items.STAVES))
            .save(writer)
    }
}
