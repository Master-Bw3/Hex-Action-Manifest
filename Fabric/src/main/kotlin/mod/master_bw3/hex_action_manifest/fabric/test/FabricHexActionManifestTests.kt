package mod.master_bw3.hex_action_manifest.fabric.test

import mod.master_bw3.hex_action_manifest.HexActionManifest
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest
import net.minecraft.test.GameTest
import net.minecraft.test.TestContext


class FabricHexActionManifestTests : FabricGameTest {
    companion object {
        @JvmStatic
        @GameTest(templateName = "empty")
        fun loggerTest(context: TestContext) {
            HexActionManifest.LOGGER.info("hello test world!")
            context.complete()
        }
    }
}