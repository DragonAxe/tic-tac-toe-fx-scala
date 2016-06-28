name := "tic-tac-toe-fx"

version := "1.0"

scalaVersion := "2.11.8"

mainClass in Compile := Some("Main")

unmanagedJars in Compile += Attributed.blank(file("/usr/lib/jvm/default/jre/lib/ext/jfxrt.jar"))
