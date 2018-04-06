package SparkApps

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KConsumer {

  def main(args: Array[String]): Unit = {
  /*  val spark: SparkSession = SparkSession.builder.
      master("local")
      .appName("sparkSession")
      .getOrCreate()
      */

    val conf = new SparkConf().setAppName("KConsumer").setMaster("local")
    val ssc = new StreamingContext(conf, Seconds(180))
    val topicsSet = "test"
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group",
      "auto.offset.reset" -> "latest"
    )
    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )


    stream.print()
    ssc.start()
    ssc.awaitTermination()
    /* val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val messages = KafkaUtils.createDirectStream[String, String](ssc, kafkaParams, topicsSet)
    println(messages)


        val zkhosts = "localhost"
        val zkports = "2181"
        val numberOfReceivers = 1
        println("Listening")

        val kafkaProperties: Map[String, String] =
          Map("zookeeper.hosts" -> zkhosts,
            "zookeeper.port" -> zkports,
            "kafka.topic" -> topic,
            "kafka.consumer.id" -> "kafka-consumer",
            "bootstrap.servers" -> "9092",
            "max.poll.records" -> "250",
            "consumer.fillfreqms" -> "1000")

        val props = new java.util.Properties()
        kafkaProperties foreach { case (key,value) => props.put(key, value)}
        val tmp_stream = ReceiverLauncher.launch(ssc, props, numberOfReceivers,StorageLevel.MEMORY_ONLY)
        val partitonOffset_stream = ProcessedOffsetManager.getPartitionOffset(tmp_stream, props)
        println("Listening")
        tmp_stream.foreachRDD(rdd => {
          println("\n\nNumber of records in this batch : " + rdd.count())
        } )
        ProcessedOffsetManager.persists(partitonOffset_stream, props)
        ssc.start()
        ssc.awaitTermination()
    */

  }
}