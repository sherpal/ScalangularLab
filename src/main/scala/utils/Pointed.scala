package utils

import magnolia._

import scala.language.experimental.macros
import scala.collection.BuildFrom

/** A type T can be [[Pointed]] if it has a special value. */
trait Pointed[T] {
  def unit: T
}

object Pointed {

  /** Summoner */
  def apply[T](implicit p: Pointed[T]): Pointed[T] = p

  /** Factory */
  def factory[T](t: T): Pointed[T] = new Pointed[T] { def unit: T = t }

  implicit def stringIsPointed: Pointed[String]                          = factory("")
  implicit def numericIsPointed[T](implicit num: Numeric[T]): Pointed[T] = factory(num.zero)

  implicit def booleanIsPointed: Pointed[Boolean] = factory(true) // both choices could be good

  implicit def seqIsPointed[T, M[_] <: scala.collection.immutable.Seq[_]](
      implicit bf: BuildFrom[List[_], T, M[T]]
  ): Pointed[M[T]] = factory(bf.fromSpecific(Nil)(Nil))

  implicit def optionIsPointed[T]: Pointed[Option[T]] = factory(Option.empty[T])

  type Typeclass[A] = Pointed[A]

  def combine[T](caseClass: CaseClass[Typeclass, T]): Typeclass[T] =
    factory[T](caseClass.construct(param => param.typeclass.unit))

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]

}
