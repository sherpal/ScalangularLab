package forms.abstractcontrolwrappers

import scala.annotation.implicitAmbiguous
import scala.language.higherKinds
import scala.scalajs.js

trait CanBeFormGroup[-T]

object CanBeFormGroup extends CanBeFormGroup[Any] {

  implicit def canBeFormGroup[E]: CanBeFormGroup[E] = this

  // Provide multiple ambiguous values so an implicit CanFail[Nothing] cannot be found.
  @implicitAmbiguous(
    "You may not build FormGroup from Nothing. Perhaps the compiler inferred something wrong? " +
      "Try to explicitly type what you have on your plate to see if it fixes it."
  )
  implicit val canBeFormGroupAmbiguous1Nothing: CanBeFormGroup[Nothing] =
    CanBeFormGroup
  implicit val canBeFormGroupAmbiguous2Nothing: CanBeFormGroup[Nothing] =
    CanBeFormGroup

  @implicitAmbiguous(
    "You may not build a FormGroup from a native JavaScript object. Only case classes are allowed."
  )
  implicit def canBeFormGroupAmbiguous1JSAny[T <: js.Any]: CanBeFormGroup[T] =
    CanBeFormGroup
  implicit def canBeFormGroupAmbiguous2JSAny[T <: js.Any]: CanBeFormGroup[T] =
    CanBeFormGroup

  @implicitAmbiguous(
    "Types emitting FormControl instead of FormGroups are not allowed to build FormGroups."
  )
  implicit def canBeFormGroupAmbiguous1IfFormControlExists[T](
    implicit formControlDeriver: FormControlDeriver[T]
  ): CanBeFormGroup[T] = CanBeFormGroup
  implicit def canBeFormGroupAmbiguous2IfFormControlExists[T](
    implicit formControlDeriver: FormControlDeriver[T]
  ): CanBeFormGroup[T] = CanBeFormGroup

  @implicitAmbiguous("Seq types are not allowed to build FormGroups.")
  implicit def canBeFormGroupAmbiguous1IfFormArrayExists[M[t] <: scala.collection.immutable.Seq[
    t
  ], T](
    implicit formArrayDeriver: FormArrayDeriver[M, T]
  ): CanBeFormGroup[M[T]] = CanBeFormGroup
  implicit def canBeFormGroupAmbiguous2IfFormArrayExists[M[t] <: scala.collection.immutable.Seq[
    t
  ], T](
    implicit formArrayDeriver: FormArrayDeriver[M, T]
  ): CanBeFormGroup[M[T]] = CanBeFormGroup

}
