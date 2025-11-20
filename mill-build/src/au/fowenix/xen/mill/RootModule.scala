package au.fowenix.xen.mill

import mill._
import os.Path
import scala.reflect.io.Directory

trait RootModule extends Module {
  val root: Path

  def clean() = Task.Command { Directory(root.toIO).deleteRecursively() }
}
