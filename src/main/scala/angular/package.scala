import typings.angularCore.mod.Type

import scala.scalajs.js

package object angular {

  /**
    * Get the Type[T] of a Class, by calling js.constructorOf
    */
  object typeOf {
    @inline def apply[T <: js.Any](
        implicit tag: js.ConstructorTag[T]
    ): Type[T] = tag.constructor.asInstanceOf[Type[T]]

    @inline def any[T <: js.Any](
        implicit tag: js.ConstructorTag[T]
    ): Type[js.Any] =
      tag.constructor.asInstanceOf[Type[js.Any]]
  }

}
