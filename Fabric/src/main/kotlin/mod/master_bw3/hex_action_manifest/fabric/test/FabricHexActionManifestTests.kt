package mod.master_bw3.hex_action_manifest.fabric.test

import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import com.google.gson.GsonBuilder
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import mod.master_bw3.hex_action_manifest.HexActionManifest
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.test.GameTest
import net.minecraft.test.TestContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.writeText


class FabricHexActionManifestTests : FabricGameTest {
    companion object {
        private val outputFolder: Path =
            FabricLoader.getInstance().gameDir.resolve(Paths.get("game-test/test-results")).also(Files::createDirectories)

        @JvmStatic
        @GameTest(templateName = "empty")
        fun generateManifest(context: TestContext) {

            val actions = HexActions.REGISTRY.entrySet.map {
                val key = it.component1()
                val value = it.component2()

                ActionInfo.of(key.value, value.prototype)
            }

            val json =
                ActionInfo.CODEC.listOf().encodeStart(JsonOps.INSTANCE, actions).resultOrPartial {}.orElseThrow()

            val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
            val jsonString = gson.toJson(json)

            outputFolder.resolve("hex_action_manifest.json").writeText(jsonString)
            context.complete()
        }
    }
}

data class ActionInfo(
    val mod: String,
    val internalName: String,
    val translationInfo: TranslationInfo,
    val startDir: String,
    val pattern: String
) {
    companion object {
        fun of(id: Identifier, pattern: HexPattern): ActionInfo {
            return ActionInfo(
                id.namespace,
                id.path,
                TranslationInfo("hexcasting.action.$id"),
                pattern.startDir.name,
                pattern.anglesSignature()
            )
        }

        val CODEC: Codec<ActionInfo> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("mod").forGetter(ActionInfo::mod),
                Codec.STRING.fieldOf("internal_name").forGetter(ActionInfo::internalName),
                TranslationInfo.CODEC.fieldOf("translation").forGetter(ActionInfo::translationInfo),
                Codec.STRING.fieldOf("start_dir").forGetter(ActionInfo::startDir),
                Codec.STRING.fieldOf("pattern").forGetter(ActionInfo::pattern),
            ).apply(it, ::ActionInfo)
        }
    }
}

data class TranslationInfo(val key: String, val en: String) {
    constructor(key: String) : this(key, Text.translatable(key).string)

    companion object {
        val CODEC: Codec<TranslationInfo> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("key").forGetter(TranslationInfo::key),
                Codec.STRING.fieldOf("en_us").forGetter(TranslationInfo::en),
            ).apply(it, ::TranslationInfo)
        }
    }
}
