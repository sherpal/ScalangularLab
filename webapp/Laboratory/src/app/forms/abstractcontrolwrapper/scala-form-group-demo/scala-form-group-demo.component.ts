import { Component } from "@angular/core";
import { forms, BookStore, BookStoreStatic } from "scalangularlab";

@Component({
  selector: "app-scala-form-group-demo",
  templateUrl: "./scala-form-group-demo.component.html",
  styleUrls: ["./scala-form-group-demo.component.css"],
})
export class ScalaFormGroupDemoComponent {
  public typedFormGroup: forms.abstractcontrolwrappers.FormGroup<
    BookStore
  > = BookStoreStatic.buildFormGroupWithInitial(BookStoreStatic.unit());

  public formValue$ = this.typedFormGroup.valueChanges();

  constructor() {}
}
