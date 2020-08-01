package directives.gettingstarted

import directives.Directive

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportStatic, JSExportTopLevel}

/**
  * A show case that inputs can be used inside a Scala directive.
  *
  * Currently, it is a bit less idiomatic due to the fact that it has to go through the
  * [[directives.Directive]] class definition in the `annotations` array, but a future
  * effort could bring actual Scala annotations to do that at compile time.
  */
@JSExportTopLevel("ScalaDirectiveWithInput")
final class ScalaDirectiveWithInput extends js.Object {

  def scalaNot_=(condition: Boolean): Unit =
    if (condition) println("true") else println("false")

}

object ScalaDirectiveWithInput {

  @JSExportStatic
  final val annotations = js.Array(
    new Directive("[scalaWithInput]", inputMembers = List("scalaNot"))
  )

}
