package directives.gettingstarted

import angular.typeOf
import directives.Directive
import org.scalajs.dom
import typings.angularCore.mod.{ElementRef, Type}
import typings.std.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportStatic, JSExportTopLevel}

@JSExportTopLevel("SimpleScalaDirective")
final class SimpleScalaDirective(elementRef: ElementRef[HTMLElement]) extends js.Object {

  println("Hello from Scala directive")

  elementRef.nativeElement.style.backgroundColor = "red"

}

object SimpleScalaDirective {

  @JSExportStatic
  final val annotations = js.Array(
    new Directive("[scala-simple-directive]")
  )

  @JSExportStatic
  final val parameters: js.Array[Type[_]] = js.Array(typeOf[ElementRef[HTMLElement]])

}
