package au.fowenix.xen.gradle.tasks

import au.fowenix.xen.gradle.internal.download
import au.fowenix.xen.gradle.internal.getValue
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.submit
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import org.gradle.workers.WorkerExecutor
import java.net.URI
import javax.inject.Inject

abstract class DownloadAssets : DefaultTask() {
    @get:Inject
    protected abstract val workerExecutor: WorkerExecutor

    @get:InputFile
    abstract val assetIndex: RegularFileProperty

    @get:OutputDirectory
    abstract val assets: DirectoryProperty

    @TaskAction
    protected fun download() {
        val assetIndex by assetIndex.asFile.map(JsonSlurper()::parse)

        val assets by assets
        val objects = assets.dir("objects")

        val queue = workerExecutor.noIsolation()

        @Suppress("UNCHECKED_CAST")
        (assetIndex as Map<String, Map<String, Map<String, String>>>)["objects"]!!.forEach { (name, data) ->
            val hash by data

            queue.submit(Action::class) {
                this.hash = hash
                this.name = name
                this.objects = objects
            }
        }

        queue.await()
    }

    protected abstract class Action : WorkAction<Action.Parameters> {
        override fun execute() {
            val hash by parameters.hash
            val path = "${hash.take(2)}/$hash"
            val file by parameters.objects.file(path).map { it.asFile }
            val name by parameters.name

            val uri = URI("https://resources.download.minecraft.net/$path")

            uri.download(file, hash, "$name -> $hash")
        }

        interface Parameters : WorkParameters {
            val hash: Property<String>
            val name: Property<String>
            val objects: DirectoryProperty
        }
    }
}