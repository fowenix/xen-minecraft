import au.fowenix.xen.gradle.Xen
import au.fowenix.xen.gradle.internal.creating
import au.fowenix.xen.gradle.tasks.DownloadAssets
import au.fowenix.xen.gradle.tasks.DownloadFile

val xen by extensions.creating(Xen::class)

val downloadAssetIndex by tasks.registering(DownloadFile::class) {
    description = "Download asset index"
    group = "xen - download"

    file = xen.assetIndex.file
    hash = xen.assetIndex.hash
    uri = xen.assetIndex.uri
}

val downloadAssets by tasks.registering(DownloadAssets::class) {
    description = "Download asset objects"
    group = "xen - download"

    assetIndex = downloadAssetIndex.flatMap(DownloadFile::file)
    assets = xen.assets
}

val downloadClient by tasks.registering(DownloadFile::class) {
    description = "Download client"
    group = "xen - download"

    file = xen.client.file
    hash = xen.client.hash
    uri = xen.client.uri
}

val downloadServer by tasks.registering(DownloadFile::class) {
    description = "Download server"
    group = "xen - download"

    file = xen.server.file
    hash = xen.server.hash
    uri = xen.server.uri
}