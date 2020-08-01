package directives.gettingstarted

import angular.typeOf
import directives.Directive
import org.scalajs.dom
import typings.angularCore.mod.{ElementRef, Type}
import typings.std.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportStatic, JSExportTopLevel}

/**
  * Angular directives are simple classes annotated with the `@Directive` annotation. This
  * annotation is defined in the companion object of this [[SimpleScalaDirective]].
  *
  * @param elementRef the [[ElementRef]] this directive is attached to. It will be injected
  *                   via Angular DI system, and that is why we need to define the
  *                   `parameters` array in the companion object.
  */
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
