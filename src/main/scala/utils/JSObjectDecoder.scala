package utils

import magnolia._

import scala.collection.BuildFrom
import scala.language.experimental.macros
import scala.scalajs.js

/**
  * Summon an instance of [[JSObjectDecoder]] to extract from a JavaScript object the necessary information to create
  * an instance of A.
  *
  * @example
  *          ```
  *          case class Bar(s: String, b: Boolean)
  *          case class Foo(i: Int, bar: Bar)
  *
  *          implicitly[JSObjectDecoder[Foo] ].decode(
  *            js.Dynamic.literal(i = 3, bar = js.Dynamic.literal(s = "hello", b = true)
  *          )
  *          // returns Foo(3, Bar("hello", true))
  *          ```
  */
trait JSObjectDecoder[A] {

  @inline final def apply(obj: js.Any): A = decode(obj)
  def decode(obj: js.Any): A

}

object JSObjectDecoder {

  /** Summoner */
  def apply[T](implicit d: JSObjectDecoder[T]): JSObjectDecoder[T] = d

  /** Factory */
  def factory[A](decoder: js.Any => A): JSObjectDecoder[A] =
    (obj: js.Any) => decoder(obj)

  private def caster[A]: JSObjectDecoder[A] = factory(_.asInstanceOf[A])

  implicit def stringJSDecoder: JSObjectDecoder[String] = caster
  implicit def intJSDecoder: JSObjectDecoder[Int] = caster
  implicit def booleanJSDecoder: JSObjectDecoder[Boolean] = caster

  implicit def seqJSDecoder[M[_] <: scala.collection.immutable.Seq[_], A](
    implicit bf: BuildFrom[List[A], A, M[A]],
    aDecoder: JSObjectDecoder[A]
  ): JSObjectDecoder[M[A]] = factory(
    obj =>
      bf.fromSpecific(List())(
        obj.asInstanceOf[js.Array[js.Any]].map(any => aDecoder.decode(any))
    )
  )

  type Typeclass[A] = JSObjectDecoder[A]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] =
    factory[T] { obj =>
      caseClass.construct { param =>
        param.typeclass.decode(
          obj.asInstanceOf[js.Dynamic].selectDynamic(param.label)
        )
      }
    }

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

}
