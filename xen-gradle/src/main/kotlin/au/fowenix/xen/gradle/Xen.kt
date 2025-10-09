package au.fowenix.xen.gradle

import au.fowenix.xen.gradle.internal.creating
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.plugins.ExtensionAware

abstract class Xen : ExtensionAware {
    abstract val assets: DirectoryProperty
    abstract val build: DirectoryProperty

    val assetIndex by extensions.creating(XenDownload::class)
    val client by extensions.creating(XenDownload::class)
    val server by extensions.creating(XenDownload::class)
}