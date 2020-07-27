package forms.abstractcontrolwrappers.usage

import forms.abstractcontrolwrappers.FormGroup
import utils.Pointed

import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

@JSExportTopLevel("BookStore")
@JSExportAll
final case class BookStore(owner: String, address: Address)

@JSExportTopLevel("BookStoreStatic")
@JSExportAll
object BookStore {

  def buildFormGroup(): FormGroup[BookStore] = FormGroup[BookStore]

  def buildFormGroupWithInitial(initial: BookStore): FormGroup[BookStore] =
    FormGroup[BookStore](initial)

  def unit(): BookStore = Pointed[BookStore].unit

}
