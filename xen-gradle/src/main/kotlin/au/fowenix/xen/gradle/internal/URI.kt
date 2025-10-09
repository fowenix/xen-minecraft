package au.fowenix.xen.gradle.internal

import org.gradle.api.logging.Logging
import java.io.File
import java.io.OutputStream
import java.net.URI

internal fun URI.download(file: File, hash: String, path: String = file.name) {
    if (file.exists()) {
        LOGGER.lifecycle("Validating: $path")

        if (file.inputStream().digest(OutputStream.nullOutputStream()) == hash) return
    }

    LOGGER.lifecycle("Downloading: $path")

    file.parentFile.mkdirs()
    file.outputStream().use { output ->
        toURL().openStream().digest(output).let { found ->
            require(found == hash) {
                "invalid hash (found: $found, expected: $hash"
            }
        }
    }

    LOGGER.lifecycle("Downloaded: $path")
}

private val LOGGER = Logging.getLogger("download")