plugins {
    id("hex_action_manifest.java")
}

architectury {
    // this looks up the value from gradle/libs.versions.toml
    minecraft = libs.versions.minecraft.get()
}

tasks {
    register("runAllDatagen") {
        dependsOn(":Forge:runCommonDatagen")
    }
}
