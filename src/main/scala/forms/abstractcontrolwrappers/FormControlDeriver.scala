package forms.abstractcontrolwrappers

import utils.JSObjectEncoder
import typings.angularForms.mod.FormControl

sealed trait FormControlDeriver[T] extends AbstractControlDeriver[T] {

  def build(t: T)(implicit encoder: JSObjectEncoder[T]): FormControl =
    new FormControl(encoder(t))

  override def buildEmpty: FormControl = new FormControl

}

object FormControlDeriver {

  def factory[T]: FormControlDeriver[T] = new FormControlDeriver[T] {}

  implicit def forString: FormControlDeriver[String]   = factory
  implicit def forInt: FormControlDeriver[Int]         = factory
  implicit def forBoolean: FormControlDeriver[Boolean] = factory
  implicit def forLong: FormControlDeriver[Long]       = factory
  implicit def forDouble: FormControlDeriver[Double]   = factory

}
