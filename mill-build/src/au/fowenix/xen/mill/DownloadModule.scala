package au.fowenix.xen.mill

import mill._
import os.Path

trait DownloadModule extends Module {
  def directory: Path

  val hash: String
  val path: String
  val url: String

  def resolved = Task { download(directory / path, hash, url) }
}
