import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { ScalaFormGroupDemoComponent } from "./forms/abstractcontrolwrapper/scala-form-group-demo/scala-form-group-demo.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { UsingScalaDirectiveComponent } from "./directives/gettingstarted/using-scala-directive/using-scala-directive.component";
import { SimpleScalaDirective } from "scalangularlab";

@NgModule({
  declarations: [
    AppComponent,
    ScalaFormGroupDemoComponent,
    UsingScalaDirectiveComponent,
    SimpleScalaDirective,
  ],
  imports: [BrowserModule, AppRoutingModule, ReactiveFormsModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
