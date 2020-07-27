package utils

import magnolia._

import scala.language.experimental.macros
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

/**
  * Summon an instance of [[JSObjectEncoder]] to be able to transform an instance of type `A` into a JavaScript object
  * representing that instance.
  *
  * @example
  *          ```
  *          case class Bar(s: String, b: Boolean)
  *          case class Foo(i: Int, bar: Bar)
  *
  *          implicitly[JSObjectEncoder[Foo] ].encode(Foo(3, Bar("hello", true)))
  *          // returns { i: 3, bar: { s: "hello", b: true }}
  *          ```
  */
trait JSObjectEncoder[A] {

  @inline final def apply(a: A): js.Any = encode(a)
  def encode(a: A): js.Any

}

object JSObjectEncoder {

  /** Summoner */
  def apply[T](implicit e: JSObjectEncoder[T]): JSObjectEncoder[T] = e

  /** Factory */
  def factory[A](encoder: A => js.Any): JSObjectEncoder[A] =
    (a: A) => encoder(a)

  implicit def stringJSEncoder: JSObjectEncoder[String] = factory(x => x)
  implicit def intJSEncoder: JSObjectEncoder[Int] = factory(x => x)
  implicit def booleanJSEncoder: JSObjectEncoder[Boolean] = factory(x => x)

  implicit def seqJSEncoder[M[t] <: scala.collection.immutable.Seq[t], A](
    implicit tEncoder: JSObjectEncoder[A]
  ): JSObjectEncoder[M[A]] =
    factory((m: M[A]) => m.toJSArray.map((a: A) => tEncoder.encode(a)))

  type Typeclass[A] = JSObjectEncoder[A]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] =
    factory[T] { (t: T) =>
      val obj = js.Dynamic.literal()

      caseClass.parameters.foreach { param =>
        obj.updateDynamic(param.label)(
          param.typeclass.encode(param.dereference(t))
        )
      }

      obj
    }

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

}
