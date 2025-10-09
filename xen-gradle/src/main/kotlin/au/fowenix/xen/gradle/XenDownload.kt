package au.fowenix.xen.gradle

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import java.net.URI

abstract class XenDownload {
    abstract val hash: Property<String>
    abstract val file: RegularFileProperty
    abstract val uri: Property<URI>
}