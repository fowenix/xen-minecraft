package au.fowenix.xen.gradle.internal

import java.io.InputStream
import java.io.OutputStream
import java.security.DigestInputStream
import java.security.MessageDigest

internal fun InputStream.digest(output: OutputStream) = DigestInputStream(
    this,
    MessageDigest.getInstance("SHA-1")
).use { input ->
    input.copyTo(output)
    input.messageDigest.digest().toHexString()
}