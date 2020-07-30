# Scalangular Lab

This project aims to serve as a laboratory for testing using and developing Angular features with Scala.

## Why?

Some Angular or TypeScript features leave to be desired, or could really enjoy a nice layer of Scala sugar on top. One example is the fact that Angular `FormGroup`s are not type safe (in any way). The project explores different possibilities to overcome these issues.

## How?

The project uses [Scala.js](https://www.scala-js.org/) to compiles Scala code into JavaScript, then uses [scala-ts](https://github.com/swachter/scala-ts) to generate the corresponding TypeScript declaration file.

Typings of used JS/TS libraries are automatically generated using [ScalablyTyped](https://scalablytyped.org/docs/readme.html). If said tyings do not work, or are too far away from a desired ergonomics, manual typings are defined in the `facades` packages.

### What about annotations?

Angular uses some annotations to do quite a lot of shenanigans at runtime. Since annotations are not part of the specifications of the TypeScript language, we need to understand what they actually do. It turns out that they simply add a static js array `annotations`. In Scala, achieving this is done by declaring a variable `annotations` in the companion object of the class, and annotate it with `@JSExportStatic`.

### What about Dependency Injection (DI)?

Given how the type erasure of TypeScript does, no types are available at runtime. Which means that again (see the "What about annotations?" section above), Angular has to do some trick to keep the types of the constructors of classes somewhere, so that it can know what to inject there.

In order to do that, Angular adds a static js array `parameters` filled with the types of the arguments in the constructor (in order they appear). In Scala, this is done by creating a `js.Array[Type[_]]` in the companion object of the class, and annotate it with `@JSExportStatic`.

Instances of `Type[_]` corresponding to js classes can be created using the helper function `angular.typeOf`. Credits to [Angular's ScalablyTyped demo](https://github.com/ScalablyTyped/Demos/blob/master/angular/src/main/scala/demo/package.scala) for its implementation.

## Philosophy

Each top level package corresponds to a theme that we explored in the process. The `utils` package contains some general facilities and the `facades` package contains hand-written typings for JS/TS libraries.

## JS Object Codec

The package `utils` contains three special type classes `JSObjectEncoder`, `JSObjectDecoder` and one grouping them both, `JSObjectCodec`. This type classes play similar role to JSON encoder and decoder, except that they encode to and decode from raw JS object. They are very helpful when Scala needs to talk to an untyped TypeScript API such as, for example, the `HttpClient` or `FormGroup`s.

## Themes

### Directives

#### Getting Started

The `directives.gettingstarted` package contains very simple examples of creating Angular directives from Scala.

#### SimpleScalaDirective

That directive is a first PoC that it can work. The directive, in itself, does two things:

- set the background colour to red
- prints some friendly message to the console.

Note that the directive takes as argument the `ElementRef`, for which ScalablyTyped has put a type parameter. A priori, the only type parameter that we can safely put is `HTMLElement` since the directive could in principle be used on anything.

### Forms

#### Reactive forms

Angular [Reactive Forms](https://angular.io/guide/reactive-forms) allow one to bind form input to JavaScript objects, with a small reactive interface on top. The main issue of that library is that all types vanish in the process, making it extremely weak to refactoring, and unpleasant to work with on a larger scale.

The `forms.abstractcontrolwrapper` packages builds a Scala layer on top of that to overcome that difficulty.

##### How does it work

The layer right on top of Angular `FormGroup` is the class `FormGroup[T]` in the `forms.abstractcontrolwrapper` package. Instances of `FormGroup[T]` come with automatically derived (by the compiler) type classes helping to fill the gap between the untyped and unsafe raw `FormGroup` to a nicer typed interface.

In order to use it, you simply have to define your form model as Scala case classes that you expose to JS (via `@JSExportTopLevel` and `@JSExportAll` annotations). Then, you use the automatic derivation of `FormGroup[T]` to expose such methods to JS. You are forced to do it this way because the TypeScript compiler is not powerful enough to feed Scala with implicit parameters.

##### Usage

In the `forms.abstractcontrolwrappers.usage`, you'll see a class `BookStore` exporting everything that TypeScript needs for automatically generating `FormGroups` for that class.

TypeScript usage of the `BookStore` forms can be found in the `ScalaFormGroupDemoComponent` component.

##### Limitation

The current implementation currently does not expose a typed layer for dealing with arrays. That means that if your model has lists, you will need to access the `FormArray`s with the unsafe `get` api of form groups.
