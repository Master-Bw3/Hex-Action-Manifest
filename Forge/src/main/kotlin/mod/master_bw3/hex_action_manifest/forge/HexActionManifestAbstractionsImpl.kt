@file:JvmName("HexActionManifestAbstractionsImpl")

package mod.master_bw3.hex_action_manifest.forge

import mod.master_bw3.hex_action_manifest.registry.HexDebugRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HexDebugRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
