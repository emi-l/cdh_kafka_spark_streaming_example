name := "App"
version := "1.0"
scalaVersion := "2.10.2"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"            % "1.3.0-cdh5.4.3" % "provided",
  "org.apache.spark" %% "spark-sql"             % "1.3.0-cdh5.4.3",
  "org.apache.spark" %% "spark-hive"            % "1.3.0-cdh5.4.3",
  "org.apache.spark" %% "spark-streaming"       % "1.3.0-cdh5.4.3",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.3.0-cdh5.4.3",
//  "org.apache.spark" %% "spark-streaming-flume" % "1.3.0-cdh5.4.3",
  "org.apache.spark" %% "spark-mllib"           % "1.3.0-cdh5.4.3",
  "org.apache.kafka"  % "kafka-clients" % "0.8.2.0-kafka-1.3.2",
  "org.json4s" %% "json4s-jackson" % "{latestVersion}"
)

resolvers ++= Seq(
  "JBoss Repository"             at "http://repository.jboss.org/nexus/content/repositories/releases/",
  "Spray Repository"             at "http://repo.spray.cc/",
  "Cloudera Repository"          at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Akka Repository"              at "http://repo.akka.io/releases/",
  "Twitter4J Repository"         at "http://twitter4j.org/maven2/",
  "Apache HBase"                 at "https://repository.apache.org/content/repositories/releases",
  "Twitter Maven Repo"           at "http://maven.twttr.com/",
  "scala-tools"                  at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository"          at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo"         at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
  "Nexusosc"                     at "http://maven.oschina.net/content/groups/public/",
  "Conjars"                      at "http://conjars.org/repo/",
  Resolver.sonatypeRepo("public")
)

assemblyJarName in assembly := "example.jar"

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)               => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml"        => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types"      => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class"      => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html"       => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".thrift"     => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".txt"        => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "version"     => MergeStrategy.first
  case "application.conf"                                  => MergeStrategy.concat
  case "unwanted.txt"                                      => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
