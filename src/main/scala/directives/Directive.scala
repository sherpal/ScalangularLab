package directives

import typings.angularCore.mod.DirectiveCls

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

final class Directive(_selector: String, inputMembers: List[String] = Nil) extends DirectiveCls {

  selector = _selector

  if (inputMembers.nonEmpty) {
    inputs = inputMembers.toJSArray.asInstanceOf[js.UndefOr[js.Array[String]]]
  }

}
