package forms.abstractcontrolwrappers

import magnolia._
import org.scalablytyped.runtime.StringDictionary
import typings.angularForms.mod.{AbstractControl, FormGroup}

import scala.language.experimental.macros
import scala.scalajs.js

trait AbstractControlDeriver[T] {

  def buildEmpty: AbstractControl

}

object AbstractControlDeriver {

  implicit def fromFormControlDeriver[T](
      implicit formControlDeriver: FormControlDeriver[T]
  ): AbstractControlDeriver[T] =
    formControlDeriver

  implicit def fromFormArrayDeriver[M[t] <: scala.collection.immutable.Seq[t], T](
      implicit formArrayDeriver: FormArrayDeriver[M, T]
  ): AbstractControlDeriver[M[T]] =
    formArrayDeriver

  type Typeclass[T] = AbstractControlDeriver[T]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] =
    new FormGroupDeriver[T] {
      override def buildEmpty: FormGroup = {
        val obj = js.Dynamic.literal()

        caseClass.parameters.foreach { param =>
          obj.updateDynamic(param.label)(param.typeclass.buildEmpty)
        }

        new FormGroup(obj.asInstanceOf[StringDictionary[AbstractControl]])
      }
    }

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

}
