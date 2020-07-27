# Scalangular Lab

This project aims to serve as a laboratory for testing using and developing Angular features with Scala.

## Why?

Some Angular or TypeScript features leave to be desired, or could really enjoy a nice layer of Scala sugar on top. One example is the fact that Angular `FormGroup`s are not type safe (in any way). The project explores different possibilities to overcome these issues.

## How?

The project uses [Scala.js](https://www.scala-js.org/) to compiles Scala code into JavaScript, then uses [scala-ts](https://github.com/swachter/scala-ts) to generate the corresponding TypeScript declaration file.

Typings of used JS/TS libraries are automatically generated using [ScalablyTyped](https://scalablytyped.org/docs/readme.html). If said tyings do not work, or are too far away from a desired ergonomics, manual typings are defined in the `facades` packages.

## Philosophy

Each top level package corresponds to a theme that we explored in the process. The `utils` package contains some general facilities and the `facades` package contains hand-written typings for JS/TS libraries.

## Themes

### Forms

#### Reactive forms

Angular [Reactive Forms](https://angular.io/guide/reactive-forms) allow one to bind form input to JavaScript object, with as small reactive interface on top. The main issue of that library is that all types vanish in the process, making it extremely weak to refactoring, and unpleasant to work with on a larger scale.

The `forms.abstractcontrolwrapper` packages builds a Scala layer on top of that to overcome that difficulty.

##### How does it work

The layer right on top of Angular `FormGroup` is the class `FormGroup[T]` in the `forms.abstractcontrolwrapper` package. Instances of `FormGroup[T]` come with automatically derived (by the compiler) type classes helping to fill the gap between the untyped and unsafe raw `FormGroup` to a nicer typed interface.

In order to use it, you simply have to define your form model as Scala case classes that you expose to JS (via `@JSExportTopLevel` and `@JSExportAll` annotations). Then, you use the automatic derivation of `FormGroup[T]` to expose such methods to JS. You are forced to do it this way because the TypeScript compiler is not powerful enough to feed Scala with implicit parameters.

##### Usage

In the `forms.abstractcontrolwrappers.usage`, you'll see a class `BookStore` exporting everything that TypeScript needs for automatically generating `FormGroups` for that class.

TypeScript usage of the `BookStore` forms can be found in the `ScalaFormGroupDemoComponent` component.
