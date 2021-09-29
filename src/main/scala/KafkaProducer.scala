import java.util.Properties
import org.apache.kafka.clients.producer._

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

object KafkaProducer extends App{

  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  for(i<- 1 to 50){
    val record = new ProducerRecord("sampleTopic", "key", s"hello $i")
    producer.send(record)
  }

  val record = new ProducerRecord("sampleTopic", "key", "the end "+new java.util.Date)
  producer.send(record)
  producer.close()

}
