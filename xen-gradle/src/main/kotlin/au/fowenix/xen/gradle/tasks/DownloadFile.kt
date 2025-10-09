package au.fowenix.xen.gradle.tasks

import au.fowenix.xen.gradle.internal.download
import au.fowenix.xen.gradle.internal.getValue
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.net.URI

abstract class DownloadFile : DefaultTask() {
    @get:Input
    abstract val hash: Property<String>

    @get:OutputFile
    abstract val file: RegularFileProperty

    @get:Input
    abstract val uri: Property<URI>

    @TaskAction
    protected fun download() {
        val hash by hash
        val file by file.asFile
        val uri by uri

        uri.download(file, hash)
    }
}