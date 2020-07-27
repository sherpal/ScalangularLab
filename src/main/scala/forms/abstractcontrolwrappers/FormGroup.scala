package forms.abstractcontrolwrappers

import typings.angularForms.mod.{FormGroup => AngularFormGroup}
import typings.rxjs.internalObservableMod
import typings.rxjs.rxMod.Observable
import utils.JSObjectCodec

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.timers.setTimeout

/**
  * A [[FormGroup]] is a type-safe, auto-derived wrapper around an Angular [[AngularFormGroup]].
  *
  * This class is build using a js object encoder and decoder, and an [[AbstractControlDeriver]]. The
  * [[AbstractControlDeriver]] must actually be a [[forms.abstractcontrolwrappers.FormGroupDeriver]]. To prove that
  * it is indeed the case, we ask for an implicit [[CanBeFormGroup]] parameter. For type parameters `T` such that the
  * derived [[AbstractControlDeriver]] type class is not a [[forms.abstractcontrolwrappers.FormGroupDeriver]], the
  * compiler will not be able to provide the proof (see the definition of [[CanBeFormGroup]] to see how it's handled).
  *
  * @param maybeInitialValue Some an initial value if needed.
  * @param canBeFormGroup proof that the [[AbstractControlDeriver]] is actually a
  *                       [[forms.abstractcontrolwrappers.FormGroupDeriver]]
  * @param codec container for
  *              - encoder for writing instances of T as js objects
  *              - decoder for constructing instances of T from js objects
  * @param abstractControlDeriver instance of the abstract control builder.
  * @tparam T type of elements represented by this form group.
  */
final class FormGroup[T] private (maybeInitialValue: Option[T])(
    implicit canBeFormGroup: CanBeFormGroup[T],
    codec: JSObjectCodec[T],
    abstractControlDeriver: AbstractControlDeriver[T]
) {

  private val encoder = codec.encoder
  private val decoder = codec.decoder

  private val angularFormGroup: AngularFormGroup =
    abstractControlDeriver.buildEmpty.asInstanceOf[AngularFormGroup]

  // Exposed so that it can be added in Angular's html file.
  @JSExport def formGroup(): AngularFormGroup = angularFormGroup

  @JSExport def patchValue(t: T): Unit = angularFormGroup.patchValue(encoder(t))

  @JSExport def setValue(t: T): Unit = angularFormGroup.setValue(encoder(t))

  private val _valueChanges = angularFormGroup.valueChanges
    .asInstanceOf[Observable[js.Any]]
    .pipe(
      typings.rxjs.operatorsMod
        .map[js.Any, T]((any: js.Any, _: Double) => decoder(any))
    )

  @JSExport def valueChanges(): internalObservableMod.Observable[T] = _valueChanges

  @JSExport def value(): T = decoder(angularFormGroup.value)

  setTimeout(0) {
    maybeInitialValue.foreach(patchValue)
  }

}

object FormGroup {

  def apply[T]()(
      implicit canBeFormGroup: CanBeFormGroup[T],
      codec: JSObjectCodec[T],
      abstractControlDeriver: AbstractControlDeriver[T]
  ): FormGroup[T] = new FormGroup[T](Option.empty)

  def apply[T](t: T)(
      implicit canBeFormGroup: CanBeFormGroup[T],
      codec: JSObjectCodec[T],
      abstractControlDeriver: AbstractControlDeriver[T]
  ): FormGroup[T] = new FormGroup[T](Some(t))

}
