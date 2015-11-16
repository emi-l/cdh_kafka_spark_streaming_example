package example

import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.util.Try

object Example {
  implicit val formats = DefaultFormats

  case class EventJson(id: String, etype: String, ip: String)

  def extractEventJson(line: String): Option[(EventJson, String)] = {
    for {
      parsed <- Try(parse(line)).toOption
      eventJson <- parsed.extractOpt[EventJson]
    } yield (eventJson, line)
  }

  def main(args: Array[String]) {
    if (args.length < 5) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads> <outputPath>")
      System.exit(1)
    }

    val Array(zkQuorum, group, topics, numThreads, outputPath) = args
    val sparkConf = new SparkConf().setAppName("Example")
    val ssc       = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

    // direct
    lines.saveAsTextFiles(outputPath + "/kafkaLines") // hdfs

    // parsed
    lines.flatMap(extractEventJson).foreachRDD { rdd =>
      rdd.saveAsTextFile(outputPath + "/kafkaParsed")
    }

    // filtered
    lines.flatMap(extractEventJson).filter { case(eventJson, _) =>
      eventJson.etype == "pageView"
    }.foreachRDD { rdd =>
      rdd.map(_._2).saveAsTextFile(outputPath + "/kafkaFiltered")
    }

    ssc.start()
    ssc.awaitTermination(1 * 1000 * 60) // ms
  }
}
