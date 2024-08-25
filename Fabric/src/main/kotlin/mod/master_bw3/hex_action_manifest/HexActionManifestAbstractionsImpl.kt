@file:JvmName("HexActionManifestAbstractionsImpl")

package mod.master_bw3.hex_action_manifest.fabric

import mod.master_bw3.hex_action_manifest.registry.HexDebugRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexDebugRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
