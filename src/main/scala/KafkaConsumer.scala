import java.util
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._

/**
 *
 * @author Danveer
 *
 *
 * Steps: ( macOS
 * brew install kafka
 * cd /usr/local/opt/kafka  and properties file would be in /usr/local/etc/kafka)
 * /usr/local/opt/kafka/bin/zookeeper-server-start /usr/local/etc/kafka/config/zookeeper.properties   (starting zookeeper)
   /usr/local/opt/kafka/bin/kafka-server-start /usr/local/etc/kafka/config/server.properties    (starting broker server)
   /usr/local/opt/kafka/bin/kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic sampleTopic     (creating topic with name as "sampleTopic")
   /usr/local/opt/kafka/bin/kafka-console-producer  --broker-list localhost:9092  --topic sampleTopic   (starting console producer)
   /usr/local/opt/kafka/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic sampleTopic --from-beginning   (starting console consumer)
 */

object KafkaConsumer {

  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(util.Collections.singletonList("sampleTopic"))

  while(true){
    val records=consumer.poll(100)
    for (record<-records.asScala){
      println(record)
    }
  }

}
