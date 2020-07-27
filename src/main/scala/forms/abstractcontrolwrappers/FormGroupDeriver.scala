package forms.abstractcontrolwrappers

import utils.JSObjectEncoder
import typings.angularForms.mod.FormGroup

trait FormGroupDeriver[T] extends AbstractControlDeriver[T] {

  final def build(t: T)(implicit encoder: JSObjectEncoder[T]): FormGroup = {
    val fg = buildEmpty
    fg.setValue(encoder(t))
    fg
  }

  override def buildEmpty: FormGroup

}
