import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.{Sink, Flow}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import JsonFormats._
import spray.json._

object KafkaConsumerApp {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "kafkaConsumerSystem")
    import system.executionContext

    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(sys.env.get("BROKER_HOST").getOrElse("localhost")+":9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    Consumer.plainSource(consumerSettings, Subscriptions.topics("people-topic"))
      .map(_.value().parseJson.convertTo[Person]) // Convert JSON string to Person
      .runWith(Sink.foreach(person => println(s"Received person: $person")))
  }
}
