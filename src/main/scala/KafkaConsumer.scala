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
Failure handling in Kafka
Broker failure: Kafka is a highly available, persistent, durable system where every message written to a partition is persisted and replicated some number of times (we will call it n). As a result, Kafka can tolerate n-1 broker failures, meaning that a partition is available as long as there is at least one broker available. Kafka’s replication protocol guarantees that once a message has been written successfully to the leader replica, it will be replicated to all available replicas.

Client consumer failure: Exactly-once delivery must account for client failures as well. But how can we know that a client has actually failed and is not just temporarily partitioned from the brokers or undergoing an application pause? Being able to tell the difference between a permanent failure and a soft one is important; for correctness, the broker should discard messages sent by a zombie producer. Same is true for the consumer; once a new client instance has been started, it must be able to recover from whatever state the failed instance left behind and begin processing from a safe point. This means that consumed offsets must always be kept in sync with produced output.

Producer-to-broker RPC failure: Broker can crash after writing a message but before it sends an ack back to the producer. Producer assuming message was not written successfully and to retries it which can lead data duplicacy.
To overcome this producers are made Idempodent using property “enable.idempotence=true”. When this feature is enabled each batch of messages sent to Kafka will contain a sequence number that the broker will use to dedupe any duplicate send. This sequence number is persisted to the replicated log, so even if the leader fails, any broker that takes over will also know if a resend is a duplicate.
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
