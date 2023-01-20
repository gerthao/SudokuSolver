ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "Sudoku"
  )

libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
libraryDependencies += "com.lihaoyi" %% "pprint" % "0.8.1"
libraryDependencies += "com.monovore" %% "decline" % "2.4.1"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
libraryDependencies += "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test