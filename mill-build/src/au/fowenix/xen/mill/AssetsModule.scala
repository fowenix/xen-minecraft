package au.fowenix.xen.mill

import java.util.concurrent.Executors
import mill._, mill.scalalib._, mill.api.JsonFormatters._
import os.{Path, SubPath}
import scala.concurrent._, scala.util.Using
import upickle.implicits.namedTuples.default.given

trait AssetsModule extends RootModule {
  val index: DownloadModule

  def resolved = Task {
    type Index = Map[String, (hash: String, size: String)]

    val file = index.resolved().path.toIO
    val objects: Index = upickle.default.read[(objects: Index)](file).objects

    Using(Executors.newVirtualThreadPerTaskExecutor()) { executor =>
      given ExecutionContext = ExecutionContext.fromExecutor(executor)

      objects.foreach { case (name, (hash, _)) =>
        println(s"Downloading: $name => $hash")

        Future {
          val path = s"${hash.take(2)}/$hash"
          val dest = root / "objects" / SubPath(path)

          download(dest, hash, s"https://resources.download.minecraft.net/$path")
        }
      }
    }

    root
  }
}
