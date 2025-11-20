package au.fowenix.xen.mill

import java.io._, java.security._
import mill._, mill.util.HexFormat
import scala.util.Using

private def digest(inputStream: InputStream, outputStream: OutputStream) = Using.Manager { use =>
  val input = use(DigestInputStream(inputStream, MessageDigest.getInstance("SHA-1")))
  val output = use(outputStream)

  input.transferTo(output)

  HexFormat.bytesToHex(input.getMessageDigest().digest())
}.get

private def download(dest: os.Path, hash: String, url: String) = {
  def validate(found: String) = require(found == hash, s"invalid hash (found: $found, expected: $hash)")

  val file = dest.toIO

  if (file.exists()) validate(digest(FileInputStream(file), OutputStream.nullOutputStream()))

  file.getParentFile.mkdirs()

  validate(requests.get(url).readBytesThrough(digest(_, FileOutputStream(file))))

  PathRef(dest)
}
