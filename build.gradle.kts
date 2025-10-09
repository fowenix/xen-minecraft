plugins {
    kotlin("jvm") version libs.versions.kotlin
    id("xen")
}

group = "au.fowenix.xen"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("au.fowenix.xen:xen-common")
}

repositories {
    mavenCentral()
}

xen {
    assets = gradle.gradleUserHomeDir.resolve("caches/xen/assets")
    build = layout.buildDirectory.dir("xen")

    assetIndex {
        hash = "1863782e33ce7b584fc45b037325a1964e095d3e"
        file = assets.file("indexes/1.7.10.json")
        uri = uri("https://launchermeta.mojang.com/v1/packages/1863782e33ce7b584fc45b037325a1964e095d3e/1.7.10.json")
    }

    client {
        hash = "e80d9b3bf5085002218d4be59e668bac718abbc6"
        file = build.file("client.jar")
        uri = uri("https://launcher.mojang.com/v1/objects/e80d9b3bf5085002218d4be59e668bac718abbc6/client.jar")
    }

    server {
        hash = "952438ac4e01b4d115c5fc38f891710c4941df29"
        uri = uri("https://launcher.mojang.com/v1/objects/952438ac4e01b4d115c5fc38f891710c4941df29/server.jar")
        file = build.file("server.jar")
    }
}