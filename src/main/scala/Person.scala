import spray.json._

case class Person(sno: Int, name: String, city: String)

object JsonFormats {
  import DefaultJsonProtocol._

  implicit val personFormat: RootJsonFormat[Person] = jsonFormat3(Person)
}
