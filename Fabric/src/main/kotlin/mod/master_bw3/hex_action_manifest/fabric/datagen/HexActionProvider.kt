package mod.master_bw3.hex_action_manifest.fabric.datagen

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.math.HexPattern
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import java.nio.file.Path
import java.util.*
import java.util.concurrent.CompletableFuture

abstract class HexActionProvider protected constructor(
    protected val dataOutput: FabricDataOutput,
) :
    DataProvider {
    /**
     * Implement this method to register actions.
     *
     *
     * Call [ActionManifestBuilder.add] to add an action.
     */
    abstract fun generateManifest(builder: ActionManifestBuilder)

    override fun run(writer: CachedOutput): CompletableFuture<*> {
        val actionEntries = TreeMap<ResourceLocation, ActionRegistryEntry>()

        generateManifest(ActionManifestBuilder { key: ResourceLocation, value: ActionRegistryEntry ->
            Objects.requireNonNull(key)
            Objects.requireNonNull(value)

            if (actionEntries.containsKey(key)) {
                throw RuntimeException("Existing action key found - $key - Duplicate will be ignored.")
            }
            actionEntries[key] = value
        })

        val actions = actionEntries.map {
            val key = it.component1()
            val value = it.component2()

            ActionInfo.of(key, value.prototype)
        }

        val json = ActionInfo.CODEC.listOf().encodeStart(JsonOps.INSTANCE, actions).resultOrPartial {}.orElseThrow()


        return DataProvider.saveStable(writer, json, getFilePath())
    }

    private fun getFilePath(): Path {
        return dataOutput
            .createPathProvider(PackOutput.Target.DATA_PACK, "hex_actions")
            .json(ResourceLocation(dataOutput.modId, "manifest"))
    }

    override fun getName(): String {
        return "(Action Manifest)"
    }


    fun interface ActionManifestBuilder {
        /**
         * Adds an action.
         *
         * @param key            The key of the action.
         * @param value          The value of the entry.
         */
        fun add(key: ResourceLocation, value: ActionRegistryEntry)

        fun add(key: ResourceKey<ActionRegistryEntry>, value: ActionRegistryEntry) {
            add(key.location(), value)
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
        fun of(id: ResourceLocation, pattern: HexPattern): ActionInfo {
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
    constructor(key: String) : this(key, Component.translatable(key).string)

    companion object {
        val CODEC: Codec<TranslationInfo> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("key").forGetter(TranslationInfo::key),
                Codec.STRING.fieldOf("en_us").forGetter(TranslationInfo::en),
            ).apply(it, ::TranslationInfo)
        }
    }
}