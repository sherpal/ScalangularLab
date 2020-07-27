package forms.abstractcontrolwrappers

import typings.angularForms.mod.FormArray

import scala.scalajs.js

trait FormArrayDeriver[M[t] <: scala.collection.immutable.Seq[t], A]
    extends AbstractControlDeriver[M[A]] {

  def buildEmpty: FormArray = new FormArray(js.Array())

  def build(
    m: M[A]
  )(implicit aControlDeriver: AbstractControlDeriver[A]): FormArray = {
    val fa = buildEmpty
    m.foreach { _ =>
      fa.insert(fa.length, aControlDeriver.buildEmpty)
    }
    fa
  }

}

object FormArrayDeriver {

  implicit def formArrayDeriver[M[t] <: scala.collection.immutable.Seq[t], A]
    : FormArrayDeriver[M, A] =
    new FormArrayDeriver[M, A] {}

}
