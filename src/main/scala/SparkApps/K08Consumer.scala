package SparkApps
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.kafka.KafkaUtils
import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.SparkConf
import org.apache.kafka.common.serialization.StringDeserializer



object K08Consumer {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val topics = Set("test")
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val messages = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,topics)
    messages.print()
    ssc.start()
    ssc.awaitTermination()



  }

}
