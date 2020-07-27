package utils

import scala.scalajs.js

/**
  * Simple wrapper around both a [[JSObjectDecoder]] and a [[JSObjectEncoder]].
  */
trait JSObjectCodec[T] {

  def encoder: JSObjectEncoder[T]
  def decoder: JSObjectDecoder[T]

  final def encode(t: T): js.Any = encoder(t)
  final def decode(any: js.Any): T = decoder(any)

}

object JSObjectCodec {

  implicit def fromEncoderAndDecoder[T](
    implicit _encoder: JSObjectEncoder[T],
    _decoder: JSObjectDecoder[T]
  ): JSObjectCodec[T] =
    new JSObjectCodec[T] {
      def encoder: JSObjectEncoder[T] = _encoder
      def decoder: JSObjectDecoder[T] = _decoder
    }

}
