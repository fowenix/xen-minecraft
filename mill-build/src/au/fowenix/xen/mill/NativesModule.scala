package au.fowenix.xen.mill

import mill._, mill.scalalib._
import scala.sys.SystemProperties, scala.util.matching.Regex

trait NativeModule extends RunModule {
  def forkArgs = Task { super.forkArgs() :+ s"-Djava.library.path=${nativeExtracted()}" }

  def nativeClassifier = SystemProperties().get("os.name").map(_.toLowerCase) match
    case Some(name) if name.contains("win")   => "natives-windows"
    case Some(name) if name.contains("mac")   => "natives-osx"
    case Some(name) if name.contains("linux") => "natives-linux"
    case _                                    => ???

  def nativeDeps = Task { Seq[Dep]() }

  def nativeExtracted = Task {
    val dest = Task.dest
    val resolved = millResolver().classpath(deps = nativeDeps())

    resolved.foreach { path =>
      os.unzip(path.path, dest, Seq(Regex("META-INF/.*")))
    }

    dest
  }

  extension (ctx: StringContext)
    def native(args: Any*): Dep = (
      ctx.parts.take(args.length).zip(args).flatMap { case (p, a) => Seq(p, a) } ++ ctx.parts.drop(args.length)
    ).mkString ++ s";classifier=$nativeClassifier"
}
